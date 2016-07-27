package com.brandon.configurations;

import com.brandon.BasedProjectApplication;
import com.brandon.utils.BUtil;
import com.google.common.base.MoreObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.messageresolver.SpringMessageResolver;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.nio.charset.Charset;
import java.util.TimeZone;

/**
 * Created by BrandonLee on 2016-07-20.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = BasedProjectApplication.class)
public class ViewResolverConfiguration extends WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter implements ApplicationContextAware {
    static final Charset CHARACTER_ENCODING = Charset.forName("UTF-8");
    final Logger logger = LoggerFactory.getLogger(ViewResolverConfiguration.class);
    private ApplicationContext applicationContext;

    @Autowired
    private BUtil bUtil;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
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

    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setCacheSeconds(bUtil.isRealMode() ? -1 : 3600);
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding(CHARACTER_ENCODING.name());
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }

    @Bean
    public SpringMessageResolver springMessageResolver(MessageSource messageSource) {
        SpringMessageResolver messageResolver = new SpringMessageResolver();
        messageResolver.setMessageSource(messageSource);
        return messageResolver;
    }


    private ViewResolver makeViewResolver(TEMPLATE_TYPED template_typed, SpringMessageResolver springMessageResolver) {
        if (!bUtil.isRealMode()) {
            logger.info("##### {} Cache Mode : {} >> {}", template_typed.name(), bUtil.isRealMode(), template_typed.toString());
        }
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setApplicationContext(applicationContext);
        resolver.setTemplateEngine(templateEngine(templateResolver(template_typed), springMessageResolver));
        switch (template_typed) {
            case JAVASCRIPT:
            case CSS:
                resolver.setViewNames(template_typed.getViewNames());
            case HTML:
                break;
        }
        resolver.setCache(bUtil.isRealMode());
        resolver.setContentType(template_typed.getContentType());
        resolver.setCharacterEncoding(template_typed.getCharacterEncoding());
        return resolver;
    }

    private TemplateEngine templateEngine(ITemplateResolver templateResolver, SpringMessageResolver springMessageResolver) {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver);
        engine.setMessageResolver(springMessageResolver);
        engine.addDialect(new Java8TimeDialect());
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
        HTML {
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

        }, CSS {
            @Override
            String getSuffix() {
                return ".css";
            }

            @Override
            String[] getViewNames() {
                return array("*.css");
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
                return array("*.js");
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
            return CHARACTER_ENCODING.name();
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("CHARACTER_ENCODING", CHARACTER_ENCODING.name())
                    .add("ContentType", getContentType())
                    .add("Prefix", getPrefix())
                    .add("Suffix", getSuffix())
                    .toString();
        }
    }
}
