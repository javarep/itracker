package com.dyrs.smjj.itracker.control;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class IssueApplication {
	private Map<String, Long> waitingIssue;

	public Map<String, Long> getWaitingIssue() {
		return waitingIssue;
	}

	public void setWaitingIssue(Map<String, Long> waitingIssue) {
		this.waitingIssue = waitingIssue;
	}

	@PostConstruct
	public void Init() {
		waitingIssue = new HashMap<String, Long>();
	}
}
