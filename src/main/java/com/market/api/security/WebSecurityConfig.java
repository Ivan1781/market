package com.market.api.security;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;

import javax.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    // @Autowired
    // CustomUserDetailsService userDetailsService;
    @Autowired
    private JwtAuthEntryPoint authEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//         http
//         .csrf().disable()   // Disable CSRF for simplicity, enable in production
//         .exceptionHandling()
        // .authenticationEntryPoint(authEntryPoint)
//         .and()
//         .sessionManagement()
//         .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//         .and()
//         .authorizeHttpRequests()
//         .antMatchers("/api/auth/**")
//         .permitAll()
//         .anyRequest().authenticated()
//         .and()
//         .httpBasic();

            http
                .authorizeHttpRequests(authorize-> authorize
                    .regexMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                    .regexMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/**")).authenticated());
            http.csrf(AbstractHttpConfigurer::disable);
            http.formLogin(form->form
                .permitAll()
                .successForwardUrl("/api/user")
                .permitAll()
            );
            http.exceptionHandling(c->c.authenticationEntryPoint(authEntryPoint));
            http.cors();
            
            http.sessionManagement(x->x
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                
                 
                

                


        http.addFilterBefore(jwtAuthenticationFilter(),
        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public  JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration authenticationConfiguration) throws Exception{
            return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean 
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    } 
}