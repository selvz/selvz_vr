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

import com.selvz.vr.repository.db.dao.ScenarioDao;
import com.selvz.vr.repository.db.dao.UserDao;
import com.selvz.vr.repository.db.pojo.Scenario;
import com.selvz.vr.repository.db.pojo.User;
import com.selvz.vr.web.external.ScenarioExt;
import com.selvz.vr.web.mapper.ScenarioWebMapper;
import com.selvz.vr.web.security.SecurityUserDetails;

/**
 * @author casam
 *
 */
@RestController
@RequestMapping(value = "/v1/scenarios")
public class ScenarioController {

	@Autowired
	private ScenarioDao scenarioDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private ScenarioWebMapper scenarioWebMapper;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ScenarioExt getScenarios() {
		SecurityUserDetails currentUser = (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User userTemp = userDao.getByEmail(currentUser.getUsername());
		User user = userDao.fetchScenarioByUserId(userTemp.getId());
		Scenario scenario = user.getScenario();
		if (scenario == null) {
			return new ScenarioExt();
		}
		ScenarioExt scenarioExt = scenarioWebMapper.convertToExternal(scenario);

		return scenarioExt;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ScenarioExt getScenario(@PathVariable Long id) {
		Scenario scenario = scenarioDao.getById(id);
		ScenarioExt scenarioExt = scenarioWebMapper.convertToExternal(scenario);

		return scenarioExt;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody ScenarioExt createScenario(@RequestBody ScenarioExt newScenarioExt,
			HttpServletResponse response) {
		Long id = newScenarioExt.id;
		Scenario scenario = scenarioDao.getById(id);
		if (scenario == null) {
			scenario = scenarioWebMapper.convertToEntity(newScenarioExt);
		} else {
			scenario = updateFields(scenario, newScenarioExt);
		}
		SecurityUserDetails currentUser = (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDao.getByEmail(currentUser.getUsername());
		scenarioDao.saveOrUpdate(scenario);
		user.setScenario(scenario);
		userDao.update(user);
		ScenarioExt scenarioExt = scenarioWebMapper.convertToExternal(scenario);

		return scenarioExt;
	}

	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAllScenarios() {
		List<Scenario> scenarios = scenarioDao.findAll();
		for (Scenario scenario : scenarios) {
			scenarioDao.delete(scenario);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteScenario(@PathVariable Long id) {
		Scenario scenario = scenarioDao.getById(id);
		scenarioDao.delete(scenario);
	}

	private Scenario updateFields(Scenario scenario, ScenarioExt newScenarioExt) {
		scenario.setAddress(newScenarioExt.address);
		scenario.setLabel(newScenarioExt.label);
		scenario.setBorder(newScenarioExt.border);
		scenario.setSlides(newScenarioExt.slides);

		return scenario;
	}

}
