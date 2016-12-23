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
import com.selvz.vr.web.external.MenuExt;
import com.selvz.vr.web.external.PosterExt;
import com.selvz.vr.web.external.SelvzExt;
import com.selvz.vr.web.external.UserConfigExt;
import com.selvz.vr.web.mapper.PosterWebMapper;
import com.selvz.vr.web.mapper.ScenarioWebMapper;

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
	private ScenarioWebMapper scenarioWebMapper;

	@Autowired
	private PosterWebMapper posterWebMapper;

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
		SelvzExt selvzExt = new SelvzExt();

		selvzExt.email = source.getEmail();

		Scenario scenario = source.getScenario();
		if (scenario != null) {
			selvzExt.env = scenarioWebMapper.convertToExternal(scenario).address;
		}
		selvzExt.sound = "";

		MenuExt menuExt = new MenuExt();
		for (Poster poster : source.getPosters()) {
			PosterExt posterExt = posterWebMapper.convertToExternal(poster);
			menuExt.item.add(posterExt);
		}
		menuExt.border = scenario.getBorder();
		menuExt.slides = scenario.getSlides();

		selvzExt.menu = menuExt;
		
		configExt.selvz = selvzExt;
		
		return configExt;
	}
}
