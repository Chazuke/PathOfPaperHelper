package com.example.demo.Views;

import org.apache.log4j.Logger;
import org.springframework.web.client.RestTemplate;

import com.example.demo.User;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;

@SpringUI
@Theme("valo")
public class MainUI extends UI{

	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;
	public static Navigator nav;
	public static User curUser;
	public static final String restURL = "http://localhost:8090/";
	public static final String restURLDb = "http://localhost:8090/db/";
	
	Logger log = Logger.getLogger(MainUI.class.getName());
	
	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("Path Of Paper Helper");
		
		nav = new Navigator(this, this);
	    
		nav.addView("LoginView", new LoginView());
		nav.addView("DiceRoller", new DiceRollerView());
		nav.addView("StartView", new StartView());
		nav.addView("RegisterView", new RegisterView());
		nav.addView("CharacterCreationView", new CharacterCreationView());
		nav.addView("ListCharactersView", new ListCharactersView());
		
		nav.navigateTo("LoginView");
	}
	
	public static boolean checkCreds() {
		if (curUser == null) return false;
		RestTemplate restTemplate = new RestTemplate();
		Boolean resp = false;
		try {
			resp = restTemplate.getForObject(restURLDb + "checkCreds?username=" + curUser.getUsername() + "&password=" + curUser.getPassword(), Boolean.class);
		}
		catch (Exception e) {
			
		}
		return resp;
	}

}
