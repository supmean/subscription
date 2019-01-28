package com.ezypay.rest.subscription;

import org.glassfish.jersey.server.ResourceConfig;

import com.ezypay.rest.subscription.exception.ApplicationException;
import com.ezypay.rest.subscription.filter.LoggingResponseFilter;

public class SubsApplication extends ResourceConfig {
	public SubsApplication() {
		// register application resources
		register(SubscriptionResource.class);

		// register filters
		register(LoggingResponseFilter.class);

		// register exception mappers
		register(ApplicationException.class);

		// register features

	}
}
