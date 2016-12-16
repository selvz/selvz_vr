/**
 * 
 */
package com.selvz.vr.web.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.selvz.vr.repository.db.dao.UserDao;
import com.selvz.vr.repository.db.pojo.Poster;
import com.selvz.vr.repository.db.pojo.Scenario;
import com.selvz.vr.repository.db.pojo.User;
import com.selvz.vr.web.external.PosterExt;
import com.selvz.vr.web.external.UserConfigExt;
import com.selvz.vr.web.mapper.PosterMapper;
import com.selvz.vr.web.mapper.ScenarioMapper;

/**
 * @author casam
 *
 */
@RestController
@RequestMapping(value = "/v1/configs")
public class UserConfigController {

	@Autowired
	private UserDao userDao;

	@Autowired
	private ScenarioMapper scenarioMapper;

	@Autowired
	private PosterMapper posterMapper;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody UserConfigExt getUserConfig(@RequestParam(required = true, value = "e") String email) {
		User user = userDao.getByEmail(email);
		Long id = user.getId();
		Scenario scenario = userDao.fetchScenarioByUserId(id).getScenario();
		Set<Poster> posters = userDao.fetchPostersByUserId(id).getPosters();
		user.setScenario(scenario);
		user.setPosters(posters);

		return convertToExternal(user);
	}

	public UserConfigExt convertToExternal(User source) {
		UserConfigExt configExt = new UserConfigExt();

		configExt.email = source.getEmail();

		Scenario scenario = source.getScenario();
		configExt.scenario = scenarioMapper.convertToExternal(scenario);

		for (Poster poster : source.getPosters()) {
			PosterExt posterExt = posterMapper.convertToExternal(poster);
			configExt.posters.add(posterExt);
		}

		return configExt;
	}
}
