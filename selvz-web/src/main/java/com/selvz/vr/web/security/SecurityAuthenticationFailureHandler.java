/**
 * 
 */
package com.selvz.vr.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

/**
 * @author casam
 *
 */
@Service("authenticationFailureHandler")
public class SecurityAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		response.reset();
		response.setHeader("Location", "index.html");
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
	}


}
