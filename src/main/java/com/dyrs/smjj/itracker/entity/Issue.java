package com.dyrs.smjj.itracker.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Issue extends OrderBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7909070372971994117L;

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
	@Size(min = 6, max = 15, message = "请输入正确的QQ号")
	private String QQ;

	@NotNull
	@Size(min = 11, max = 11, message = "请输入正确的手机号")
	@Pattern(regexp = "^1[3|4|5|7|8]\\d{9}$", message = "请输入正确的手机号")
	private String mobile;

	private String title;

	@NotNull
	@Size(min = 10, max = 1024, message = "最少10个字")
	private String content;

	@OneToMany
	private List<Attachment> attachments;

	@OneToMany
	private List<Comment> comments;

	private Category category;

	@ManyToOne
	private User solvedBy;

	private Date solvedOn;

	private String solvedComment;

	private String orderNo;
	private StatusEnum status;
	
	@Transient
	private List<StatusHis> statusHis;

	@ManyToOne
	private User user;

	private Date orderDate;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public List<StatusHis> getStatusHis() {
		return statusHis;
	}

	public void setStatusHis(List<StatusHis> statusHis) {
		this.statusHis = statusHis;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

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
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Date getSolvedOn() {
		return solvedOn;
	}

	public void setSolvedOn(Date solvedOn) {
		this.solvedOn = solvedOn;
	}
}
