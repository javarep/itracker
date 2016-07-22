package com.dyrs.smjj.itracker.controller;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.dyrs.smjj.itracker.control.IssueDao;
import com.dyrs.smjj.itracker.entity.Category;
import com.dyrs.smjj.itracker.entity.Issue;
import com.dyrs.smjj.itracker.entity.StatusEnum;

@Model
public class IssueService {
	@Inject
	private FacesContext facesContext;

	@Inject
	private IssueDao issueDao;

	@Inject
	private Event<Issue> issueEventSrc;

	@Produces
	@Named
	private Issue newIssue;

	@PostConstruct
	public void initNewIssue() {
		newIssue = new Issue();
		newIssue.setStatus(StatusEnum.Waiting);
	}

	public List<Category> getCategories() {
		return Arrays.asList(Category.values());
	}

	public void addNewIssus() throws Exception {
		try {
			createIssue(newIssue);

			final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "OK!", "问题提交成功");
			facesContext.addMessage(null, m);
			initNewIssue();
		} catch (Exception e) {
			final String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Error while saving data");
			facesContext.addMessage(null, m);
		}
	}

	public void createIssue(Issue issue) throws Exception {
		issueDao.persist(issue);
		issueEventSrc.fire(issue);
	}

	private String getRootErrorMessage(Exception e) {
		// Default to general error message that registration failed.
		String errorMessage = "提交问题  失败 . 查看服务器错误日志，获得更多帮助";
		if (e == null) {
			// This shouldn't happen, but return the default messages
			return errorMessage;
		}

		// Start with the exception and recurse to find the root cause
		Throwable t = e;
		while (t != null) {
			// Get the message from the Throwable class instance
			errorMessage = t.getLocalizedMessage();
			t = t.getCause();
		}
		// This is the root cause message
		return errorMessage;
	}
}
