package com.dyrs.smjj.itracker.control;

import java.util.Collection;

import javax.ejb.Stateless;

import com.dyrs.smjj.itracker.entity.User;

@Stateless
public class UserDao extends AbstractDao<User> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1003756971340886342L;

	public UserDao() {
		super(User.class);
	}


	public User findByCode(String code) {
		Collection<User> users = getEntityManager().createQuery("select u from User u where u.code = :username")
				.setParameter("username", code).setMaxResults(1).getResultList();
		if (users.size() > 0) {
			return users.toArray(new User[1])[0];
		}
		return null;
	}
}
