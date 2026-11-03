package com.tekmaturix.enterpriseknowledgeassistantmcp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
		return httpSecurity.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
				.cors(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable).build();
	}

}
