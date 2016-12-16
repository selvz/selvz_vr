/**
 * 
 */
package com.selvz.vr.web.mapper;

import org.springframework.stereotype.Service;

import com.selvz.vr.repository.db.pojo.User;
import com.selvz.vr.web.external.UserExt;

/**
 * @author casam
 *
 */
@Service
public class UserWebMapper implements WebMapper<User, UserExt> {

	@Override
	public UserExt convertToExternal(User source) {
		UserExt userExt = new UserExt();
		userExt.id = source.getId();
		userExt.email = source.getEmail();
		userExt.password = source.getPassword();
		
		return userExt;
	}

	@Override
	public User convertToEntity(UserExt source) {
		User user = new User();
		user.setId(source.id);
		user.setEmail(source.email);
		user.setPassword(source.password);
		user.setRole("ROLE_USER");
		user.setFirstAccess(Boolean.TRUE);
		
		return user;
	}

}
