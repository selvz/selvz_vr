/**
 * 
 */
package com.selvz.vr.repository.db.dao;

import java.util.List;

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
}
