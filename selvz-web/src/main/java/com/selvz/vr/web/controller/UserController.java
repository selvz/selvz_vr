/**
 * 
 */
package com.selvz.vr.web.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.selvz.vr.repository.db.dao.UserDao;
import com.selvz.vr.repository.db.pojo.User;
import com.selvz.vr.web.external.UserExt;
import com.selvz.vr.web.external.UsersExt;
import com.selvz.vr.web.mapper.UserWebMapper;

/**
 * @author casam
 *
 */
@RestController
@RequestMapping(value = "/v1/users")
public class UserController {

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserWebMapper userWebMapper;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody UsersExt getUsers() {
		UsersExt usersExt = new UsersExt();
		List<User> users = userDao.findAll();
		for (User user : users) {
			UserExt userExt = userWebMapper.convertToExternal(user);
			usersExt.users.add(userExt);
		}

		return usersExt;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody UserExt getUser(@PathVariable Long id) {
		User user = userDao.getById(id);
		UserExt userExt = userWebMapper.convertToExternal(user);

		return userExt;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody UserExt createUser(@RequestBody UserExt newUserExt, HttpServletResponse response) {
		User user = userWebMapper.convertToEntity(newUserExt);
		userDao.save(user);
		UserExt userExt = userWebMapper.convertToExternal(user);

		return userExt;
	}

	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAllUsers() {
		List<User> users = userDao.findAll();
		for (User user : users) {
			if (!user.getRole().equals("ROLE_ADMIN")) {
				userDao.delete(user);
			}
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable Long id) {
		User user = userDao.getById(id);
		userDao.delete(user);
	}

	@RequestMapping(method = RequestMethod.PATCH)
	@ResponseStatus(HttpStatus.OK)
	public UserExt updateUserFields(@RequestBody UserExt newUserExt, HttpServletRequest request) {

		String email = null;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if ("selvz.user.email".equals(cookie.getName())) {
				email = cookie.getValue();
			}

		}

		if (email == null) {
			return null;
		}

		User user = userDao.getByEmail(email);
		updateUser(user, newUserExt);
		userDao.update(user);

		UserExt userExt = userWebMapper.convertToExternal(user);

		return userExt;
	}

	private void updateUser(User user, UserExt newUserExt) {
		String password = newUserExt.password;
		if (password != null) {
			user.setPassword(password);
		}

		user.setFirstAccess(Boolean.FALSE);
	}
}
