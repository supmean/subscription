package com.ezypay.rest.subscription.dto;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ezypay.rest.subscription.SubscriptionDAO;

public class SubscriptionTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testBuilder() {
		try {
			// Monthly
			String amount = "30";
			String currency = "AUD";
			String start_date = "01/01/2019";
			String end_date = "31/03/2019";
			String charge_desc = "15";
			Subscription expected = new Subscription.Builder()
					.withAmount(new Amount.Builder().withCurrency(Currency.getInstance(currency))
							.withValue(new BigDecimal(amount)).build())
					.withStartDateAndEndDate(start_date, end_date).build(charge_desc);
			assertNotNull(expected);
			assertEquals(expected.getSubscription_type(), "MONTHLY");
			assertNotNull(expected.getId());
			assertNotNull(expected.getInovice_dates());

			
			 amount = "30";
			 currency = "AUD";
			 start_date = "01/01/2019";
			 end_date = "31/03/2019";
			 charge_desc = "TUESDAY";
			 expected = new Subscription.Builder()
					.withAmount(new Amount.Builder().withCurrency(Currency.getInstance(currency))
							.withValue(new BigDecimal(amount)).build())
					.withStartDateAndEndDate(start_date, end_date).build(charge_desc);
			assertNotNull(expected);
			assertEquals(expected.getSubscription_type(), "WEEKLY");
			assertNotNull(expected.getId());
			assertNotNull(expected.getInovice_dates());
			
			 amount = "30";
			 currency = "AUD";
			 start_date = "01/01/2019";
			 end_date = "31/03/2019";
			 charge_desc = "";
			 expected = new Subscription.Builder()
					.withAmount(new Amount.Builder().withCurrency(Currency.getInstance(currency))
							.withValue(new BigDecimal(amount)).build())
					.withStartDateAndEndDate(start_date, end_date).build(charge_desc);
			assertNotNull(expected);
			assertEquals(expected.getSubscription_type(), "DAILY");
			assertNotNull(expected.getId());
			assertNotNull(expected.getInovice_dates());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
