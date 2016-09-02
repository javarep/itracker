package com.dyrs.smjj.itracker.control;

import java.util.List;

import javax.ejb.Stateless;

import com.dyrs.smjj.itracker.entity.User_Department;

@Stateless
public class User_DepDao extends AbstractDao<User_Department> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1003756971340886342L;

	public User_DepDao() {
		super(User_Department.class);
	}

	public List<User_Department> getByUser(String username) {
		return getEntityManager().createQuery("select u from User_Department u where u.userName = :username")
				.setParameter("username", username).getResultList();
	}

	public List<User_Department> getByDepartment(String departmentname) {
		return getEntityManager()
				.createQuery("select u from User_Department u where u.departmentName = :departmentname")
				.setParameter("departmentname", departmentname).getResultList();
	}
}
