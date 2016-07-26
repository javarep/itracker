package com.dyrs.smjj.itracker.filter;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Null;

import com.dyrs.smjj.itracker.control.IssueApplication;
import com.dyrs.smjj.itracker.control.UserDao;
import com.dyrs.smjj.itracker.entity.User;

@Named
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 7765876811740798583L;

	private String username;
	private String password;
	private String msg;
	private int count;
	private String category;
	private String mobile;
	private String department;
	@Inject
	private UserDao userDao;

	@Inject
	private IssueApplication application;

	private User user;

	@Produces
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Produces
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String login() {
		user = userDao.findByCode(username);
		if (user == null) {
			user = new User();
			user.setCode(username);
			user.setName(username);
			user.setPassword(password);
			userDao.persist(user);
		} else if (!user.getPassword().equals(password)) {
			msg = "密码错误！";
			return "login";
		}

		application.getOnLineSolver().put(category, username);
		FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("login", this);
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
				.getResponse();
		try {
			response.sendRedirect("/itracker/faces/views/resolve.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("login", null);

		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
				.getResponse();

		try {
			response.sendRedirect("/itracker/faces/views/resolve.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "login";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Produces
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
}
