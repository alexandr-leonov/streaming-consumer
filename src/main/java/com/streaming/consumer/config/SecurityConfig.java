package com.streaming.consumer.config;

import com.streaming.consumer.config.property.SecurityProperties;
import com.streaming.consumer.type.SecurityRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityProperties properties;

    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username(properties.getName())
                .password(properties.getPassword())
                .roles(SecurityRole.ADMIN.name())
                .build();
        return new MapReactiveUserDetailsService(user);
    }

}
