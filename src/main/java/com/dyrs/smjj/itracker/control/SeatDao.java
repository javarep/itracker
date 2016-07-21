package com.dyrs.smjj.itracker.control;

import com.dyrs.smjj.itracker.entity.Seat;

import javax.ejb.Stateless;


@Stateless
public class SeatDao extends AbstractDao<Seat> {

    public SeatDao() {
        super(Seat.class);
    }
}
