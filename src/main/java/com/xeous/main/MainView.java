package com.xeous.main;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

public class MainView extends CustomComponent implements View{
	
	private VerticalLayout layout = new VerticalLayout();
	private Menu menu;
	private TabSheet tabSheet;
	private Dashboard dashboard;
	
	public void enter(ViewChangeEvent event) {
	}

}
