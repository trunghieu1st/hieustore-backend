package com.example.hieustore.security;

import com.example.hieustore.security.jwt.JwtAuthenticationEntryPoint;
import com.example.hieustore.security.jwt.JwtAuthenticationFilter;
import com.example.hieustore.service.impl.CustomUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsServiceImpl customUserDetailsService;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(request -> corsConfiguration())
                .and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                .antMatchers("/api/v1/auth/**","/api/v1/user/create").permitAll()
//                .anyRequest().authenticated()
                .anyRequest().permitAll()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint());
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    CorsConfiguration corsConfiguration() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setAllowCredentials(Boolean.FALSE);
        corsConfiguration.addAllowedMethod(HttpMethod.GET);
        corsConfiguration.addAllowedMethod(HttpMethod.POST);
        corsConfiguration.addAllowedMethod(HttpMethod.PATCH);
        corsConfiguration.addAllowedMethod(HttpMethod.PUT);
        corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
        return corsConfiguration;
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}
