/**
 * 
 */
package com.selvz.vr.repository.db.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.selvz.vr.repository.db.pojo.Poster;

/**
 * @author casam
 *
 */
@Repository
public class PosterDao extends DefaultDao {

	@Transactional(readOnly = true)
	public Poster getById(long id) {
		return (Poster) getSession().get(Poster.class, id);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Poster> findAll() {
		return getSession().createCriteria(Poster.class).list();
	}

	@Transactional
	public void save(Poster e) {
		getSession().persist(e);
	}

	@Transactional
	public void update(Poster e) {
		getSession().update(e);
	}

	@Transactional
	public void delete(Poster e) {
		getSession().delete(e);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Poster> fetchUser() {
		Criteria criteria = getSession().createCriteria(Poster.class);
		List<Poster> posters = criteria.setFetchMode("user", FetchMode.JOIN).list();

		return posters;
	}
}
