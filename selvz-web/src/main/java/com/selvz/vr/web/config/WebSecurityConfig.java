/**
 * 
 */
package com.selvz.vr.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.selvz.vr.web.security.SecurityAuthenticationEntryPoint;
import com.selvz.vr.web.security.SecurityAuthenticationFailureHandler;
import com.selvz.vr.web.security.SecurityAuthenticationProvider;
import com.selvz.vr.web.security.SecurityAuthenticationSucessHandler;

/**
 * @author casam
 *
 */
@Configuration
@EnableWebSecurity
@ComponentScan("com.selvz.vr")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityAuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	private SecurityAuthenticationSucessHandler authenticationSucessHandler;
	
	@Autowired
	private SecurityLogoutSucessHandler logoutSuccessHandler;

	@Autowired
	private SecurityAuthenticationProvider authenticationProvider;

	@Autowired
	private SecurityAuthenticationFailureHandler authenticationFailureHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authenticationProvider(authenticationProvider).csrf().disable().exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint).and().authorizeRequests()
				.antMatchers("/index.html", "resources/**", "/header", "/footer", "/api/v1/configs**").permitAll()
				.antMatchers("/config.html").hasAnyRole("USER").antMatchers("/admin.html").hasAnyRole("ADMIN")
				.antMatchers("/api/v1/users**", "/api/v1/scenarios**", "/api/v1/posters**").authenticated().and()
				.formLogin().permitAll().successHandler(authenticationSucessHandler)
				.failureHandler(authenticationFailureHandler).and().logout().invalidateHttpSession(true)
				.deleteCookies("JSESSIONID", "selvz.user.email").logoutSuccessHandler(logoutSuccessHandler);
	}

	@Bean
	public SecurityAuthenticationSucessHandler restSuccessHandler() {
		return new SecurityAuthenticationSucessHandler();
	}

	@Bean
	public SimpleUrlAuthenticationFailureHandler restFailureHandler() {
		return new SimpleUrlAuthenticationFailureHandler();
	}
}
