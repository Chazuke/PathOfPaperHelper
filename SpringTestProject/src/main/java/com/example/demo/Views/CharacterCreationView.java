package com.example.demo.Views;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.demo.User;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

public class CharacterCreationView extends GridLayout implements View {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	TextField nameIn;
	TextField descIn;
	Label display;

	//Do once on view creation
	public CharacterCreationView() {
		setRows(6);
		setColumns(6);
		Label name = new Label("Character Name (Required):");
		Label desc = new Label("Description (Optional):");
		display = new Label();
		Button submit = new Button("Submit");
		submit.addClickListener(clickEvent -> submitClickHandler());
		Button startView = new Button("Return to Menu");
		startView.addClickListener(clickEvent -> MainUI.nav.navigateTo("StartView"));
		nameIn = new TextField();
		descIn = new TextField();
		descIn.setMaxLength(140);
		nameIn.setMaxLength(30);
		
		addComponent(name, 0, 0);
		addComponent(desc, 0, 1);
		
		addComponent(nameIn, 1, 0);
		addComponent(descIn, 1, 1);
		
		addComponent(submit, 0, 2);
		addComponent(display, 1, 2);
		addComponent(startView, 0, 3);
	}
	
	//Do every time the view is displayed
	@Override
	public void enter(ViewChangeEvent event) {
		if (!MainUI.checkCreds()) {
			MainUI.curUser = null;
			MainUI.nav.navigateTo("LoginView");
		}
		nameIn.clear();
		descIn.clear();
		display.setValue("");
	}
	
	private void submitClickHandler() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<User> entity = new HttpEntity<User>(MainUI.curUser, headers);
		ResponseEntity<String> resp = null;
		try {
			if (descIn.isEmpty()) resp = restTemplate.exchange(MainUI.restURLDb + "createCharacter?name=" + nameIn.getValue(), HttpMethod.POST, entity, String.class);
			else resp = restTemplate.exchange(MainUI.restURLDb + "createCharacter?name=" + nameIn.getValue() + "&description=" + descIn.getValue(), HttpMethod.POST, entity, String.class);
			display.setValue(resp.getBody());
		}
		catch (Exception e) {
			display.setValue(e.getMessage());
		}
	}
}
