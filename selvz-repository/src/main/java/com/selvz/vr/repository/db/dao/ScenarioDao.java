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
	public Scenario getById(Long id) {
		if (id == null) {
			return null;
		}
		return (Scenario) getSession().get(Scenario.class, id);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Scenario> findAll() {
		return getSession().createCriteria(Scenario.class).list();
	}

	@Transactional
	public void save(Scenario s) {
		getSession().persist(s);
	}

	@Transactional
	public void update(Scenario s) {
		getSession().update(s);
	}

	@Transactional
	public void delete(Scenario s) {
		getSession().delete(s);
	}
	
	@Transactional
	public void saveOrUpdate(Scenario s) {
		getSession().saveOrUpdate(s);
	}
}
