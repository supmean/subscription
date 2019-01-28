package com.ezypay.rest.subscription;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ezypay.rest.subscription.dto.Amount;
import com.ezypay.rest.subscription.dto.Subscription;

public class SubscriptionDAOTest extends SubscriptionDAO {

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
	public final void testCreateSubscription() {
		try {
			Subscription expected = new Subscription.Builder()
					.withAmount(new Amount.Builder().withCurrency(Currency.getInstance("AUD"))
							.withValue(new BigDecimal(20)).build())
					.withStartDateAndEndDate("01/01/2019", "31/03/2019").build("15");
			SubscriptionDAO sdao = new SubscriptionDAO();
			Subscription actual = sdao.createSubscription(expected);
			assertSame(expected, actual);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public final void testGetSubscription() throws Exception {
		Subscription sub = new Subscription.Builder()
				.withAmount(new Amount.Builder().withCurrency(Currency.getInstance("AUD"))
						.withValue(new BigDecimal(20)).build())
				.withStartDateAndEndDate("01/01/2019", "31/03/2019").build("15");
		SubscriptionDAO sdao = new SubscriptionDAO();
		Subscription actual = sdao.createSubscription(sub);
		Subscription expected = sdao.getSubscription(sub.getId());
		assertNotNull(expected);
		assertEquals(expected, actual);
	}

	@Test
	public final void testUpdateSubscription() {
		try {
			Subscription expected = new Subscription.Builder()
					.withAmount(new Amount.Builder().withCurrency(Currency.getInstance("AUD"))
							.withValue(new BigDecimal(20)).build())
					.withStartDateAndEndDate("01/01/2019", "31/03/2019").build("15");

			SubscriptionDAO sdao = new SubscriptionDAO();
			Subscription actual = sdao.updateSubscription(expected);
			assertSame(expected, actual);


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	}


