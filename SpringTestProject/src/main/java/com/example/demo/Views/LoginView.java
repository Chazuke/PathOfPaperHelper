package com.example.demo.Views;

import com.vaadin.ui.*;

import org.springframework.web.client.RestTemplate;

import com.example.demo.User;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;


public class LoginView extends VerticalLayout implements View {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TextField username;
	private PasswordField pass;

	//Do only on view creation
	public LoginView() {
		Label instructions = new Label("Please enter your username and password");
		Button submit = new Button("Submit");
		submit.addClickListener(clickEvent -> loginClickHandler());
		Button register = new Button("Register an Account");
		register.addClickListener(clickEvent -> registerClickHandler());
		username = new TextField();
		pass = new PasswordField();
		addComponent(instructions);
		addComponent(username);
		addComponent(pass);
		addComponent(submit);
		addComponent(register);
	}
	
	//Do every time the view opens
	@Override
	public void enter(ViewChangeEvent event) {
		username.clear();
		pass.clear();
	}
	
	private boolean checkCredentials(String username, String password) {
		RestTemplate restTemplate = new RestTemplate();
		Boolean response = false;
		try {
			response = restTemplate.getForObject(MainUI.restURLDb + "checkCreds?username=" + username + "&password=" + password, Boolean.class);
		}
		catch (Exception e) {
			Notification.show("Err: " + e.getMessage());
			return false;
		}
		return response;
	}
	
	private User getUserInfo(String username, String password) {
		//RestTemplate restTemplate = new RestTemplate();
		User curUser = new User(username, password);
		try {
			/*
			ResponseEntity<Character[]> respEntity = restTemplate.getForEntity(MainUI.restURLDb+"getUserChars?username=" + 
															curUser.getUsername(), Character[].class);
			Character[] characters = respEntity.getBody();
			curUser.setCharacters(new LinkedList<Character>(Arrays.asList(characters)));
			*/
			/*
			ResponseEntity<Campaign[]> respEntity2 = restTemplate.getForEntity(MainUI.restURLDb + "getUserCamps?username=" + 
																			curUser.getUsername(), Campaign[].class);
			Campaign[] campaigns = respEntity2.getBody();
			curUser.setCampaigns(new LinkedList<Campaign>(Arrays.asList(campaigns)));
			*/
		}
		catch (Exception e) {
			
		}
		
		return curUser;
	}
	
	private void loginClickHandler() {
		if (checkCredentials(username.getValue(), pass.getValue())) {
			MainUI.curUser = getUserInfo(username.getValue(), pass.getValue());
			MainUI.nav.navigateTo("StartView");
		}
		else {
			Notification.show("Username and Password combo couldn't be found");
		}
	}
	
	private void registerClickHandler() {
		MainUI.nav.navigateTo("RegisterView");
	}
}
