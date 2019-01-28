package com.ezypay.rest.subscription;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SubscriptionResourceTest {

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
		//fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testIsNumericDate() {
		
		String s1 = "30";
		String s2 = "02";
		String s3 ="50";
		String s4 = "0.4";
		String s5 = "test";
		assertTrue(SubscriptionResource.isNumericDate(s1));
		assertTrue(SubscriptionResource.isNumericDate(s2));
		assertFalse(SubscriptionResource.isNumericDate(s3));
		assertFalse(SubscriptionResource.isNumericDate(s4));
		assertFalse(SubscriptionResource.isNumericDate(s5));
	}

	@Test
	public final void testIsDayOfWeek() {
		String s1 = "TUESDAY";
		String s2 = "monday";
		String s3 ="monDay";
		String s4 = "sunda";
		String s5 = "test";
		String s6 = "";
		
		System.out.println(s1 +" is a day of week? "+ SubscriptionResource.isDayOfWeek(s1));
		assertTrue(SubscriptionResource.isDayOfWeek(s1));
		System.out.println(s2 +" is a day of week? "+ SubscriptionResource.isDayOfWeek(s2));
		assertTrue(SubscriptionResource.isDayOfWeek(s2));
		System.out.println(s3 +" is a day of week? "+ SubscriptionResource.isDayOfWeek(s3));
		assertTrue(SubscriptionResource.isDayOfWeek(s3));
		System.out.println(s4 +" is a day of week? "+ SubscriptionResource.isDayOfWeek(s4));
		assertFalse(SubscriptionResource.isDayOfWeek(s4));
		System.out.println(s5 +" is a day of week? "+ SubscriptionResource.isDayOfWeek(s5));
		assertFalse(SubscriptionResource.isDayOfWeek(s5));
		System.out.println(s6 +" is a day of week? "+ SubscriptionResource.isDayOfWeek(s6));
		assertFalse(SubscriptionResource.isDayOfWeek(s6));
	}

}
