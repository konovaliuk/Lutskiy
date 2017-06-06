package com.company.airline.manager;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.Test;


public class TestMessageCondition {

	@Test
	public void localeMessageCondition() {
		MessageCondition instance = new MessageCondition();
		ResourceBundle resource =  instance.getRes(new Locale("fr"));
		Assert.assertEquals(new Locale("en"), resource.getLocale());
	}
	
	
	@Test
	public void localeMessageHashMap() {
		MessageHashMap instance = new MessageHashMap();
		ResourceBundle resource =  instance.getRes(new Locale("uk"));
		Assert.assertEquals(new Locale("uk"), resource.getLocale());
	}
}
