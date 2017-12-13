package com.example.demo.Views;

import org.springframework.web.client.RestTemplate;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class RegisterView extends VerticalLayout implements View {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TextField username;
	private PasswordField pass;
	private Label errfield;

	public RegisterView() {
		Label instructions = new Label("Please enter your desired username and password");
		errfield = new Label();
		Button submit = new Button("Submit");
		submit.addClickListener(clickEvent -> submitClickHandler());
		Button login = new Button("Return to Login");
		login.addClickListener(clickEvent -> loginClickHandler());
		
		username = new TextField();
		pass = new PasswordField();
		
		addComponent(instructions);
		addComponent(username);
		addComponent(pass);
		addComponent(submit);
		addComponent(login);
		addComponent(errfield);
	}
	
	//Do every time the view opens
	@Override
	public void enter(ViewChangeEvent event) {
		username.clear();
		pass.clear();
		errfield.setValue("");
	}
	
	private void submitClickHandler() {
		RestTemplate restTemplate = new RestTemplate();
		try {
			String response = restTemplate.getForObject("http://localhost:8090/db/createUser?username=" + 
														username.getValue() + "&password=" + 
														pass.getValue(), String.class);
			errfield.setValue(response);
		}
		catch (Exception e) {
			Notification.show("Something went wrong with user registration");
			errfield.setValue(e.getMessage());
		}
	}
	
	private void loginClickHandler() {
		MainUI.nav.navigateTo("LoginView");
	}
	
}
