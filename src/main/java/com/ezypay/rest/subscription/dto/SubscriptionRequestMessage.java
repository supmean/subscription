package com.ezypay.rest.subscription.dto;

import com.fasterxml.jackson.annotation.JsonInclude;


public class SubscriptionRequestMessage {
	private String amount;
	private String charge_description;
	private String start_date;
	private String end_date;

	public SubscriptionRequestMessage() {
		super();
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCharge_description() {
		return charge_description;
	}

	public void setCharge_description(String charge_description) {
		this.charge_description = charge_description;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
}
