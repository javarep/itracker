package com.dyrs.smjj.itracker.entity;

import java.io.Serializable;
import java.security.KeyStore.PrivateKeyEntry;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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

	@PostConstruct
	public void init() {
		comments = new ArrayList<Comment>();
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
	@Lob
	private String content;

	@OneToMany
	private List<Attachment> attachments;

	@OneToMany
	private List<Comment> comments;

	@NotNull
	private String category;

	private String subCategory;

	@NotNull
	private String issueType;

	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	@ManyToOne
	private User solvedBy;

	private Date solvedOn;

	@Lob
	private String solvedComment;

	private String transferBy;

	public String getTransferBy() {
		return transferBy;
	}

	public void setTransferBy(String transferBy) {
		this.transferBy = transferBy;
	}

	public Date getTransferOn() {
		return transferOn;
	}

	public void setTransferOn(Date transferOn) {
		this.transferOn = transferOn;
	}

	public String getTransferComment() {
		return transferComment;
	}

	public void setTransferComment(String transferComment) {
		this.transferComment = transferComment;
	}

	private Date transferOn;
	private String transferComment;

	private String orderNo;
	private StatusEnum status;

	private String department;
	private String job;
	private String userName;
	private String customer;
	private String projectNo;

	private long ownerId;
	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public boolean isHolding() {
		return holding;
	}

	public void setHolding(boolean holding) {
		this.holding = holding;
	}

	private boolean holding;

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Transient
	private List<StatusHis> statusHis;

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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getSolvedOn() {
		return solvedOn;
	}

	public void setSolvedOn(Date solvedOn) {
		this.solvedOn = solvedOn;
	}

	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
}
