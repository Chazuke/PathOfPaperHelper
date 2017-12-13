package com.example.demo.Views;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.demo.User;
import com.example.demo.Character;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class ListCharactersView extends VerticalLayout implements View {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Label errfield;
	TabSheet tab;

	public ListCharactersView() {
		Button startView = new Button("Return to Menu");
		startView.addClickListener(clickEvent -> MainUI.nav.navigateTo("StartView"));
		errfield = new Label();
		tab = new TabSheet();
		addComponent(startView);
		addComponent(tab);
		addComponent(errfield);	
	}
	
	//Do every time the view opens
	@Override
	public void enter(ViewChangeEvent event) {
		if (!MainUI.checkCreds()) {
			MainUI.curUser = null;
			MainUI.nav.navigateTo("LoginView");
		}
		tab.removeAllComponents();
		refresh();
	}
	
	private void refresh() {
		RestTemplate restTemplate = new RestTemplate();
		List<Character> chars = new ArrayList<Character>();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<User> entity = new HttpEntity<User>(MainUI.curUser, headers);
		ResponseEntity<List<Character>> resp = null;
		try {
			resp = restTemplate.exchange(MainUI.restURLDb + "getCharacters", HttpMethod.POST, entity, new ParameterizedTypeReference<List<Character>>() {});
			chars = resp.getBody();
		}
		catch (Exception e) {
			errfield.setValue(e.getMessage());
		}
		
		for (Character i : chars) {
			DisplayCharacterView charTab = new DisplayCharacterView(i);
			tab.addTab(charTab, i.getCharName());
		}
	}
}
