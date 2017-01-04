package com.crowd.mall.domain;

import java.io.Serializable;
import java.util.List;

public class WeightScore implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	private List<HotelInfo> hotels;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<HotelInfo> getHotels() {
		return hotels;
	}
	public void setHotels(List<HotelInfo> hotels) {
		this.hotels = hotels;
	}
	
}
