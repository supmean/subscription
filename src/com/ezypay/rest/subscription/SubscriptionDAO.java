package com.ezypay.rest.subscription;

import java.util.concurrent.ConcurrentHashMap;

import com.ezypay.rest.subscription.dto.Subscription;

public class SubscriptionDAO {
	ConcurrentHashMap<String, Subscription> fakeDB = new ConcurrentHashMap<>();

	public Subscription createSubscription(Subscription subs) {
		String id = subs.getId();
		fakeDB.put(id, subs);
		return fakeDB.get(id);
	}
	public Subscription getSubscription(String id) {
		return fakeDB.get(id);
	}
	public Subscription updateSubscription(Subscription subs) {
		String id = subs.getId();
		fakeDB.put(id, subs);
		return fakeDB.get(id);
		
	}
	
}
