package com.dyrs.smjj.itracker.controller;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.dyrs.smjj.itracker.entity.Category;
import com.dyrs.smjj.itracker.entity.Issue;
import com.dyrs.smjj.itracker.entity.SeatPosition;

@Model
public class IssueService {
	@Inject
	private FacesContext facesContext;
	
	@Produces
	@Named
	private Issue newIssue;
	
	@PostConstruct
	public void initNewIssue(){
		newIssue=new Issue();
	}
	
	public List<Category> getCategories() {
        return Arrays.asList(Category.values());
    }
}
