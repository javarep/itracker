package com.dyrs.smjj.itracker.control;

import java.util.List;

import javax.ejb.Stateless;

import com.dyrs.smjj.itracker.entity.Issue;

@Stateless
public class IssueDao extends AbstractDao<Issue> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6965589751681200501L;

	public IssueDao() {
		super(Issue.class);
	}

	public List<Issue> getReportIssues(String mobile) {
		return getEntityManager()
				.createQuery("select u from Issue u where u.mobile = :mobile order by u.status,u.orderDate desc")
				.setParameter("mobile", mobile).getResultList();
	}

	public List<Issue> getResolveIssues(String category) {
		return getEntityManager()
				.createQuery("select u from Issue u where u.category = :category order by u.status,u.orderDate desc")
				.setParameter("category", category).getResultList();
	}
}
