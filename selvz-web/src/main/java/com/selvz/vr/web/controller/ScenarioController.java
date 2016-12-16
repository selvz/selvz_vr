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

import com.selvz.vr.repository.db.dao.ScenarioDao;
import com.selvz.vr.repository.db.pojo.Scenario;
import com.selvz.vr.web.external.ScenarioExt;
import com.selvz.vr.web.external.ScenariosExt;
import com.selvz.vr.web.mapper.ScenarioMapper;

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
	private ScenarioMapper scenarioMapper;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ScenariosExt getScenarios() {
		ScenariosExt scenariosExt = new ScenariosExt();
		List<Scenario> scenarios = scenarioDao.findAll();
		for (Scenario scenario : scenarios) {
			ScenarioExt scenarioExt = scenarioMapper.convertToExternal(scenario);
			scenariosExt.scenarios.add(scenarioExt);
		}

		return scenariosExt;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ScenarioExt getScenario(@PathVariable Long id) {
		Scenario scenario = scenarioDao.getById(id);
		ScenarioExt scenarioExt = scenarioMapper.convertToExternal(scenario);

		return scenarioExt;
	}

}
