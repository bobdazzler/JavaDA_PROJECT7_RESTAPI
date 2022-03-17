package com.nnk.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.nnk.springboot.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Bean
    public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userDetailsService());
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/login").permitAll()
		.antMatchers("/user/list").permitAll()
		.antMatchers("/user/add").permitAll()
		.antMatchers("/user/update").permitAll()
		.antMatchers("/user/validate").permitAll()
        //.antMatchers("/user/**").hasAnyAuthority("ADMIN")
		.antMatchers("/bidlist/**").authenticated()
		.antMatchers("/curvePoint/**").authenticated()
		.antMatchers("/rating/**").authenticated()
		.antMatchers("/ruleName/**").authenticated()
		.antMatchers("/trade/**").authenticated()
		.antMatchers("admin/home").authenticated()
        .antMatchers("/bidlist/**").hasAnyAuthority("ADMIN","USER")
        .antMatchers("/curvePoint/**").hasAnyAuthority("ADMIN","USER")
        .antMatchers("/rating/**").hasAnyAuthority("ADMIN","USER")
        .antMatchers("/ruleName/**").hasAnyAuthority("ADMIN","USER")
        .antMatchers("/trade/**").hasAnyAuthority("ADMIN","USER")
        .anyRequest().authenticated()
       
		.and()
		.formLogin()
		.defaultSuccessUrl("/admin/home")
		.permitAll()
		.and()
		.logout()
		.invalidateHttpSession(true)
		.clearAuthentication(true)
		.permitAll();
	}
}
