package com.brandon.author;

import com.google.common.collect.Lists;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * Created by brandon Lee on 2016-08-30.
 */
public enum SimpleHierarchyRole implements GrantedAuthority {
    ROLE_USER {
        @Override
        public String getAuthority() {
            return "USER";
        }

        @Override
        public List<GrantedAuthority> authorities() {
            return Lists.newArrayList(ROLE_USER);
        }
    }, ROLE_ADMIN {
        @Override
        public String getAuthority() {
            return "ADMIN";
        }

        @Override
        public List<GrantedAuthority> authorities() {
            return Lists.newArrayList(ROLE_USER, ROLE_ADMIN);
        }
    }, ROLE_MASTER {
        @Override
        public String getAuthority() {
            return "MASTER";
        }

        @Override
        public List<GrantedAuthority> authorities() {
            return Lists.newArrayList(ROLE_USER, ROLE_ADMIN, ROLE_MASTER);
        }
    };


    public abstract List<GrantedAuthority> authorities();
}
