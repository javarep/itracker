package com.dyrs.smjj.itracker.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.dyrs.smjj.itracker.boundary.TheatreBox;

@Named
@SessionScoped
public class TheatreBooker implements Serializable {
	@Inject
	private Logger logger;

	@Inject
	private TheatreBox theatreBox;

	@Inject
	private FacesContext facesContext;

	private int money;

	@PostConstruct
	public void createCustomer() {
		this.money = 100;
	}

	public void bookSeat(int seatId) {
		logger.info("Booking seat " + seatId);
		int seatPrice = theatreBox.getSeatPrice(seatId);
		if (seatPrice > money) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not enough Money!",
					"Registration unsuccessful");
			facesContext.addMessage(null, message);
			return;
		}
		theatreBox.buyTicket(seatId);
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Booked!", "Booking successful");
		facesContext.addMessage(null, message);
		logger.info("Seat booked.");
		money -= seatPrice;
	}

	public int getMoney() {
		return money;
	}
}
