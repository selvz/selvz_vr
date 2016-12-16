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
	private SecurityAuthenticationSucessHandler authenticationSucessHandler;

	@Autowired
	private SecurityAuthenticationProvider authenticationProvider;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authenticationProvider(authenticationProvider).csrf().disable().exceptionHandling().and()
				.authorizeRequests().antMatchers("/index.html", "resources/**").permitAll().antMatchers("/config.html")
				.hasAnyRole("USER").antMatchers("/admin.html").hasAnyRole("ADMIN").antMatchers("/api/v1/**")
				.authenticated().and().formLogin().permitAll().successHandler(authenticationSucessHandler).and()
				.logout().invalidateHttpSession(true).deleteCookies("JSESSIONID");
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
