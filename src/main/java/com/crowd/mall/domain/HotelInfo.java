package com.crowd.mall.domain;

import java.io.Serializable;

public class HotelInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String hid;
	private Float score;
	private String price;
	public String getHid() {
		return hid;
	}
	public void setHid(String hid) {
		this.hid = hid;
	}
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
}
