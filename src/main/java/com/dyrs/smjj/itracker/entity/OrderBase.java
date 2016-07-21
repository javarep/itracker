package com.dyrs.smjj.itracker.entity;

import java.util.List;

public class OrderBase extends Base {
	private String orderNo;
	private StatusEnum status;
	private List<StatusHis> statusHis;
	private User user;

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
}
