package com.coolweather.app.model;

public class Province {
	private int id;
	private String provience_name;
	private String provience_code;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id=id;
	}
	public String getProvienceName() {
		return provience_name;
	}
	public void setProvienceName(String provienceName) {
		this.provience_name=provienceName;
	}
	public String getProvienceCode() {
		return provience_code;
	}
	public void setProvienceCode(String provienceCode) {
		this.provience_code=provienceCode;
	}
}
