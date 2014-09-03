package com.xeous.main;

import java.util.ArrayList;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Setter;

import com.vaadin.event.FieldEvents;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.Reindeer;

public class LoginView extends CustomComponent implements View{
	
	private TextField usernameField;
	private PasswordField passwordField;
	private ComboBox comboBox;
	private Button loginButton;
	private HorizontalLayout buttonsLayout;
	private FormLayout loginLayout;
	private CustomLayout mainLayout;
	private RCharachter rcharachter;
	private ShortcutListener enterShortcutListener = null;
	private DefaultErrorHandler customErrorHandler;

	@Setter(AccessLevel.PROTECTED)
	private  Map<String, String> env;

	public LoginView() {
		env = System.getenv();
	}

	
	public void enter(ViewChangeEvent event) {
	}
	
	protected void init() {
		setupShortcutListener();
		setDefaultErrorHandler();
		initLoginButton();
		initFields();
		initLayouts();
	}
	
	protected void setDefaultErrorHandler() {
		customErrorHandler = new DefaultErrorHandler() {
			@Override
			public void error(com.vaadin.server.ErrorEvent event) {

				String cause = "The action failed because:\n";
				for (Throwable t = event.getThrowable(); t != null; t = t.getCause()) {
					if (t.getCause() == null) {
						cause += t.getClass().getName();
					}
				}

				Notification.show(cause, Notification.Type.ERROR_MESSAGE);
			}
		};
		//UI.getCurrent().setErrorHandler(customErrorHandler);
	}
	
	protected void setupShortcutListener() {
		enterShortcutListener = new ShortcutListener("", ShortcutAction.KeyCode.ENTER, null) {
			@Override
			public void handleAction(Object sender, Object target) {
				login();
			}
		};
	}
	
	protected void login() {
		String userName = usernameField.getValue();
		String password = passwordField.getValue();
		try {
			if (testStringsForNullOrEmpty(userName)) {
				usernameField.focus();
				throw new IllegalArgumentException("Töltse ki a Felhasználónév mezőt!");
			} else if (testStringsForNullOrEmpty(password)) {
				passwordField.focus();
				throw new IllegalArgumentException("Töltse ki a Jelszó mezőt!");
			}
		} catch (Exception e) {
			Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
		}
		((MainUI) UI.getCurrent()).setLoggedin(true);
		UI.getCurrent().getNavigator().navigateTo("MainLayout");
		
	}
	
	private boolean testStringsForNullOrEmpty(String string) {
		if(string.isEmpty() || string.equals(null)){
			return true;
		}
		return false;
	}


	protected void initFields() {
		usernameField = new TextField("Felhasználói név:");
		passwordField = new PasswordField("Jelszó:");
		ArrayList<String> options = new ArrayList<String>();
		options.add("1");
		options.add("2");
		comboBox = new ComboBox("Things:", options);
		comboBox.setTextInputAllowed(false);
		if (comboBox != null && !comboBox.getContainerDataSource().getItemIds().isEmpty()) {
			comboBox.setValue(comboBox.getContainerDataSource().getItemIds().iterator().next());
		}
		comboBox.setNullSelectionAllowed(false);
		setUpComponents(usernameField, passwordField, comboBox);
		addShortcutListenerAdderAndRemoverListenersToField(usernameField);
		addShortcutListenerAdderAndRemoverListenersToField(passwordField);
	}

	private void addShortcutListenerAdderAndRemoverListenersToField(final AbstractTextField field) {
		field.addFocusListener(new FieldEvents.FocusListener() {
			public void focus(FieldEvents.FocusEvent event) {
				field.addShortcutListener(enterShortcutListener);
			}
		});
		field.addBlurListener(new FieldEvents.BlurListener() {
			public void blur(FieldEvents.BlurEvent event) {
				field.removeShortcutListener(enterShortcutListener);
			}
		});
	}
	
	protected void initLoginButton() {
		loginButton = new Button("Belépés", new Button.ClickListener() {
			public void buttonClick(Button.ClickEvent event) {
				login();
			}
		});

		loginButton.addStyleName(Reindeer.BUTTON_SMALL);
	}
	
	protected void initLayouts() {
		buttonsLayout = new HorizontalLayout();
		buttonsLayout.setWidth(100, Unit.PERCENTAGE);
		buttonsLayout.addComponent(loginButton);
		buttonsLayout.setComponentAlignment(loginButton, Alignment.TOP_RIGHT);

		loginLayout = new FormLayout();
		loginLayout.setSizeFull();
		loginLayout.setMargin(false);
		loginLayout.setSpacing(true);

		addComponentsToLayout(loginLayout, usernameField, passwordField, comboBox, buttonsLayout);

		mainLayout = new CustomLayout("AdminLoginWindow");
		mainLayout.setHeight(500, Unit.PIXELS);
		mainLayout.addComponent(loginLayout, "loginLayout");

		setCompositionRoot(mainLayout);
	}
	
	protected void setUpComponents(AbstractComponent... fields) {
		for (AbstractComponent field : fields) {
			field.setWidth(100, Unit.PERCENTAGE);
			field.setImmediate(true);
		}
	}
	
	@Override
	public void attach() {
		super.attach();
		init();
	}

	protected void addComponentsToLayout(Layout layout, Component... component) {
		for (Component c : component) {
			layout.addComponent(c);
		}
	}
}
