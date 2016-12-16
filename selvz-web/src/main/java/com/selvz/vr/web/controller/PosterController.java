/**
 * 
 */
package com.selvz.vr.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.selvz.vr.repository.db.dao.PosterDao;
import com.selvz.vr.repository.db.pojo.Poster;
import com.selvz.vr.web.external.PosterExt;
import com.selvz.vr.web.external.PostersExt;
import com.selvz.vr.web.mapper.PosterMapper;

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
	private PosterMapper posterMapper;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody PostersExt getPosters() {
		PostersExt postersExt = new PostersExt();
		List<Poster> posters = posterDao.findAll();
		for (Poster poster : posters) {
			PosterExt posterExt = posterMapper.convertToExternal(poster);
			postersExt.posters.add(posterExt);
		}

		return postersExt;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody PosterExt getPoster(@PathVariable Long id) {
		Poster poster = posterDao.getById(id);
		PosterExt posterExt = posterMapper.convertToExternal(poster);

		return posterExt;
	}
}
