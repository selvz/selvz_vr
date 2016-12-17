/**
 * 
 */
package com.selvz.vr.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.selvz.vr.repository.db.dao.PosterDao;
import com.selvz.vr.repository.db.dao.UserDao;
import com.selvz.vr.repository.db.pojo.Poster;
import com.selvz.vr.repository.db.pojo.User;
import com.selvz.vr.web.external.PosterExt;
import com.selvz.vr.web.external.PostersExt;
import com.selvz.vr.web.mapper.PosterWebMapper;
import com.selvz.vr.web.security.SecurityUserDetails;

/**
 * @author casam
 *
 */
@RestController
@RequestMapping(value = "/v1/posters")
public class PosterController {

	@Autowired
	private PosterDao posterDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private PosterWebMapper posterWebMapper;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody PostersExt getPosters() {
		SecurityUserDetails currentUser = (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String email = currentUser.getUsername();
		PostersExt postersExt = new PostersExt();
		List<Poster> posters = posterDao.findAll();
		for (Poster poster : posters) {
			if (poster.getUser().getEmail().equals(email)) {
				PosterExt posterExt = posterWebMapper.convertToExternal(poster);
				postersExt.posters.add(posterExt);
			}
		}

		return postersExt;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody PosterExt getPoster(@PathVariable Long id) {
		Poster poster = posterDao.getById(id);
		PosterExt posterExt = posterWebMapper.convertToExternal(poster);

		return posterExt;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody PosterExt createPoster(@RequestBody PosterExt newPosterExt, HttpServletResponse response) {
		Poster poster = posterWebMapper.convertToEntity(newPosterExt);
		SecurityUserDetails currentUser = (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDao.getByEmail(currentUser.getUsername());
		poster.setUser(user);
		posterDao.save(poster);
		PosterExt posterExt = posterWebMapper.convertToExternal(poster);

		return posterExt;
	}

	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAllPosters() {
		List<Poster> posters = posterDao.findAll();
		for (Poster poster : posters) {
			posterDao.delete(poster);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletePoster(@PathVariable Long id) {
		Poster poster = posterDao.getById(id);
		posterDao.delete(poster);
	}
}
