package com.dyrs.smjj.itracker.controller;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.dyrs.smjj.itracker.boundary.TheatreBox;
import com.dyrs.smjj.itracker.entity.Seat;
import com.google.common.collect.Lists;

@Model
public class TheatreInfo {
	@Inject
	private Logger logger;

	@Inject
	private TheatreBox box;

	private Collection<Seat> seats;

	@PostConstruct
	public void retrieveAllSeatOrderedByName() {
		seats = box.getSeats();
	}

	@Produces
	@Named
	public Collection<Seat> getSeats() {
		return Lists.newArrayList(seats);
	}

	public void onMemberListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Seat member) {
		retrieveAllSeatOrderedByName();
	}
}
