package com.company.airline.manager;

import java.util.Locale;
import java.util.ResourceBundle;

public class Message {
	static final String BUNDLE_NAME = "localization.text";
	private static IMessage message = new MessageCondition();

	private Message() {
	}

	public static ResourceBundle getResource(Locale locale) {
		return message.getRes(locale);
	}
}
