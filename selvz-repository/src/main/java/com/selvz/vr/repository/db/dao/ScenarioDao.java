/**
 * 
 */
package com.selvz.vr.repository.db.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.selvz.vr.repository.db.pojo.Scenario;

/**
 * @author casam
 *
 */
@Repository
public class ScenarioDao extends DefaultDao {
	
	@Transactional(readOnly = true)
	public Scenario getById(long id) {
		return (Scenario) getSession().get(Scenario.class, id);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Scenario> findAll() {
		return getSession().createCriteria(Scenario.class).list();
	}

	@Transactional
	public void save(Scenario e) {
		getSession().persist(e);
	}

	@Transactional
	public void update(Scenario e) {
		getSession().update(e);
	}

	@Transactional
	public void delete(Scenario e) {
		getSession().delete(e);
	}
}
