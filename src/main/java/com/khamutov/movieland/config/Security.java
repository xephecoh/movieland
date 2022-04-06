package com.khamutov.movieland.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("*/api/v1/movies/*")
                .permitAll()
                .antMatchers("*/admin/*")
                .hasRole("admin")
                //.antMatchers("/api/v1/movies/*")
                //.hasAuthority("admin")
                .and()
                .formLogin()
                .and()
                .logout().logoutSuccessUrl("/");
    }

/*    @Bean
    public JdbcUserDetailsManager users(DataSource dataSource) {
        UserDetails user = User.builder()
                .username("user")
                .password("{bcrypt}$2a$12$.YIauhJES6IOhPzx29gUPuoGCR8coELF.01odvPu.b5j2f22WGWvy")
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{bcrypt}$2a$12$.YIauhJES6IOhPzx29gUPuoGCR8coELF.01odvPu.b5j2f22WGWvy")
                .roles("admin", "user")
                .build();
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        String username = user.getUsername();
        if (jdbcUserDetailsManager.userExists(username)) {
            jdbcUserDetailsManager.deleteUser(username);
        }
        String adminName = admin.getUsername();
        if (jdbcUserDetailsManager.userExists(adminName)) {
            jdbcUserDetailsManager.deleteUser(adminName);
        }
        jdbcUserDetailsManager.createUser(user);
        jdbcUserDetailsManager.createUser(admin);
        return jdbcUserDetailsManager;
    }*/
}

