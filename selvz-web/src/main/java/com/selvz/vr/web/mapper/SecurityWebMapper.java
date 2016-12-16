/**
 * 
 */
package com.selvz.vr.web.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.selvz.vr.repository.db.pojo.User;
import com.selvz.vr.web.security.SecurityRole;
import com.selvz.vr.web.security.SecurityUserDetails;

/**
 * @author casam
 *
 */
@Service
public class SecurityWebMapper {
	
	public SecurityUserDetails convertToSecurity(User source) {
		SecurityUserDetails user = new SecurityUserDetails();
		user.setUsername(source.getEmail());
		user.setPassword(source.getPassword());
		user.setFirstAccess(source.getFirstAccess());
		SecurityRole role = new SecurityRole();
		role.setName(source.getRole());
		List<SecurityRole> roles = new ArrayList<SecurityRole>();
		roles.add(role);
		user.setAuthorities(roles);
		
		return user;
	}
}
