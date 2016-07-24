package com.dyrs.smjj.itracker.controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jsoup.Jsoup;

import com.dyrs.smjj.itracker.control.IssueApplication;
import com.dyrs.smjj.itracker.control.IssueDao;

import com.dyrs.smjj.itracker.entity.Issue;
import com.dyrs.smjj.itracker.entity.StatusEnum;
import com.dyrs.smjj.itracker.filter.LoginBean;

@Model
public class IssueService {
	@Inject
	private FacesContext facesContext;

	@Inject
	private IssueDao issueDao;

	@Inject
	private IssueApplication application;

	@Inject
	private LoginBean loginBean;

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

	public String getIssues() throws Exception {
		Map<String, Long> map = application.getWaitingIssue();
		String key = loginBean.getCategory();
		if (map.containsKey(key)) {
			long id = map.get(key);
			return issueDao.find(id).getContent();
		}
		throw new Exception();
	}

	public String getMessage() {
		Map<String, Long> map = application.getWaitingIssue();
		String key = loginBean.getCategory();
		if (map.containsKey(key)) {
			long id = map.get(key);
			map.remove(key);
			String content = issueDao.find(id).getContent();
			String plain = Jsoup.parse(content).text();
			return plain;
		}

		return null;
	}

	public void addNewIssus() throws Exception {
		try {
			createIssue(newIssue);
			// Init UserSession
			loginBean.setMobile(newIssue.getMobile());
			application.getWaitingIssue().put(newIssue.getCategory(), newIssue.getId());
			final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "OK!", "问题提交成功");
			facesContext.addMessage(null, m);
			initNewIssue();
		} catch (Exception e) {
			final String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Error while saving data");
			facesContext.addMessage(null, m);
		}
	}

	public String solveIssue(long id) {
		Issue issue = issueDao.find(id);
		if (issue.getStatus() == StatusEnum.Waiting) {
			issue.setStatus(StatusEnum.Processing);
			issue.setSolvedBy(loginBean.getUser());
		} else if (issue.getStatus() == StatusEnum.Processing) {
			issue.setStatus(StatusEnum.Completed);
			// issue.setSolvedBy(loginBean.getUser());
			issue.setSolvedOn(new java.util.Date());
		}

		issueDao.edit(issue);
		return "success";
	}

	public String completeIssue(long id, String comment) throws Exception {
		Issue issue = issueDao.find(id);
		if (issue.getStatus() == StatusEnum.Processing) {
			issue.setStatus(StatusEnum.Completed);
			// issue.setSolvedBy(loginBean.getUser());
			issue.setSolvedOn(new java.util.Date());
			issue.setSolvedComment(comment);
		}

		issueDao.edit(issue);

		return "success";
	}

	public void transferIssue(String category, long id) {

	}

	public String getStatus(StatusEnum status) {
		switch (status) {
		case Waiting:
			return "等待处理";
		case Processing:
			return "处理中";
		case Completed:
			return "处理完成";

		default:
			return "未知";
		}
	}

	public StatusEnum getOriStatus(String status) {
		switch (status) {
		case "等待处理":
			return StatusEnum.Waiting;
		case "处理中":
			return StatusEnum.Processing;
		case "处理完成":
			return StatusEnum.Completed;

		default:
			return StatusEnum.Waiting;
		}
	}

	public void createIssue(Issue issue) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String sdt = df.format(new Date(System.currentTimeMillis()));
		issue.setOrderNo(issue.getCategory() + sdt);
		issue.setOrderDate(new java.util.Date());
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