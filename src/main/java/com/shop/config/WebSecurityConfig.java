package com.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.shop.service.UserService;
import com.shop.service.impl.CustomUserDetailsService;

import jakarta.servlet.Filter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	private final JwtAuthEntryPoint authEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	public WebSecurityConfig(JwtAuthEntryPoint authEntryPoint) {
		this.authEntryPoint = authEntryPoint;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.cors()
		.and()
		.csrf().disable()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
        .exceptionHandling()
        .authenticationEntryPoint(authEntryPoint)
        .and()
        .authorizeRequests()
        .requestMatchers("/api/auth/**").permitAll()
				.requestMatchers(HttpMethod.GET,"/api/item/**").hasAuthority("USER")
				.requestMatchers(HttpMethod.POST,"/api/item/**").hasAuthority("ADMIN")
        .anyRequest().permitAll()
        .and()
        .httpBasic();
		
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}



    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
	
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
