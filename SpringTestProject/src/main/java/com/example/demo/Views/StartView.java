package com.example.demo.Views;

import com.vaadin.ui.*;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;


public class StartView extends VerticalLayout implements View {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Do only on view construction
	public StartView() {
		Button loginViewButton = new Button("Logout");
		loginViewButton.addClickListener(clickEvent -> {
			MainUI.curUser = null;
			MainUI.nav.navigateTo("LoginView");
		});
		
		Button diceRollerViewButton = new Button("Dice Roller");
		diceRollerViewButton.addClickListener(clickEvent -> MainUI.nav.navigateTo("DiceRoller"));
		
		Button charCreationButton = new Button("Create a Character");
		charCreationButton.addClickListener(clickEvent -> MainUI.nav.navigateTo("CharacterCreationView"));
		
		Button listCharsButton = new Button("View all Characters");
		listCharsButton.addClickListener(clickEvent -> MainUI.nav.navigateTo("ListCharactersView"));
		
		addComponent(loginViewButton);
		addComponent(diceRollerViewButton);
		addComponent(charCreationButton);
		addComponent(listCharsButton);
	}
	
	//Do every time the view opens
	@Override
	public void enter(ViewChangeEvent event) {
		if (!MainUI.checkCreds()) {
			MainUI.curUser = null;
			MainUI.nav.navigateTo("LoginView");
		}
	}
}
