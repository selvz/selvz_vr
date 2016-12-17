/**
 * 
 */
package com.selvz.vr.web.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Service;

/**
 * @author casam
 *
 */
@Service("logoutSuccessHandler")
public class SecurityLogoutSucessHandler extends SimpleUrlLogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		response.reset();
		response.setHeader("Location", "index.html");
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		super.onLogoutSuccess(request, response, authentication);
	}
}
