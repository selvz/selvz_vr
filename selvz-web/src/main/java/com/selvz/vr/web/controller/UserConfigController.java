/**
 * 
 */
package com.selvz.vr.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.selvz.vr.repository.db.dao.UserDao;
import com.selvz.vr.repository.db.pojo.Poster;
import com.selvz.vr.repository.db.pojo.Scenario;
import com.selvz.vr.repository.db.pojo.User;
import com.selvz.vr.web.external.PosterExt;
import com.selvz.vr.web.external.ScenarioExt;
import com.selvz.vr.web.external.UserConfigExt;
import com.selvz.vr.web.external.UserExt;

/**
 * @author casam
 *
 */
@RestController
@RequestMapping(value = "/v1/configs")
public class UserConfigController {
	
	@Autowired
	private UserDao userDao;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody UserConfigExt getUserConfig(){
		User user = userDao.getById(3);
		Scenario scenario = userDao.fetchScenarioByUserId(3).getScenario();
		Set<Poster> posters = userDao.fetchPostersByUserId(3).getPosters();
		
		UserConfigExt configExt = new UserConfigExt();
		
		ScenarioExt scenarioExt = new ScenarioExt();
		scenarioExt.address = scenario.getAddress();
		scenarioExt.label = scenario.getLabel();
		configExt.scenario = scenarioExt;
		
		UserExt userExt = new UserExt();
		userExt.email = user.getEmail();
		configExt.user = userExt;
		
		for (Poster poster : posters) {
			PosterExt posterExt = new PosterExt();
			posterExt.address = poster.getAddress();
			posterExt.label = poster.getLabel();
			configExt.posters.add(posterExt);
		}
		
		return configExt;
	}
}
