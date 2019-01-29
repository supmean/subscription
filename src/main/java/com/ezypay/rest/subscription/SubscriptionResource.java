package com.ezypay.rest.subscription;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Arrays;
import java.util.Currency;
import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ParamException;

import com.ezypay.rest.subscription.dto.Amount;
import com.ezypay.rest.subscription.dto.Subscription;
import com.ezypay.rest.subscription.dto.SubscriptionRequestMessage;
import com.ezypay.rest.subscription.exception.ApplicationException;

@Path("/subscription")
public class SubscriptionResource {
	private static final Logger logger = Logger.getLogger(SubscriptionResource.class);
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
	public final SubscriptionDAO subscriptionDao = new SubscriptionDAO();

	/**
	 * @param amount
	 * @param charge_description
	 * @param start_date
	 * @param end_date
	 * @return
	 * @throws ApplicationException
	 */
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/create")
	public Subscription createSubscription(SubscriptionRequestMessage subRequestMessage) throws ApplicationException {

		// validation section is needed for request parameters
		Subscription subscription;
		// Build Amount
		Amount amt = new Amount.Builder().withCurrency(Currency.getInstance("AUD"))
				.withValue(new BigDecimal(subRequestMessage.getAmount())).build();
		String charge_desc = subRequestMessage.getCharge_description().trim().toUpperCase();
		String subscription_type;
		try {
			formatter.parse(subRequestMessage.getStart_date());
			formatter.parse(subRequestMessage.getEnd_date());

		} catch (DateTimeParseException e) {
			System.err.println("Invalid date format");
			throw new IllegalArgumentException("Invalid date format");

		}
		// subscription type logic and construct Subscription at one time
		// charge description must not be null in parameter
		if (charge_desc != null && "".equals(charge_desc)) {
			subscription_type = "DAILY";

		} else if (isNumericDate(charge_desc)) {
			subscription_type = "MONTHLY";

		} else if (isDayOfWeek(charge_desc)) {
			subscription_type = "WEEKLY";
		} else {
			throw new IllegalArgumentException("charge description is invalid");
		}
		// Construct Subscription
		try {
			subscription = new Subscription.Builder().withAmount(amt).withSubscription_type(subscription_type)
					.withStartDateAndEndDate(subRequestMessage.getStart_date(), subRequestMessage.getEnd_date())
					.build(charge_desc);
		} catch (Exception exception) {
			logger.error(exception.getMessage());
			throw new ApplicationException();
		}
		// Set the invoice date
		return subscription;

	}

	/**
	 * Take a string to check whether or not it is valid for the format of numeric
	 * date in month
	 * 
	 * @param s
	 * @return
	 */
	public final static boolean isNumericDate(String s) {
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
	public final static boolean isDayOfWeek(String s) {
		boolean result = false;
		if (s != null && !"".equals(s.trim().toUpperCase())) {
			for (DayOfWeek d : DayOfWeek.values()) {
				if (d.name().contentEquals(s.toUpperCase())) {
					result = true;
				}
			}
		}
		return result;

	}

	public static void main(String[] args) {
		String s = "30".toUpperCase();
		System.out.println(s + " is a date number? " + isNumericDate(s));
		String d = "tues".toUpperCase();
		System.out.println(d + " is a day of week? " + isDayOfWeek(d));

	}
}
