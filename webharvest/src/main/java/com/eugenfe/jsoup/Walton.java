package com.eugenfe.jsoup;

public class Walton {
	private String ownerName;
	private String mailAddress;
	private String mailAddress1;
//	private String mailAddress2;
	private String lacationAddress;
	private String parcelNo;
	private String marketValue;
	
	public Walton(String ownerName, String mailAddress, String mailAddress1, 
			String lacationAddress, String parcelNo, String marketValue) {
		this.ownerName = ownerName;
		this.mailAddress = mailAddress;
		this.mailAddress1 = mailAddress1;
//		this.mailAddress2 = mailAddress2;
		this.lacationAddress = lacationAddress;
		this.parcelNo = parcelNo;
		this.marketValue = marketValue;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getMailAddress1() {
		return mailAddress1;
	}

	public void setMailAddress1(String mailAddress1) {
		this.mailAddress1 = mailAddress1;
	}

//	public String getMailAddress2() {
//		return mailAddress2;
//	}
//
//	public void setMailAddress2(String mailAddress2) {
//		this.mailAddress2 = mailAddress2;
//	}

	public String getLacationAddress() {
		return lacationAddress;
	}

	public void setLacationAddress(String lacationAddress) {
		this.lacationAddress = lacationAddress;
	}

	public String getParcelNo() {
		return parcelNo;
	}

	public void setParcelNo(String parcelNo) {
		this.parcelNo = parcelNo;
	}

	public String getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(String marketValue) {
		this.marketValue = marketValue;
	}

	
}
