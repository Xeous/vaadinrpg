package com.xeous.main;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class MainView extends CustomComponent implements View {

	private VerticalLayout mainLayout;
	private TextArea messagePanel;
	private Button sendButton;
	private HorizontalLayout buttonsLayout;
	private VerticalLayout textLayout;
	private TextField enterField;
	private MenuBar barmenu;
	private Label selection = new Label("-");
	private TabSheet tabSheet;
	private Dashboard dashboard;

	public void enter(ViewChangeEvent event) {
	}

	public MainView() {
		super();
		tabSheet = new TabSheet();
		setup();
	}

	private void setup() {
		mainLayout = new VerticalLayout();
		mainLayout.setMargin(true);
		mainLayout.setSizeFull();
		mainLayout.setSpacing(true);
		
		dashboard = new Dashboard();
		dashboard.setSizeFull();
		tabSheet = new TabSheet();
		tabSheet.addTab("Ruhafalva", dashboard, false);
		tabSheet.setSizeFull();
		mainLayout.addComponent(tabSheet);
		mainLayout.setExpandRatio(tabSheet, 1.0f);
		mainLayout.setSizeFull();
		setSizeFull();
		setCompositionRoot(mainLayout);
		
	//	initFields();
	//	initLayouts();
	}

	protected void initLayouts() {
		buttonsLayout = new HorizontalLayout();
		buttonsLayout.setWidth(20, Unit.PERCENTAGE);
		buttonsLayout.addComponent(sendButton);
		
		messagePanel.setSizeFull();

		textLayout = new VerticalLayout();
		textLayout.setSizeFull();
		textLayout.setMargin(true);
		textLayout.setSpacing(true);
		textLayout.addComponent(messagePanel);

		mainLayout = new VerticalLayout();
		mainLayout.setMargin(true);
		mainLayout.setSizeFull();
		mainLayout.setSpacing(true);
		mainLayout.addComponent(barmenu);
		mainLayout.addComponent(selection);
		mainLayout = new VerticalLayout();
		mainLayout.addComponent(textLayout);
		mainLayout.addComponent(buttonsLayout);
		mainLayout.addComponent(enterField);

		setCompositionRoot(mainLayout);
	}

	protected void initFields() {
		
		enterField = new TextField();
		messagePanel = new TextArea("Area");

		messagePanel.setValue("Greetings adventurer!");
		messagePanel.setReadOnly(true);
		sendButton = new Button("Send the message");
		setupMenu();

		sendButton.addListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				doevent(enterField.getValue());
			}
		});
	}

	protected void setupMenu() {
		barmenu = new MenuBar();

		MenuBar.Command mycommand = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				selection.setValue("Ordered a " + selectedItem.getText()
						+ " from menu.");
			}
		};
		MenuBar.MenuItem character = barmenu.addItem("Character", null, null);
		character.addItem("Items", null, mycommand);
		character.addItem("Character Page", null, mycommand);
		character.addItem("Map", null, mycommand);
		
	}

	protected void doevent(String value) {
		messagePanel.setReadOnly(false);
		messagePanel.setValue(value);
		messagePanel.setReadOnly(true);
	}

	protected void setUpComponents(AbstractComponent... fields) {
		for (AbstractComponent field : fields) {
			field.setWidth(100, Unit.PERCENTAGE);
			field.setImmediate(true);
		}
	}

	protected void addComponentsToLayout(Layout layout, Component... component) {
		for (Component c : component) {
			layout.addComponent(c);
		}
	}

}
