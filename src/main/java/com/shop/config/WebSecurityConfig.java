package com.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

import jakarta.servlet.Filter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	private final JwtAuthEntryPoint authEntryPoint;
	private final UserService userDetailsService;

	@Autowired
	public WebSecurityConfig(JwtAuthEntryPoint authEntryPoint, UserService userDetailsService) {
		this.authEntryPoint = authEntryPoint;
		this.userDetailsService = userDetailsService;
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
        .anyRequest().permitAll()
        .and()
        .httpBasic();
http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
return http.build();
	}


	private Filter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

//	@Bean
//	public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
//			PasswordEncoder passwordEncoder) {
//		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//		authenticationProvider.setUserDetailsService(userDetailsService);
//		authenticationProvider.setPasswordEncoder(passwordEncoder);
//
//		return new ProviderManager(authenticationProvider);
//	}

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
