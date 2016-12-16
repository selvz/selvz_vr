/**
 * 
 */
package com.selvz.vr.repository.db.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.selvz.vr.repository.db.pojo.User;

/**
 * @author casam
 *
 */
@Repository
public class UserDao extends DefaultDao {

	@Transactional(readOnly = true)
	public User getById(long id) {
		return (User) getSession().get(User.class, id);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<User> findAll() {
		return getSession().createCriteria(User.class).list();
	}

	@Transactional
	public void save(User e) {
		getSession().persist(e);
	}

	@Transactional
	public void update(User e) {
		getSession().update(e);
	}

	@Transactional
	public void delete(User e) {
		getSession().delete(e);
	}

	@Transactional(readOnly = true)
	public User fetchScenarioByUserId(long id) {
		Criteria criteria = getSession().createCriteria(User.class);
		User user = (User) criteria.setFetchMode("scenario", FetchMode.JOIN).add(Restrictions.eq("id", id))
				.uniqueResult();

		return user;
	}

	@Transactional(readOnly = true)
	public User fetchPostersByUserId(long id) {
		Criteria criteria = getSession().createCriteria(User.class);
		User user = (User) criteria.setFetchMode("posters", FetchMode.JOIN).add(Restrictions.eq("id", id))
				.uniqueResult();

		return user;
	}

}
