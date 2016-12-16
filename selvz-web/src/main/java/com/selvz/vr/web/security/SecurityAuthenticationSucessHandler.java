/**
 * 
 */
package com.selvz.vr.web.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

/**
 * @author casam
 *
 */
public class SecurityAuthenticationSucessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private RequestCache requestCache = new HttpSessionRequestCache();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		Collection<? extends GrantedAuthority> roles = user.getAuthorities();
		GrantedAuthority role = buildUserRole();
		String contextPath = request.getContextPath();
		String redirectAddress;
		if (roles != null && roles.contains(role)) {
			redirectAddress = contextPath + "/config.html";
		} else {
			redirectAddress = contextPath + "/admin.html";
		}

		System.out.println(redirectAddress);
		response.sendRedirect(redirectAddress);
		SavedRequest savedRequest = requestCache.getRequest(request, response);

		if (savedRequest == null) {
			clearAuthenticationAttributes(request);
			return;
		}
		String targetUrlParam = getTargetUrlParameter();
		if (isAlwaysUseDefaultTargetUrl()
				|| (targetUrlParam != null && StringUtils.hasText(request.getParameter(targetUrlParam)))) {
			requestCache.removeRequest(request, response);
			clearAuthenticationAttributes(request);
			return;
		}

		clearAuthenticationAttributes(request);
	}

	private SecurityRole buildUserRole() {
		SecurityRole role = new SecurityRole();
		role.setName("ROLE_USER");
		return role;
	}
}
