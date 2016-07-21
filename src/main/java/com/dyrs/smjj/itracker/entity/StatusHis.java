package com.dyrs.smjj.itracker.entity;

import java.util.Date;

public class StatusHis extends Base {
	private User user;
	private StatusEnum source;
	private StatusEnum target;
	private Date date;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public StatusEnum getSource() {
		return source;
	}

	public void setSource(StatusEnum source) {
		this.source = source;
	}

	public StatusEnum getTarget() {
		return target;
	}

	public void setTarget(StatusEnum target) {
		this.target = target;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
