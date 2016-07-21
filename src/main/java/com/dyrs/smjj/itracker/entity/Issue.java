package com.dyrs.smjj.itracker.entity;

import java.util.List;

public class Issue extends OrderBase {
	private String QQ;
	private String mobile;
	private String title;
	private String Content;
	private List<Attachment> attachments;
	private List<Comment> comments;
	private String assignTo;
	private User solvedBy;
	private String solvedComment;

	public String getQQ() {
		return QQ;
	}

	public void setQQ(String qQ) {
		QQ = qQ;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public String getAssignTo() {
		return assignTo;
	}

	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}

	public User getSolvedBy() {
		return solvedBy;
	}

	public void setSolvedBy(User solvedBy) {
		this.solvedBy = solvedBy;
	}

	public String getSolvedComment() {
		return solvedComment;
	}

	public void setSolvedComment(String solvedComment) {
		this.solvedComment = solvedComment;
	}
}
