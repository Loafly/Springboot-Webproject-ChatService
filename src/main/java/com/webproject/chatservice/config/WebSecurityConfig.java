package com.webproject.chatservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public WebSecurityConfig (JwtTokenProvider jwtTokenProvider)
    {
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();
//        http.cors().configurationSource(corsConfigurationSource());
//        http.headers().frameOptions().sameOrigin();
        http.authorizeRequests()
                // login 없이 허용
//                .antMatchers("/**").permitAll()
//                .antMatchers("/api/user/**").permitAll()
                .antMatchers("/chatting/**").permitAll()
                .antMatchers("/api/user/login").permitAll()
                .antMatchers("/api/user/kakaoLogin").permitAll()
                .antMatchers("/api/user/signup").permitAll()
                .antMatchers("/api/user/signup/emailCheck").permitAll()
                .antMatchers("/api/user/findPassword").permitAll()
                .antMatchers("/api/user/changePassword").permitAll()
//                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/api/chat/message").permitAll()
                .antMatchers("/forbidden").permitAll()

                // 그 외 모든 요청은 인증과정 필요
                .anyRequest().authenticated()
//                .and()
//                .exceptionHandling()
//                .accessDeniedPage("/forbidden")
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);

        // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다
    }
}
