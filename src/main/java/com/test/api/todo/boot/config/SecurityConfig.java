package com.test.api.todo.boot.config;

import com.test.api.todo.auth.filter.AuthJwtFilter;
import com.test.api.todo.boot.advisor.CustomAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthJwtFilter authJwtFilter;
    private final CorsConfig corsConfig;
    private static final String[] PERMIT_URL_ARRAY = {
        /* swagger v2 */
        "/v2/api-docs",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui.html",
        "/webjars/**",
        /* swagger v3 */
        "/v3/api-docs/**",
        "/swagger-ui/**"
    };

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            // CORS
            .cors().configurationSource(corsConfig.corsFilter())
            .and()
            // CSRF 해제 - JWT 사용하기때문
            .csrf().disable()
            // JWT 사용을 위한 Stateless Policy 설정
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .formLogin().disable()
            .httpBasic().disable()
            .authorizeHttpRequests((authz) -> {
                    try {
                        authz
                                .antMatchers(PERMIT_URL_ARRAY).permitAll()
                                .antMatchers(HttpMethod.OPTIONS).permitAll()
                                .antMatchers("/**/public/**").permitAll()
                                .antMatchers(HttpMethod.POST,"/user/**").permitAll()
                                .antMatchers("/auth/login").permitAll()
                                .antMatchers("/verify/**").permitAll()
                                .anyRequest().authenticated()
                                .and()
                                .addFilterBefore(authJwtFilter, UsernamePasswordAuthenticationFilter.class)
                                .exceptionHandling()
                                .accessDeniedHandler(new CustomAccessDeniedHandler());
                    }catch (Exception e) {

                    }
                }
            );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring().antMatchers("/resources/**"));
    }
}
