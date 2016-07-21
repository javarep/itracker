package com.dyrs.smjj.itracker.control;

import com.dyrs.smjj.itracker.entity.SeatType;

import javax.ejb.Stateless;


@Stateless
public class SeatTypeDao extends AbstractDao<SeatType> {

    public SeatTypeDao() {
        super(SeatType.class);
    }
}
