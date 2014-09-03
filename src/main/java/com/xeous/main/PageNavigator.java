package com.xeous.main;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.SingleComponentContainer;
import com.vaadin.ui.UI;

public class PageNavigator extends Navigator {

	public PageNavigator(UI ui, SingleComponentContainer container) {
		super(ui, container);
		this.addView("login", LoginView.class);
		this.addView("MainLayout", MainView.class);
		this.navigateTo("login");
	}

	@Override
	public void navigateTo(String navigationState) {
		if (((MainUI) UI.getCurrent()).getLoggedin() == true) {
			super.navigateTo("MainLayout");
		}
		else {
			super.navigateTo("login");
		}
	}
}
