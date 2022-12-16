package com.jdw.springboot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "security.auth")
public class UserAuthProperties {

    private Map<String, User> user = new LinkedHashMap<>();

    public Map<String, User> getUser() {
        return user;
    }

    public void setUser(Map<String, User> user) {
        this.user = user;
    }


    public static class User {
        private String name;
        private String password;
        private List<String> roles;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public List<String> getRoles() {
            return roles;
        }

        public void setRoles(List<String> roles) {
            this.roles = roles;
        }
    }
}
