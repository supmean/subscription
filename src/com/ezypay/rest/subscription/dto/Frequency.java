package com.ezypay.rest.subscription.dto;

public enum Frequency {
	DAILY("DAILY"), WEEKLY("WEEKLY"), MONTHLY("MONTHLY"), UNDEFINED("UNDEFINED");
	private final String frequency;

	Frequency(String frequency) {
		this.frequency = frequency;
	}

	public String getFrequency() {
		return this.frequency;
	}

	public static Frequency fromString(String aFrequency) {
		for (Frequency frequency : Frequency.values()) {
			if (frequency.getFrequency().toLowerCase().equals(aFrequency.toLowerCase())) {
				return frequency;
			}
		}
		return Frequency.UNDEFINED;
	}
}
