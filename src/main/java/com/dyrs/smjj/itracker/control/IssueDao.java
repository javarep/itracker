package com.dyrs.smjj.itracker.control;

import javax.ejb.Stateless;

import com.dyrs.smjj.itracker.entity.Issue;

@Stateless
public class IssueDao extends AbstractDao<Issue> {
	public IssueDao() {
		super(Issue.class);
	}
}
