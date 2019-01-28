package com.ezypay.rest.subscription.dto;

import java.math.BigDecimal;
import java.util.Currency;

public class Amount {
	private BigDecimal value;
	private Currency currency;

	public Amount(BigDecimal value, Currency currency) {
		super();
		this.value = value;
		this.currency = currency;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Amount() {
	}

	private Amount(Builder builder) {
		this.value = builder.value;
		this.currency = builder.currency;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private BigDecimal value;
		private Currency currency;

		public Builder withValue(BigDecimal value) {
			this.value = value;
			return this;
		}

		public Builder withCurrency(Currency currency) {
			this.currency = currency;
			return this;
		}

		public Amount build() {
			return new Amount(this);
		}
	}

}
