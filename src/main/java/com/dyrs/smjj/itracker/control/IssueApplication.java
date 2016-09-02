package com.dyrs.smjj.itracker.control;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

@Singleton
@Startup
@ApplicationPath("rest")
@Path("user")
public class IssueApplication extends Application {
	public IssueApplication() {
	}

	private Map<String, Long> waitingIssue;

	public Map<String, Long> getWaitingIssue() {
		return waitingIssue;
	}

	public void setWaitingIssue(Map<String, Long> waitingIssue) {
		this.waitingIssue = waitingIssue;
	}

	private Map<String, String> onLineSolver;

	private Map<String, String> currentUser;

	@PostConstruct
	public void Init() {
		waitingIssue = new HashMap<String, Long>();
		onLineSolver = new HashMap<String, String>();
		currentUser = new HashMap<String, String>();
	}

	public Map<String, String> getOnLineSolver() {
		return onLineSolver;
	}

	public void setOnLineSolver(Map<String, String> onLineSolver) {
		this.onLineSolver = onLineSolver;
	}

	public Map<String, String> getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(Map<String, String> currentUser) {
		this.currentUser = currentUser;
	}

	@GET
	@Produces("application/json")
	public Response setCurrentUser(@QueryParam("c") String category, @QueryParam("u") String user) {
		currentUser.put(category.toUpperCase(), user);
		return Response.status(200).entity("OK").build();
	}
}
