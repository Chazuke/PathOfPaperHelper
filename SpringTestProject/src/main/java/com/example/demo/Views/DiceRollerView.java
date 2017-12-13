package com.example.demo.Views;

import org.springframework.web.client.RestTemplate;

import com.example.demo.DiceReturn;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.slider.SliderOrientation;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Slider;

public class DiceRollerView extends GridLayout implements View {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Slider vertDiceNum;
	Label display;
	
	//Do only on view creation
	public DiceRollerView() {
		setRows(4);
		setColumns(4);
		//setSizeFull();
		display = new Label();
		Button rollMultipleD10 = new Button("Roll D10");
		Button mainUI = new Button("Return to Menu");
		vertDiceNum = new Slider (1, 10);
		vertDiceNum.setOrientation(SliderOrientation.VERTICAL);
		rollMultipleD10.addClickListener(clickEvent -> display.setValue(rollMultipleD10(vertDiceNum.getValue())));
		mainUI.addClickListener(clickEvent -> MainUI.nav.navigateTo("StartView"));
		addComponent(vertDiceNum, 0, 0);
		addComponent(rollMultipleD10, 1, 0);
		addComponent(display, 1, 1);
		addComponent(mainUI, 1, 2);
	}
	
	//Do every time the view opens
	@Override
	public void enter(ViewChangeEvent event) {
		if (!MainUI.checkCreds()) {
			MainUI.curUser = null;
			MainUI.nav.navigateTo("LoginView");
		}
		display.setValue("");
		vertDiceNum.clear();
	}
	
	private String rollMultipleD10(double numDice) {
		RestTemplate restTemplate = new RestTemplate();
		try {
			DiceReturn rollDice = restTemplate.getForObject("http://localhost:8090/rolld10?numDice=" + (int) numDice, DiceReturn.class);
			return rollDice.toString();
		} 
		catch (Exception e) {
			return "Could not complete command as expected: " + e.toString();
		}
	}
}
