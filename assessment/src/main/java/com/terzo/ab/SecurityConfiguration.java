package com.terzo.ab;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		auth.inMemoryAuthentication().withUser("hr").password(encoder.encode("password")).roles("HR").and()
				.withUser("manager").password(encoder.encode("password")).roles("MANAGER").and().withUser("user")
				.password(encoder.encode("password")).roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().disable().authorizeRequests().antMatchers("/").hasAnyRole("USER", "HR", "MANAGER")
				.antMatchers("/hr/updateSalary").hasRole("HR")
				.antMatchers("/hr/viewSalaryByEmployee").hasAnyRole("MANAGER", "HR").anyRequest().authenticated().and()
				.httpBasic();
	}
}
