package com.crowd.mall.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class RerankTroubleShootRequest {
	private String bucketId; //bucketid r0,r1,....
	private int cityId; //城市id
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date checkIn; //入住时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date checkOut; //离店时间
	private int channel; //请求渠道: 1表示WEB, 2表示app, 8表示微信
	private int serviceSeq; //服务序号,0表示请求线上,1表示请求灰度58
	private String keyWord; //关键词,非必须,不设置为空
	private int districtType; //区域位置类型,非必须,0表示不限, 1表示商圈,2表示行政区
	private int districtId; //如果districtType设定为非0值,此id对应的就是区域位置id
	private String deviceId; //设备id, 必填
	
	public String getBucketId() {
		return bucketId;
	}
	public void setBucketId(String bucketId) {
		this.bucketId = bucketId;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public Date getCheckIn() {
		return checkIn;
	}
	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}
	public Date getCheckOut() {
		return checkOut;
	}
	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}
	public int getChannel() {
		return channel;
	}
	public void setChannel(int channel) {
		this.channel = channel;
	}
	public int getServiceSeq() {
		return serviceSeq;
	}
	public void setServiceSeq(int serviceSeq) {
		this.serviceSeq = serviceSeq;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public int getDistrictType() {
		return districtType;
	}
	public void setDistrictType(int districtType) {
		this.districtType = districtType;
	}
	public int getDistrictId() {
		return districtId;
	}
	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
}
