package com.company.airline.manager;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

class MessageHashMap implements IMessage {
	private static final HashMap<Locale, ResourceBundle> resources = new HashMap<Locale, ResourceBundle>();

	@Override
	public ResourceBundle getRes(Locale locale) {
		ResourceBundle resource = resources.get(locale);
		if (resource == null) {
			resource = ResourceBundle.getBundle(Message.BUNDLE_NAME, locale);
			resources.put(locale, resource);
		}
		return resource;
	}
}
