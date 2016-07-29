/**
 * 
 */
package com.dyrs.smjj.itracker.entity;

/**
 * @author 王春雷
 *
 */
public enum StatusEnum {
	Waiting(0), Processing(1), Completed(2), Repeat(3), Refused(4);
	
	private final int id;

	StatusEnum(int id) {
		this.id = id;
	}

	public int getValue() {
		return id;
	}
}
