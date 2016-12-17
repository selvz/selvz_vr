/**
 * 
 */
package com.selvz.vr.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

/**
 * @author casam
 *
 */
@Service("authenticationEntryPoint")
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {

		response.reset();
		response.setHeader("Location", "index.html");
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
	}

}
