package com.xeous.main;

import java.util.ArrayList;
import java.util.Map;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class MainView extends CustomComponent implements View{
	
	private VerticalLayout mainLayout;
	private TextArea messagePanel;
	private Button sendButton;
	private HorizontalLayout buttonsLayout;
	private VerticalLayout textLayout;
	private TextField enterField;
	private MenuBar barmenu;
	private Label selection = new Label("-");
	
	public void enter(ViewChangeEvent event) {
	}
	
	public MainView() {
		super();
		setup();
	}

	private void setup() {
		initFields();
		initLayouts();
	}
	
	protected void initLayouts() {
		buttonsLayout = new HorizontalLayout();
		buttonsLayout.setWidth(20, Unit.PERCENTAGE);
		buttonsLayout.addComponent(sendButton);
		buttonsLayout.setComponentAlignment(sendButton, Alignment.TOP_LEFT);
		
		textLayout = new VerticalLayout();
		textLayout.setSizeFull();
		textLayout.setMargin(false);
		textLayout.setSpacing(true);
		textLayout.addComponent(messagePanel);
		
		mainLayout= new VerticalLayout();
		mainLayout.addComponent(barmenu);
		mainLayout.addComponent(selection);
		mainLayout = new VerticalLayout();
		mainLayout.addComponent(textLayout);
		mainLayout.addComponent(buttonsLayout);
		mainLayout.addComponent(enterField);

		setCompositionRoot(mainLayout);
	}
	
	protected void initFields() {
		enterField= new TextField();
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
		        selection.setValue("Ordered a " +
		                selectedItem.getText() +
		                " from menu.");
		    }  
		};
		MenuBar.MenuItem beverages =
			    barmenu.addItem("Beverages", null, null);
			MenuBar.MenuItem hot_beverages =
			    beverages.addItem("Hot", null, null);
			hot_beverages.addItem("Tea", null, mycommand);
			hot_beverages.addItem("Coffee", null, mycommand);
			MenuBar.MenuItem cold_beverages =
			    beverages.addItem("Cold", null, null);
			cold_beverages.addItem("Milk", null, mycommand);
			cold_beverages.addItem("Weissbier", null, mycommand);

			MenuBar.MenuItem snacks =
			    barmenu.addItem("Snacks", null, null);
			snacks.addItem("Weisswurst", null, mycommand);
			snacks.addItem("Bratwurst", null, mycommand);
			snacks.addItem("Currywurst", null, mycommand);
			        
			MenuBar.MenuItem services =
			    barmenu.addItem("Services", null, null);
			services.addItem("Car Service", null, mycommand);
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
