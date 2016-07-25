package com.dyrs.smjj.itracker.control;

import java.util.List;

import javax.ejb.Stateless;

import com.dyrs.smjj.itracker.entity.Issue;
import com.dyrs.smjj.itracker.filter.LoginBean;

@Stateless
public class IssueDao extends AbstractDao<Issue> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6965589751681200501L;

	public IssueDao() {
		super(Issue.class);
	}

	public List<Issue> getReportIssues(LoginBean bean) {
		return getEntityManager()
				.createQuery(
						"select u from Issue u where u.mobile = :mobile or u.department= :department and u.userName = :username order by u.status,u.orderDate desc")
				.setParameter("mobile", bean.getMobile()).setParameter("department", bean.getDepartment())
				.setParameter("username", bean.getUsername()).getResultList();
	}

	public List<Issue> getResolveIssues(String category) {
		return getEntityManager()
				.createQuery("select u from Issue u where u.category = :category order by u.status,u.orderDate desc")
				.setParameter("category", category).getResultList();
	}
}
