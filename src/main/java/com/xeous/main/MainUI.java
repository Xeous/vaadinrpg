package com.xeous.main;


import java.util.Locale;
import java.util.concurrent.locks.Lock;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

@Theme(value = "rpg")
public class MainUI extends UI{
	
	@Getter(AccessLevel.PROTECTED)
	private static final String ADMIN_SESSION_ID = "admin";
	
	@Getter
	@Setter
	private Boolean loggedin = false;
	
	@Getter
	@Setter
	private PageNavigator pageNavigator;
	
	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("Login Page");
		pageNavigator = new PageNavigator(this, this);

		setLocale(new Locale("hu", "HU"));
	}
	
}
