package com.brandon.configurations;

import com.brandon.BasedProjectApplication;
import com.brandon.Constant;
import com.brandon.utils.BUtil;
import com.google.common.base.MoreObjects;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.messageresolver.SpringMessageResolver;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by BrandonLee on 2016-07-20.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = BasedProjectApplication.class)
public class ViewResolverConfiguration extends WebMvcConfigurerAdapter implements ApplicationContextAware {
    final Logger logger = getLogger(getClass());
    private ApplicationContext applicationContext;
    @Autowired
    private BUtil bUtil;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        boolean isReal = bUtil.isRealMode();
        registry.addResourceHandler("/font/**").addResourceLocations("classpath:/static/font/").setCachePeriod(isReal ? Constant.ONE_YEAR : 0);
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/").setCachePeriod(isReal ? Constant.ONE_YEAR : 0);
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/").setCachePeriod(isReal ? Constant.ONE_YEAR : 0);
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/").setCachePeriod(isReal ? Constant.ONE_YEAR : 0);
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/").setCachePeriod(isReal ? Constant.ONE_YEAR : 0);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        /* ArgumentResolver
        * Pageable과 같이 처리하기 위해서는 argumentResolver를 작성해야함
        *
        * */
    }

    @Bean
    public ViewResolver htmlViewResolver(SpringMessageResolver springMessageResolver) {
        return makeViewResolver(TEMPLATE_TYPED.HTML, springMessageResolver);
    }

    @Bean
    public ViewResolver cssViewResolver(SpringMessageResolver springMessageResolver) {
        return makeViewResolver(TEMPLATE_TYPED.CSS, springMessageResolver);
    }

    @Bean
    public ViewResolver javascriptViewResolver(SpringMessageResolver springMessageResolver) {
        return makeViewResolver(TEMPLATE_TYPED.JAVASCRIPT, springMessageResolver);
    }

    @Bean
    public CookieLocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setDefaultLocale(LocaleContextHolder.getLocale());
        localeResolver.setDefaultTimeZone(TimeZone.getDefault());
        localeResolver.setCookieName("locale");
        localeResolver.setCookieMaxAge(bUtil.isRealMode() ? 3600 : -1);
        return localeResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LocaleChangeInterceptor());
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setCacheSeconds(bUtil.isRealMode() ? -1 : 3600);
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding(Constant.CHARACTER_ENCODING.name());
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }

    @Bean
    public SpringMessageResolver springMessageResolver(MessageSource messageSource) {
        SpringMessageResolver messageResolver = new SpringMessageResolver();
        messageResolver.setMessageSource(messageSource);
        return messageResolver;
    }

    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setApplicationContext(applicationContext);
        localValidatorFactoryBean.setValidationMessageSource(messageSource());
        return localValidatorFactoryBean;
    }

    @Override
    public Validator getValidator() {
        return localValidatorFactoryBean();
    }

    private ViewResolver makeViewResolver(TEMPLATE_TYPED template_typed, SpringMessageResolver springMessageResolver) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setApplicationContext(applicationContext);
        resolver.setOrder(template_typed.ordinal() + 1);
        switch (template_typed) {
            case JAVASCRIPT:
            case CSS:
                resolver.setViewNames(template_typed.getViewNames());
                resolver.setTemplateEngine(templateEngine(templateResolver(template_typed), springMessageResolver));
                break;
            case HTML:
                resolver.setTemplateEngine(templateEngine(templateResolver(template_typed), springMessageResolver, new Java8TimeDialect(), new LayoutDialect()));
                break;
        }
        resolver.setCache(bUtil.isRealMode());
        resolver.setContentType(template_typed.getContentType());
        resolver.setCharacterEncoding(template_typed.getCharacterEncoding());
        return resolver;
    }

    private TemplateEngine templateEngine(ITemplateResolver templateResolver, SpringMessageResolver springMessageResolver, IDialect... dialects) {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver);
        engine.setMessageResolver(springMessageResolver);
        if (dialects != null) {
            Arrays.asList(dialects).stream().forEach(x -> engine.addDialect(x));
        }
        return engine;
    }

    private ITemplateResolver templateResolver(TEMPLATE_TYPED template_typed) {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(applicationContext);
        resolver.setPrefix(template_typed.getPrefix());
        resolver.setTemplateMode(template_typed.getTemplateMode());
        resolver.setCacheable(bUtil.isRealMode());
        switch (template_typed) {
            default:
                break;
            case HTML:
                resolver.setCacheTTLMs(3600L);
                resolver.setSuffix(template_typed.getSuffix());
                break;
        }
        return resolver;
    }

    enum TEMPLATE_TYPED {
        CSS {
            @Override
            String getSuffix() {
                return ".css";
            }

            @Override
            String[] getViewNames() {
                return array("common.css");
            }

            @Override
            String getContentType() {
                return "text/css";
            }

            @Override
            String getPrefix() {
                return "classpath:static/css/";
            }

            @Override
            TemplateMode getTemplateMode() {
                return TemplateMode.CSS;
            }

        }, JAVASCRIPT {
            @Override
            String getSuffix() {
                return ".js";
            }

            @Override
            String[] getViewNames() {
                return array("common.js");
            }

            @Override
            String getContentType() {
                return "application/javascript";
            }

            @Override
            String getPrefix() {
                return "classpath:static/js/";
            }

            @Override
            TemplateMode getTemplateMode() {
                return TemplateMode.JAVASCRIPT;
            }
        }, HTML {
            @Override
            String getSuffix() {
                return ".html";
            }

            @Override
            String[] getViewNames() {
                return array("*.html");
            }

            @Override
            String getContentType() {
                return "text/html";
            }

            @Override
            String getPrefix() {
                return "classpath:templates/";
            }

            @Override
            TemplateMode getTemplateMode() {
                return TemplateMode.HTML;
            }

        };

        private static String[] array(String... args) {
            return args;
        }

        abstract String getContentType();

        abstract String getPrefix();

        abstract String getSuffix();

        abstract String[] getViewNames();

        abstract TemplateMode getTemplateMode();

        public String getCharacterEncoding() {
            return Constant.CHARACTER_ENCODING.name();
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("CHARACTER_ENCODING", Constant.CHARACTER_ENCODING.name())
                    .add("ContentType", getContentType())
                    .add("Prefix", getPrefix())
                    .add("Suffix", getSuffix())
                    .toString();
        }
    }
}
