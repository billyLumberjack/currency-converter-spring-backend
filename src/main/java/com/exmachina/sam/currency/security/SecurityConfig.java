package com.exmachina.sam.currency.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${spring.security.admin.username}")
    private final String adminUsername = null;

    @Value("${spring.security.admin.password}")
    private final String adminPassword = null;

    @Autowired
    protected ObjectMapper objectMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        AuthenticationEntryPoint authenticationEntryPoint = (request, response, e) -> {

            AuthenticationBean authenticationBean = new AuthenticationBean(e.getMessage());
            String authBeanJson = objectMapper.writeValueAsString(authenticationBean);

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(authBeanJson);
        };

        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/convert/**").hasRole(UserRoles.STRING_ADMIN)
                .antMatchers("/currencies/**").hasRole(UserRoles.STRING_ADMIN)
                .antMatchers("/rate/**").hasRole(UserRoles.STRING_ADMIN)
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        auth.inMemoryAuthentication()
                .withUser(adminUsername)
                .password(encoder.encode(adminPassword))
                .roles(UserRoles.STRING_ADMIN);
    }
}