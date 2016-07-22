package com.dyrs.smjj.itracker.control;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.dyrs.smjj.itracker.entity.Issue;

@RequestScoped
public class IssueProducer {
	@Inject
	private IssueDao issueDao;

	private List<Issue> issues;

	@PostConstruct
	public void retriveAllIssues() {
		issues = issueDao.findAll();
	}

	@Produces
	@Named
	public List<Issue> getIssues() {
		return issues;
	}

	public void onListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Issue member) {
		retriveAllIssues();
	}
}
