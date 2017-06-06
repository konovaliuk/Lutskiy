package com.company.airline.manager;

import java.util.Locale;
import java.util.ResourceBundle;

class MessageCondition implements IMessage {
	private static final Locale UK = new Locale("uk");
	private static final Locale RU = new Locale("ru");
	private static final ResourceBundle UK_RESOURCE = ResourceBundle.getBundle(Message.BUNDLE_NAME, UK);
	private static final ResourceBundle RU_RESOURCE = ResourceBundle.getBundle(Message.BUNDLE_NAME, RU);
	private static final ResourceBundle EN_RESOURCE = ResourceBundle.getBundle(Message.BUNDLE_NAME, new Locale("en"));

	@Override
	public ResourceBundle getRes(Locale locale) {
		if (locale.getLanguage().equals(UK.getLanguage())) {
			return UK_RESOURCE;
		}
		if (locale.getLanguage().equals(RU.getLanguage())) {
			return RU_RESOURCE;
		}
		return EN_RESOURCE;
	}
}
