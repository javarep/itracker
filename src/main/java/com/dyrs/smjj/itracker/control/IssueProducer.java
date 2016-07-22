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

	private List<Issue> reportIssues;
	private List<Issue> resolveIssues;

	@PostConstruct
	public void retriveAllIssues() {
		reportIssues = issueDao.findAll();
		resolveIssues = issueDao.findAll();
	}

	@Produces
	@Named
	public List<Issue> getReportIssues() {
		return reportIssues;
	}
	
	@Produces
	@Named
	public List<Issue> getResolveIssues() {
		return resolveIssues;
	}
	
	public void onListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Issue member) {
		retriveAllIssues();
	}
}
