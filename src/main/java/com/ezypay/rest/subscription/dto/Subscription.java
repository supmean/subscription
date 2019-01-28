package com.ezypay.rest.subscription.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ezypay.rest.subscription.dto.Amount;

public class Subscription {
	private String id;
	private Amount amount;
	private String subscription_type;
	private String[] inovice_dates;

	@JsonIgnore
	private String start_date;
	@JsonIgnore
	private String end_date;

	/**
	 * Constructor to create Subscription with UUID
	 */
	public Subscription() {
		super();
		UUID uuid = UUID.randomUUID();
		this.id = uuid.toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Amount getAmount() {
		return amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	public String getSubscription_type() {
		return subscription_type;
	}

	public void setSubscription_type(String subscription_type) {
		this.subscription_type = subscription_type;
	}

	public String[] getInovice_dates() {
		return inovice_dates;
	}

	public void setInovice_dates(String[] inovice_dates) {
		this.inovice_dates = inovice_dates;
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

	private Subscription(Builder builder) {
		this.id = UUID.randomUUID().toString();
		this.amount = builder.amount;
		this.subscription_type = builder.subscription_type;
		this.inovice_dates = builder.inovice_dates;

		this.start_date = builder.start_date;
		this.end_date = builder.end_date;
	}

	public static final String DATE_FORMAT = "dd/MM/yyyy";

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private Amount amount;
		private String subscription_type;
		private String[] inovice_dates;
		private List<LocalDate> invoice_dates_list;
		private String start_date;
		private LocalDate startD;
		private String end_date;
		private LocalDate endD;
		private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

		public Builder withAmount(Amount amount) {
			this.amount = amount;
			return this;
		}

		public Builder withSubscription_type(String subscription_type) {
			this.subscription_type = subscription_type;
			return this;
		}

		public Builder withStartDateAndEndDate(String start_date, String end_date) throws Exception {
			if ((start_date == null) || (end_date == null))
				throw new IllegalArgumentException("date is null");

			this.startD = LocalDate.parse(start_date, formatter);
			this.endD = LocalDate.parse(end_date, formatter);
			if (startD.isAfter(endD))
				throw new IllegalArgumentException("dates are in wrong order");
			if (!startD.plusMonths(3).isAfter(endD))
				throw new IllegalArgumentException("Time span is over three months");
			this.start_date = start_date;
			this.end_date = end_date;
			return this;
		}

		public Subscription build(String charge_desc) throws Exception {
			setInvoiceDate(charge_desc);
			return new Subscription(this);
		}

		/**
		 * Set invoice dates in all the related properties in Subscription
		 * 
		 * @param subs
		 * @param formatter
		 * @param invoice_dates_list
		 */

		private String[] formatAndConvertLocalDates(List<LocalDate> invoice_dates_list) {
			String[] invoice_dates;

			invoice_dates = new String[invoice_dates_list.size()];
			for (int i = 0; i < invoice_dates_list.size(); i++) {
				invoice_dates[i] = invoice_dates_list.get(i).format(formatter);// It has to be formatted
			}
			System.out.println("Invoice date in String[]: " + Arrays.toString(invoice_dates));
			return invoice_dates;
		}

		/**
		 * Take a string to check whether or not it is valid for the format of numeric
		 * date in month
		 * 
		 * @param s
		 * @return
		 */
		public boolean isNumericDate(String s) {
			if (s != null && !"".equals(s.trim()))
				return s.matches("^((0?[1-9])|((1|2)[0-9])|30|31)$");
			else
				return false;
		}

		/**
		 * Take a string to check whether or not it can be parsed as any day of week
		 * 
		 * @param s
		 * @return
		 */
		public boolean isDayOfWeek(String s) {

			if (s != null && !"".equals(s.trim().toUpperCase())) {
				try {
					DayOfWeek.valueOf(s);
					return true;
				} catch (IllegalArgumentException ex) {
					return false;
				}
			} else
				return false;

		}

		private void setInvoiceDate(String charge_desc) throws Exception {

			// Use start_date, end_date and frequency to create invoice_dates array
			if (charge_desc != null && "".equals(charge_desc)) {
				this.subscription_type = "DAILY";

			} else if (isNumericDate(charge_desc)) {
				this.subscription_type = "MONTHLY";

			} else if (isDayOfWeek(charge_desc)) {
				this.subscription_type = "WEEKLY";
			} else {
				throw new IllegalArgumentException("charge description is invalid");
			}

			switch (this.subscription_type) {

			case "DAILY": {
				// Daily: everyday, from start_date to end_date
				this.invoice_dates_list = startD.datesUntil(endD.plusDays(1), Period.ofDays(1))
						.collect(Collectors.toList());
				this.inovice_dates = formatAndConvertLocalDates(invoice_dates_list);

			}
				break;

			case "WEEKLY": {
				// Extract the day of week from charge_desc
				try {
					DayOfWeek charge_day = DayOfWeek.valueOf(charge_desc);
					// i.e. First occurrence of the specified day
					this.invoice_dates_list = startD.with(TemporalAdjusters.nextOrSame(charge_day))
							.datesUntil(endD.plusDays(1), Period.ofWeeks(1)).collect(Collectors.toList());
					this.inovice_dates = formatAndConvertLocalDates(invoice_dates_list);
				} catch (IllegalArgumentException ex) {
					System.err.println("Incorrect day of week in charge descripion." + ex);

				}

			}
				break;
			case "MONTHLY": {
				// find the corresponding invoice date of current month.
				try {
					LocalDate charge_date = startD.withDayOfMonth(Integer.valueOf(charge_desc));

					// Start date before the specified charge date in charge description, both in
					// same month
					if (startD.isBefore(charge_date)) {
						this.invoice_dates_list = charge_date.datesUntil(endD.plusDays(1), Period.ofMonths(1))
								.collect(Collectors.toList());

					} else {
						// Start date after charge date, first charge occurrence is in the coming month.
						this.invoice_dates_list = charge_date.plusMonths(1)
								.datesUntil(endD.plusDays(1), Period.ofMonths(1)).collect(Collectors.toList());
					}
					this.inovice_dates = formatAndConvertLocalDates(invoice_dates_list);

				} catch (IllegalArgumentException ex) {
					System.err.println("Incorrect format for date in charge descripion." + ex);

				}

			}
				break;
			default:
				throw new IllegalArgumentException();

			}

		}

	}

}
