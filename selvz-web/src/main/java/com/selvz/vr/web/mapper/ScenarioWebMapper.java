/**
 * 
 */
package com.selvz.vr.web.mapper;

import org.springframework.stereotype.Service;

import com.selvz.vr.repository.db.pojo.Scenario;
import com.selvz.vr.web.external.ScenarioExt;

/**
 * @author casam
 *
 */
@Service
public class ScenarioWebMapper implements WebMapper<Scenario, ScenarioExt> {

	@Override
	public ScenarioExt convertToExternal(Scenario source) {
		ScenarioExt scenarioExt = new ScenarioExt();
		scenarioExt.id = source.getId();
		scenarioExt.address = source.getAddress();
		scenarioExt.label = source.getLabel();
		
		return scenarioExt;
	}

	@Override
	public Scenario convertToEntity(ScenarioExt source) {
		Scenario scenario = new Scenario();
		scenario.setId(source.id);
		scenario.setAddress(source.address);
		scenario.setLabel(source.label);

		return scenario;
	}

}
