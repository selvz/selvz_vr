/**
 * 
 */
package com.selvz.vr.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.selvz.vr.repository.db.dao.UserDao;
import com.selvz.vr.repository.db.pojo.User;
import com.selvz.vr.web.mapper.SecurityWebMapper;

/**
 * @author casam
 *
 */
@Service
public class SecurityUserService implements UserDetailsService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private SecurityWebMapper securityWebMapper;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userDao.getByEmail(email);
		if (user == null) {
			return null;
		}
		
		return securityWebMapper.convertToSecurity(user);
	}

}
