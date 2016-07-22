package com.dyrs.smjj.itracker.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Issue extends OrderBase implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@NotNull
	private String QQ;

	@NotNull
	private String mobile;

	private String title;

	@NotNull
	@Size(min = 10, message = "不能少于10个字")
	private String Content;

	@OneToMany
	private List<Attachment> attachments;

	@OneToMany
	private List<Comment> comments;

	private Category category;

	@ManyToOne
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
