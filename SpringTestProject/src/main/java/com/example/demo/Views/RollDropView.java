package com.example.demo.Views;

import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.web.client.RestTemplate;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.example.demo.Items.AmuletItem;
import com.example.demo.Items.BootsItem;
import com.example.demo.Items.ChestItem;
import com.example.demo.Items.HeadItem;
import com.example.demo.Items.RingItem;
import com.example.demo.Character;

public class RollDropView extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(RollDropView.class.getName());
	
	Label errfield;
	Label item_id;
	Character displayedChar;
	HeadItem headItem;
	ChestItem chestItem;
	BootsItem bootsItem;
	AmuletItem amuletItem;
	RingItem ringItem;
	
	HorizontalSplitPanel hsplit;
	
	GridLayout layout;
	TextField tier;
	TextField name;
	NativeSelect<String> rarity;
	Button roll;
	
	Label tierDisplay;
	Label nameDisplay;
	Label slotDisplay;
	Label typeDisplay;
	Label rarityDisplay;
	Label implicitDisplay;
	Label explicit1Display;
	Label explicit2Display;
	Label signatureDisplay;
	
	Button apply;
	
	PopupView confirmDialog;
	Button accept;
	Button reject;
	Boolean applyItem;
	
	TabSheet curGear;
	
	Button.ClickListener rollListener;
	
	public RollDropView(Character i) {
		
		//Set the current character being rolled for
		displayedChar = i;
		applyItem = false;
		headItem = new HeadItem();
		chestItem = new ChestItem();
		bootsItem = new BootsItem();
		amuletItem = new AmuletItem();
		ringItem = new RingItem();
		
		//Reusable display for returned messages of API calls
		errfield = new Label();
		item_id = new Label();
		
		//Reusable text fields and labels
		rarity = new NativeSelect<>("Rarity");
		rarity.setItems("Random", "Normal", "Magic", "Rare", "Signature");
		rarity.setEmptySelectionAllowed(false);
		rarity.setValue("Random");
		tier = new TextField("Tier");
		name = new TextField("Name");
		
		//Reusable display labels
		tierDisplay = new Label();
		nameDisplay = new Label();
		slotDisplay = new Label();
		typeDisplay = new Label();
		rarityDisplay = new Label();
		implicitDisplay = new Label();
		explicit1Display = new Label();
		explicit2Display = new Label();
		signatureDisplay = new Label();
				
		//SplitPanel
		hsplit = new HorizontalSplitPanel();
		VerticalLayout leftPanel = new VerticalLayout();
		
		//Return buttons
		Button listCharView = new Button ("Return to character list");
		listCharView.addClickListener(clickEvent -> MainUI.nav.navigateTo("ListCharactersView"));
		Button startView = new Button ("Return to menu");
		startView.addClickListener(clickEvent -> MainUI.nav.navigateTo("StartView"));
		
		//Buttons on the left side of the split panel
		Button randAll = new Button("Roll a random item from all categories");
		randAll.addClickListener(clickEvent -> displayRightPanel("All"));
		Button randArmour = new Button("Roll a random armour drop");
		randArmour.addClickListener(clickEvent -> displayRightPanel("Armour"));
		Button randWeapon = new Button("Roll a random weapon drop");
		randWeapon.addClickListener(clickEvent -> displayRightPanel("Weapon"));
		Button randAccessory = new Button("Roll a random accessory drop");
		randAccessory.addClickListener(clickEvent -> displayRightPanel("Accessory"));
		Button randCustom = new Button("Roll a random item from a custom list");
		randCustom.addClickListener(clickEvent -> displayRightPanel("Custom"));
		Button randHead = new Button("Roll a head item");
		randHead.addClickListener(clickEvent -> displayRightPanel("Head"));
		Button randChest = new Button("Roll a chest item");
		randChest.addClickListener(clickEvent -> displayRightPanel("Chest"));
		Button randBoot = new Button("Roll a boots item");
		randBoot.addClickListener(clickEvent -> displayRightPanel("Boots"));
		Button randSword = new Button("Roll a sword item");
		randSword.addClickListener(clickEvent -> displayRightPanel("Sword"));
		Button randAxe = new Button("Roll an axe item");
		randAxe.addClickListener(clickEvent -> displayRightPanel("Axe"));
		Button randMace = new Button("Roll a mace item");
		randMace.addClickListener(clickEvent -> displayRightPanel("Mace"));
		Button randStaff = new Button("Roll a staff item");
		randStaff.addClickListener(clickEvent -> displayRightPanel("Staff"));
		Button randDagger = new Button("Roll a dagger item");
		randDagger.addClickListener(clickEvent -> displayRightPanel("Dagger"));
		Button randBow = new Button("Roll a bow item");
		randBow.addClickListener(clickEvent -> displayRightPanel("Bow"));
		Button randClaw = new Button("Roll a claw item");
		randClaw.addClickListener(clickEvent -> displayRightPanel("Claw"));
		Button randSceptre = new Button("Roll a sceptre item");
		randSceptre.addClickListener(clickEvent -> displayRightPanel("Sceptre"));
		Button randWand = new Button("Roll a wand item");
		randWand.addClickListener(clickEvent -> displayRightPanel("Wand"));
		Button randSpear = new Button("Roll a spear/polearm item");
		randSpear.addClickListener(clickEvent -> displayRightPanel("Spear"));
		Button randRing = new Button("Roll a ring item");
		randRing.addClickListener(clickEvent -> displayRightPanel("Ring"));
		Button randAmulet = new Button("Roll an amulet item");
		randAmulet.addClickListener(clickEvent -> displayRightPanel("Amulet"));
		Button randFlask = new Button("Roll a flask item");
		randFlask.addClickListener(clickEvent -> displayRightPanel("Flask"));
		Button randOrb = new Button("Roll for an orb item");
		randOrb.addClickListener(clickEvent -> displayRightPanel("Orb"));
		Button randShield = new Button("Roll for an orb item");
		randShield.addClickListener(clickEvent -> displayRightPanel("Shield"));
		
		//leftPanel.addComponent(randAll);
		//leftPanel.addComponent(randArmour);
		//leftPanel.addComponent(randWeapon);
		//leftPanel.addComponent(randAccessory);
		//leftPanel.addComponent(randCustom);
		leftPanel.addComponent(randHead);
		leftPanel.addComponent(randChest);
		leftPanel.addComponent(randBoot);
		leftPanel.addComponent(randRing);
		leftPanel.addComponent(randAmulet);
		//leftPanel.addComponent(randSword);
		//leftPanel.addComponent(randAxe);
		//leftPanel.addComponent(randMace);
		//leftPanel.addComponent(randStaff);
		//leftPanel.addComponent(randDagger);
		//leftPanel.addComponent(randBow);
		//leftPanel.addComponent(randClaw);
		//leftPanel.addComponent(randSceptre);
		//leftPanel.addComponent(randWand);
		//leftPanel.addComponent(randSpear);
		//leftPanel.addComponent(randShield);
		//leftPanel.addComponent(randFlask);
		//leftPanel.addComponent(randOrb);
		
		//Setup for split panel left side
		hsplit.setCaption("Rolling items for character: " + displayedChar.getCharName());
		hsplit.setFirstComponent(leftPanel);
		addComponent(hsplit);
		
		//Add return buttons to layout
		addComponent(listCharView);
		addComponent(startView);	
		
		//Current gear tabsheet
		curGear = refreshGear();
		addComponent(curGear);
	}
	
	//Do every time the view opens
	@Override
	public void enter(ViewChangeEvent event) {
		if (!MainUI.checkCreds()) {
			MainUI.curUser = null;
			MainUI.nav.navigateTo("LoginView");
		}
	}
	
	//Generate the right side of the split panel dynamically based on which button was pressed on the left side
	public void displayRightPanel(String item) {
		if (item == null) return;
		
		tier.setValue("");
		name.setValue("");
		rarity.setValue("");
		
		nameDisplay.setValue("");
		slotDisplay.setValue("");
		tierDisplay.setValue("");
		typeDisplay.setValue("");
		rarityDisplay.setValue("");
		implicitDisplay.setValue("");
		explicit1Display.setValue("");
		explicit2Display.setValue("");
		signatureDisplay.setValue("");
		errfield.setValue("");
		item_id.setValue("");
		
		roll = new Button("Roll");
		apply = new Button("Apply to Character");

		//Popup confirmation dialog
		VerticalLayout popupLayout = new VerticalLayout();
		HorizontalLayout buttonLayout = new HorizontalLayout();
		Label warningText = new Label("Applying this to your character will overwrite your current item in this slot. Are you sure you want to do this?");
		accept = new Button("Apply New Item");
		reject = new Button("Keep Old Item");
		buttonLayout.addComponent(accept);
		buttonLayout.addComponent(reject);
		popupLayout.addComponent(warningText);
		popupLayout.addComponent(buttonLayout);
		confirmDialog = new PopupView("", popupLayout);
		
		//Switch based on which item was chosen
		switch (item) {
		//A random item from all the items was chosen to be rolled
		case "All":
			
			break;
			
		//A head item was chosen to be rolled
		case "Head":
			//Create the general layout to be displayed
			layout = new GridLayout(5, 20);
			
			//Add a click listener to the roll button so that it will roll a head item and not something else
			roll.addClickListener(clickEvent-> {
				errfield.setValue("");
				try {
					rollHeadItem(Integer.parseInt(tier.getValue()), name.getValue(), rarity.getValue());
				}
				catch (NumberFormatException e) {
					Notification.show("Tier must be a number");
				}
			});
			
			//Add all of the components to the general layout setting those that don't need to be displayed now to invisible
			layout.addComponent(tier, 0, 0);
			layout.addComponent(name, 0, 1);
			layout.addComponent(rarity, 0, 2);
			layout.addComponent(roll, 0, 3);
			layout.addComponent(apply, 1, 3);
			apply.setVisible(false);
			layout.addComponent(confirmDialog, 0, 4);
			layout.addComponent(nameDisplay, 0, 5);
			layout.addComponent(slotDisplay, 0, 6);
			layout.addComponent(tierDisplay, 0, 7);
			layout.addComponent(typeDisplay, 0, 8);
			layout.addComponent(rarityDisplay, 0, 9);
			layout.addComponent(implicitDisplay, 0, 10);
			layout.addComponent(explicit1Display, 0, 11);
			layout.addComponent(explicit2Display, 0, 12);
			layout.addComponent(signatureDisplay, 0, 13);
			layout.addComponent(errfield, 0, 14);
			layout.addComponent(item_id, 0, 15);
			
			//Set the general layout as the right panel in the split panel
			hsplit.setSecondComponent(layout);
			
			//Apply button click -> either prompt that helmet is being overwritten or apply helmet to the empty slot
			apply.addClickListener(clickEvent -> {
				if (displayedChar.getHeadItem() != null) {
					errfield.setValue("Old Head Item ID: " + displayedChar.getHeadItem().getId());
					confirmDialog.setPopupVisible(true);
				}
				else {
					applyItem = true;
					applyItemToChar("Head", headItem.getId());
				}
			});
			
			//Accept overwriting of head item
			accept.addClickListener(clickEvent-> {
					applyItem = true;
					confirmDialog.setPopupVisible(false);
					applyItemToChar("Head", headItem.getId());
			});
			
			//Reject overwriting of head item
			reject.addClickListener(clickEvent -> confirmDialog.setPopupVisible(false));
			break;
			
		case "Chest":
			//Create the general layout to be displayed
			layout = new GridLayout(5, 20);
			
			//Add a click listener to the roll button so that it will roll a chest item and not something else
			roll.addClickListener(clickEvent-> {
				errfield.setValue("");
				try {
					rollChestItem(Integer.parseInt(tier.getValue()), name.getValue(), rarity.getValue());
				}
				catch (NumberFormatException e) {
					Notification.show("Tier must be a number");
				}
			});
			
			//Add all of the components to the general layout setting those that don't need to be displayed now to invisible
			layout.addComponent(tier, 0, 0);
			layout.addComponent(name, 0, 1);
			layout.addComponent(rarity, 0, 2);
			layout.addComponent(roll, 0, 3);
			layout.addComponent(apply, 1, 3);
			apply.setVisible(false);
			layout.addComponent(confirmDialog, 0, 4);
			layout.addComponent(nameDisplay, 0, 5);
			layout.addComponent(slotDisplay, 0, 6);
			layout.addComponent(tierDisplay, 0, 7);
			layout.addComponent(typeDisplay, 0, 8);
			layout.addComponent(rarityDisplay, 0, 9);
			layout.addComponent(implicitDisplay, 0, 10);
			layout.addComponent(explicit1Display, 0, 11);
			layout.addComponent(explicit2Display, 0, 12);
			layout.addComponent(signatureDisplay, 0, 13);
			layout.addComponent(errfield, 0, 14);
			layout.addComponent(item_id, 0, 15);
			
			//Set the general layout as the right panel in the split panel
			hsplit.setSecondComponent(layout);
			
			//Apply button click -> either prompt that chest is being overwritten or apply chest to the empty slot
			apply.addClickListener(clickEvent -> {
				if (displayedChar.getChestItem() != null) {
					errfield.setValue("Old Chest Item ID: " + displayedChar.getChestItem().getId());
					confirmDialog.setPopupVisible(true);
				}
				else {
					applyItem = true;
					applyItemToChar("Chest", chestItem.getId());
				}
			});
			
			//Accept overwriting of chest item
			accept.addClickListener(clickEvent-> {
					applyItem = true;
					confirmDialog.setPopupVisible(false);
					applyItemToChar("Chest", chestItem.getId());
			});
			
			//Reject overwriting of chest item
			reject.addClickListener(clickEvent -> confirmDialog.setPopupVisible(false));
			break;
			
		case "Boots":
			//Create the general layout to be displayed
			layout = new GridLayout(5, 20);
			
			//Add a click listener to the roll button so that it will roll a boots item and not something else
			roll.addClickListener(clickEvent-> {
				errfield.setValue("");
				try {
					rollBootsItem(Integer.parseInt(tier.getValue()), name.getValue(), rarity.getValue());
				}
				catch (NumberFormatException e) {
					Notification.show("Tier must be a number");
				}
			});
			
			//Add all of the components to the general layout setting those that don't need to be displayed now to invisible
			layout.addComponent(tier, 0, 0);
			layout.addComponent(name, 0, 1);
			layout.addComponent(rarity, 0, 2);
			layout.addComponent(roll, 0, 3);
			layout.addComponent(apply, 1, 3);
			apply.setVisible(false);
			layout.addComponent(confirmDialog, 0, 4);
			layout.addComponent(nameDisplay, 0, 5);
			layout.addComponent(slotDisplay, 0, 6);
			layout.addComponent(tierDisplay, 0, 7);
			layout.addComponent(typeDisplay, 0, 8);
			layout.addComponent(rarityDisplay, 0, 9);
			layout.addComponent(implicitDisplay, 0, 10);
			layout.addComponent(explicit1Display, 0, 11);
			layout.addComponent(explicit2Display, 0, 12);
			layout.addComponent(signatureDisplay, 0, 13);
			layout.addComponent(errfield, 0, 14);
			layout.addComponent(item_id, 0, 15);
			
			//Set the general layout as the right panel in the split panel
			hsplit.setSecondComponent(layout);
			
			//Apply button click -> either prompt that boots is being overwritten or apply to boots to the empty slot
			apply.addClickListener(clickEvent -> {
				if (displayedChar.getBootsItem() != null) {
					errfield.setValue("Old Boots Item ID: " + displayedChar.getBootsItem().getId());
					confirmDialog.setPopupVisible(true);
				}
				else {
					applyItem = true;
					applyItemToChar("Boots", bootsItem.getId());
				}
			});
			
			//Accept overwriting of boots item
			accept.addClickListener(clickEvent-> {
					applyItem = true;
					confirmDialog.setPopupVisible(false);
					applyItemToChar("Boots", bootsItem.getId());
			});
			
			//Reject overwriting of boots item
			reject.addClickListener(clickEvent -> confirmDialog.setPopupVisible(false));
			break;
			
		case "Amulet":
			//Create the general layout to be displayed
			layout = new GridLayout(5, 20);
			
			//Add a click listener to the roll button so that it will roll an amulet item and not something else
			roll.addClickListener(clickEvent-> {
				errfield.setValue("");
				try {
					rollAmuletItem(Integer.parseInt(tier.getValue()), name.getValue(), rarity.getValue());
				}
				catch (NumberFormatException e) {
					Notification.show("Tier must be a number");
				}
			});
			
			//Add all of the components to the general layout setting those that don't need to be displayed now to invisible
			layout.addComponent(tier, 0, 0);
			layout.addComponent(name, 0, 1);
			layout.addComponent(rarity, 0, 2);
			layout.addComponent(roll, 0, 3);
			layout.addComponent(apply, 1, 3);
			apply.setVisible(false);
			layout.addComponent(confirmDialog, 0, 4);
			layout.addComponent(nameDisplay, 0, 5);
			layout.addComponent(slotDisplay, 0, 6);
			layout.addComponent(tierDisplay, 0, 7);
			layout.addComponent(typeDisplay, 0, 8);
			layout.addComponent(rarityDisplay, 0, 9);
			layout.addComponent(implicitDisplay, 0, 10);
			layout.addComponent(explicit1Display, 0, 11);
			layout.addComponent(explicit2Display, 0, 12);
			layout.addComponent(signatureDisplay, 0, 13);
			layout.addComponent(errfield, 0, 14);
			layout.addComponent(item_id, 0, 15);
			
			//Set the general layout as the right panel in the split panel
			hsplit.setSecondComponent(layout);
			
			//Apply button click -> either prompt that amulet is being overwritten or apply amulet to the empty slot
			apply.addClickListener(clickEvent -> {
				if (displayedChar.getAmuletItem() != null) {
					errfield.setValue("Old Amulet Item ID: " + displayedChar.getAmuletItem().getId());
					confirmDialog.setPopupVisible(true);
				}
				else {
					applyItem = true;
					applyItemToChar("Amulet", amuletItem.getId());
				}
			});
			
			//Accept overwriting of amulet item
			accept.addClickListener(clickEvent-> {
					applyItem = true;
					confirmDialog.setPopupVisible(false);
					applyItemToChar("Amulet", amuletItem.getId());
			});
			
			//Reject overwriting of amulet item
			reject.addClickListener(clickEvent -> confirmDialog.setPopupVisible(false));
			break;
			
		case "Ring":
			//Create the general layout to be displayed
			layout = new GridLayout(5, 20);
			
			//Add a click listener to the roll button so that it will roll a boots item and not something else
			roll.addClickListener(clickEvent-> {
				errfield.setValue("");
				try {
					rollRingItem(Integer.parseInt(tier.getValue()), name.getValue(), rarity.getValue());
				}
				catch (NumberFormatException e) {
					Notification.show("Tier must be a number");
				}
			});
			
			RadioButtonGroup<String> ringSelect = new RadioButtonGroup<>("Replace Ring:");
			ringSelect.setItems("Ring1", "Ring2");
			
			//Add all of the components to the general layout setting those that don't need to be displayed now to invisible
			layout.addComponent(tier, 0, 0);
			layout.addComponent(name, 0, 1);
			layout.addComponent(rarity, 0, 2);
			layout.addComponent(roll, 0, 3);
			layout.addComponent(apply, 1, 3);
			apply.setVisible(false);
			layout.addComponent(confirmDialog, 0, 4);
			if (displayedChar.getRingItem1() != null && displayedChar.getRingItem2() != null) {
				layout.addComponent(ringSelect, 0, 5);
				layout.addComponent(nameDisplay, 0, 6);
				layout.addComponent(slotDisplay, 0, 7);
				layout.addComponent(tierDisplay, 0, 8);
				layout.addComponent(typeDisplay, 0, 9);
				layout.addComponent(rarityDisplay, 0, 10);
				layout.addComponent(implicitDisplay, 0, 11);
				layout.addComponent(explicit1Display, 0, 12);
				layout.addComponent(explicit2Display, 0, 13);
				layout.addComponent(signatureDisplay, 0, 14);
				layout.addComponent(errfield, 0, 15);
				layout.addComponent(item_id, 0, 16);
			}
			else {
				layout.addComponent(nameDisplay, 0, 5);
				layout.addComponent(slotDisplay, 0, 6);
				layout.addComponent(tierDisplay, 0, 7);
				layout.addComponent(typeDisplay, 0, 8);
				layout.addComponent(rarityDisplay, 0, 9);
				layout.addComponent(implicitDisplay, 0, 10);
				layout.addComponent(explicit1Display, 0, 11);
				layout.addComponent(explicit2Display, 0, 12);
				layout.addComponent(signatureDisplay, 0, 13);
				layout.addComponent(errfield, 0, 14);
				layout.addComponent(item_id, 0, 15);
			}
			
			//Set the general layout as the right panel in the split panel
			hsplit.setSecondComponent(layout);
			
			//Apply button click -> either prompt that boots is being overwritten or apply to boots to the empty slot
			apply.addClickListener(clickEvent -> {
				if (displayedChar.getRingItem1() == null) {
					applyItem = true;
					applyItemToChar("Ring1", ringItem.getId());
				}
				else if (displayedChar.getRingItem1() != null && displayedChar.getRingItem2() == null) {
					applyItem = true;
					applyItemToChar("Ring2", ringItem.getId());
				}
				else if (displayedChar.getRingItem1() != null && displayedChar.getRingItem2() != null) {
					errfield.setValue("Old Boots Item ID: " + displayedChar.getBootsItem().getId());
					confirmDialog.setPopupVisible(true);
				}
			});
			
			//Accept overwriting of boots item
			accept.addClickListener(clickEvent-> {
					applyItem = true;
					confirmDialog.setPopupVisible(false);
					applyItemToChar(ringSelect.getValue(), bootsItem.getId());
			});
			
			//Reject overwriting of boots item
			reject.addClickListener(clickEvent -> confirmDialog.setPopupVisible(false));
			break;
		}
	}
	
	public void rollRandomItem(int tier, String name) {
		
	}
	
	public void rollRandomArmour(int tier, String name) {
		Random rand = new Random();
		int item = rand.nextInt(10)+1;
		
		switch (item) {
		case 1:
			rollHeadItem(tier, name, null);
			break;
		
		case 2:
			rollChestItem(tier, name, null);
			break;
			
		case 3:
			rollBootsItem(tier, name, null);
			break;
		}
	}
	
	public void rollRandomWeapon(int tier, String name) {
		Random rand = new Random();
		int item = rand.nextInt(10)+1;
		
		switch (item) {
		case 1:
		}
	}
	
	public void rollRandomAccessory(int tier, String name) {
		Random rand = new Random();
		int item = rand.nextInt(10)+1;
		
		switch (item) {
		case 1:
		}
	}
	
	public void rollRandomCustom(int tier, String name) {
		
	}
	
	public void rollHeadItem(int tier, String name, String rarity) {
		//Generate the rest template and headers for sending in the user object
		RestTemplate restTemplate = new RestTemplate();
		
		if (rarity.equals("")) rarity = "Random";
		
		//Attempt to call the API function and capture the response
		try {
			headItem = restTemplate.getForObject(MainUI.restURLDb + "rollHeadItem?tier=" + tier 
			+ "&rarity=" + rarity + "&name=" + name + "&wearer=" + displayedChar.getId(), HeadItem.class);
		}
		catch (Exception e) {
			errfield.setValue(e.getMessage());
			return;
		}
		finally {
			//Set the display field values and make them visible
			item_id.setValue("Item_ID: " + headItem.getId().toString());
			nameDisplay.setValue("Name: " + headItem.getName());
			slotDisplay.setValue("Slot: " + headItem.getSlot());
			tierDisplay.setValue("Tier: " + headItem.getTier().toString());
			typeDisplay.setValue("Type: " + headItem.getType());
			rarityDisplay.setValue("Rarity: " + headItem.getRarity());
			implicitDisplay.setValue("Implicit: " + headItem.getImplicit());
			explicit1Display.setValue("Explicit(1): " + headItem.getExplicit1());
			explicit2Display.setValue("Explicit(2): " + headItem.getExplicit2());
			signatureDisplay.setValue("Signature: " + headItem.getSignature());
			apply.setVisible(true);
		}
	}
	
	public void rollChestItem(int tier, String name, String rarity) {
		//Generate the rest template and headers for sending in the user object
		RestTemplate restTemplate = new RestTemplate();
		
		if (rarity.equals("")) rarity = "Random";
		
		//Attempt to call the API function and capture the response
		try {
			chestItem = restTemplate.getForObject(MainUI.restURLDb + "rollChestItem?tier=" + tier 
			+ "&rarity=" + rarity + "&name=" + name + "&wearer=" + displayedChar.getId(), ChestItem.class);
		}
		catch (Exception e) {
			errfield.setValue(e.getMessage());
			return;
		}
		finally {
			//Set the display field values and make them visible
			item_id.setValue("Item_ID: " + chestItem.getId().toString());
			nameDisplay.setValue("Name: " + chestItem.getName());
			slotDisplay.setValue("Slot: " + chestItem.getSlot());
			tierDisplay.setValue("Tier: " + chestItem.getTier().toString());
			typeDisplay.setValue("Type: " + chestItem.getType());
			rarityDisplay.setValue("Rarity: " + chestItem.getRarity());
			implicitDisplay.setValue("Implicit: " + chestItem.getImplicit());
			explicit1Display.setValue("Explicit(1): " + chestItem.getExplicit1());
			explicit2Display.setValue("Explicit(2): " + chestItem.getExplicit2());
			signatureDisplay.setValue("Signature: " + chestItem.getSignature());
			apply.setVisible(true);
		}
	}
	
	public void rollBootsItem(int tier, String name, String rarity) {
		//Generate the rest template and headers for sending in the user object
		RestTemplate restTemplate = new RestTemplate();
		
		if (rarity.equals("")) rarity = "Random";
		
		//Attempt to call the API function and capture the response
		try {
			bootsItem = restTemplate.getForObject(MainUI.restURLDb + "rollBootsItem?tier=" + tier 
			+ "&rarity=" + rarity + "&name=" + name + "&wearer=" + displayedChar.getId(), BootsItem.class);
		}
		catch (Exception e) {
			errfield.setValue(e.getMessage());
			return;
		}
		finally {
			//Set the display field values and make them visible
			item_id.setValue("Item_ID: " + bootsItem.getId().toString());
			nameDisplay.setValue("Name: " + bootsItem.getName());
			slotDisplay.setValue("Slot: " + bootsItem.getSlot());
			tierDisplay.setValue("Tier: " + bootsItem.getTier().toString());
			typeDisplay.setValue("Type: " + bootsItem.getType());
			rarityDisplay.setValue("Rarity: " + bootsItem.getRarity());
			implicitDisplay.setValue("Implicit: " + bootsItem.getImplicit());
			explicit1Display.setValue("Explicit(1): " + bootsItem.getExplicit1());
			explicit2Display.setValue("Explicit(2): " + bootsItem.getExplicit2());
			signatureDisplay.setValue("Signature: " + bootsItem.getSignature());
			apply.setVisible(true);
		}
	}
	
	public void rollRingItem(int tier, String name, String rarity) {
		//Generate the rest template and headers for sending in the user object
		RestTemplate restTemplate = new RestTemplate();
		
		if (rarity.equals("")) rarity = "Random";
		
		//Attempt to call the API function and capture the response
		try {
			ringItem = restTemplate.getForObject(MainUI.restURLDb + "rollRingItem?tier=" + tier 
			+ "&rarity=" + rarity + "&name=" + name + "&wearer=" + displayedChar.getId(), RingItem.class);
		}
		catch (Exception e) {
			errfield.setValue(e.getMessage());
			return;
		}
		finally {
			//Set the display field values and make them visible
			item_id.setValue("Item_ID: " + ringItem.getId().toString());
			nameDisplay.setValue("Name: " + ringItem.getName());
			slotDisplay.setValue("Slot: " + ringItem.getSlot());
			tierDisplay.setValue("Tier: " + ringItem.getTier().toString());
			typeDisplay.setValue("Type: " + ringItem.getType());
			rarityDisplay.setValue("Rarity: " + ringItem.getRarity());
			implicitDisplay.setValue("Implicit: " + ringItem.getImplicit());
			explicit1Display.setValue("Explicit(1): " + ringItem.getExplicit1());
			explicit2Display.setValue("Explicit(2): " + ringItem.getExplicit2());
			signatureDisplay.setValue("Signature: " + ringItem.getSignature());
			apply.setVisible(true);
		}
	}
	
	public void rollAmuletItem(int tier, String name, String rarity) {
		//Generate the rest template and headers for sending in the user object
		RestTemplate restTemplate = new RestTemplate();
		
		if (rarity.equals("")) rarity = "Random";
		
		//Attempt to call the API function and capture the response
		try {
			amuletItem = restTemplate.getForObject(MainUI.restURLDb + "rollAmuletItem?tier=" + tier 
			+ "&rarity=" + rarity + "&name=" + name + "&wearer=" + displayedChar.getId(), AmuletItem.class);
		}
		catch (Exception e) {
			errfield.setValue(e.getMessage());
			return;
		}
		finally {
			//Set the display field values and make them visible
			item_id.setValue("Item_ID: " + amuletItem.getId().toString());
			nameDisplay.setValue("Name: " + amuletItem.getName());
			slotDisplay.setValue("Slot: " + amuletItem.getSlot());
			tierDisplay.setValue("Tier: " + amuletItem.getTier().toString());
			typeDisplay.setValue("Type: " + amuletItem.getType());
			rarityDisplay.setValue("Rarity: " + amuletItem.getRarity());
			implicitDisplay.setValue("Implicit: " + amuletItem.getImplicit());
			explicit1Display.setValue("Explicit(1): " + amuletItem.getExplicit1());
			explicit2Display.setValue("Explicit(2): " + amuletItem.getExplicit2());
			signatureDisplay.setValue("Signature: " + amuletItem.getSignature());
			apply.setVisible(true);
		}
	}
	
	public void rollSwordItem() {
		
	}
	
	public void rollAxeItem() {
		
	}
	
	public void rollMaceItem() {
		
	}
	
	public void rollStaffItem() {
		
	}
	
	public void rollDaggerItem() {
		
	}
	
	public void rollBowItem() {
		
	}
	
	public void rollClawItem() {
		
	}
	
	public void rollSceptreItem() {
		
	}
	
	public void rollWandItem() {
		
	}
	
	public void rollSpearItem() {
		
	}
	
	public void rollShieldItem() {
		
	}
	
	public void rollFlaskItem() {
		
	}
	
	public void rollOrbItem() {
		
	}
	
	public void applyItemToChar(String slot, Long item_id) {
		//If accept was chosen in the popup then apply the item to the character
		if (applyItem) {
			RestTemplate restTemplate = new RestTemplate();
			try {
				String resp;
				if (slot.equals("Ring1")) {
					resp = restTemplate.getForObject(MainUI.restURLDb + "applyItem?itemType=Ring" + "&id=" 
							+ item_id + "&char_id=" + displayedChar.getId() + "&overwrite=" + 1, String.class);
				}
				else if (slot.equals("Ring2")) {
					resp = restTemplate.getForObject(MainUI.restURLDb + "applyItem?itemType=Ring" + "&id=" 
							+ item_id + "&char_id=" + displayedChar.getId() + "&overwrite=" + 2, String.class);
				}
				else {
					resp = restTemplate.getForObject(MainUI.restURLDb + "applyItem?itemType=" + slot + "&id=" 
															+ item_id + "&char_id=" + displayedChar.getId(), String.class);
				}
				errfield.setValue(resp);
			}
			catch (Exception e) {
				
			}
			finally {
				applyItem = false;
				switch (slot) {
				case "Head":
					displayedChar.setHeadItem(headItem);
					break;
					
				case "Chest":
					displayedChar.setChestItem(chestItem);
					break;
					
				case "Boots":
					displayedChar.setBootsItem(bootsItem);
					break;
					
				case "Amulet":
					displayedChar.setAmuletItem(amuletItem);
					break;
					
				case "Ring1":
					displayedChar.setRingItem1(ringItem);
					break;
					
				case "Ring2":
					displayedChar.setRingItem2(ringItem);
					break;

				}
				removeComponent(curGear);
				curGear = refreshGear();
				addComponent(curGear);
			}
		}
		else {
			errfield.setValue("Must Confirm Item Overwrite");
		}
	}
	
	private VerticalLayout displayHeadItem() {
		VerticalLayout ret = new VerticalLayout();
		HeadItem item = displayedChar.getHeadItem();
		if (item == null) {
			Label noItem = new Label("No item equipped in this slot");
			ret.addComponent(noItem);
			return ret;
		}
		Label name = new Label("Name: " + item.getName());
		Label type = new Label("Type: " + item.getType());
		Label tier = new Label("Tier: " + item.getTier());
		Label rarity = new Label("Rarity: " + item.getRarity());
		Label implicit = new Label("Implicit: " + item.getImplicit());
		Label explicit1 = new Label("Explicit(1): " + item.getExplicit1());
		Label explicit2 = new Label("Explicit(2): " + item.getExplicit2());
		Label signature = new Label("Signature: " + item.getSignature());
		
		ret.addComponent(name);
		ret.addComponent(type);
		ret.addComponent(tier);
		ret.addComponent(rarity);
		ret.addComponent(implicit);
		ret.addComponent(explicit1);
		ret.addComponent(explicit2);
		ret.addComponent(signature);
		
		return ret;
	}
	
	private VerticalLayout displayChestItem() {
		VerticalLayout ret = new VerticalLayout();
		ChestItem item = displayedChar.getChestItem();
		if (item == null) {
			Label noItem = new Label("No item equipped in this slot");
			ret.addComponent(noItem);
			return ret;
		}
		Label name = new Label("Name: " + item.getName());
		Label type = new Label("Type: " + item.getType());
		Label tier = new Label("Tier: " + item.getTier());
		Label rarity = new Label("Rarity: " + item.getRarity());
		Label implicit = new Label("Implicit: " + item.getImplicit());
		Label explicit1 = new Label("Explicit(1): " + item.getExplicit1());
		Label explicit2 = new Label("Explicit(2): " + item.getExplicit2());
		Label signature = new Label("Signature: " + item.getSignature());
		
		ret.addComponent(name);
		ret.addComponent(type);
		ret.addComponent(tier);
		ret.addComponent(rarity);
		ret.addComponent(implicit);
		ret.addComponent(explicit1);
		ret.addComponent(explicit2);
		ret.addComponent(signature);
		
		return ret;
	}
	
	private VerticalLayout displayBootsItem() {
		VerticalLayout ret = new VerticalLayout();
		BootsItem item = displayedChar.getBootsItem();
		if (item == null) {
			Label noItem = new Label("No item equipped in this slot");
			ret.addComponent(noItem);
			return ret;
		}
		Label name = new Label("Name: " + item.getName());
		Label type = new Label("Type: " + item.getType());
		Label tier = new Label("Tier: " + item.getTier());
		Label rarity = new Label("Rarity: " + item.getRarity());
		Label implicit = new Label("Implicit: " + item.getImplicit());
		Label explicit1 = new Label("Explicit(1): " + item.getExplicit1());
		Label explicit2 = new Label("Explicit(2): " + item.getExplicit2());
		Label signature = new Label("Signature: " + item.getSignature());
		
		ret.addComponent(name);
		ret.addComponent(type);
		ret.addComponent(tier);
		ret.addComponent(rarity);
		ret.addComponent(implicit);
		ret.addComponent(explicit1);
		ret.addComponent(explicit2);
		ret.addComponent(signature);
		
		return ret;
	}
	
	private VerticalLayout displayRing1() {
		VerticalLayout ret = new VerticalLayout();
		RingItem item = displayedChar.getRingItem1();
		if (item == null) {
			Label noItem = new Label("No item equipped in this slot");
			ret.addComponent(noItem);
			return ret;
		}
		Label name = new Label("Name: " + item.getName());
		Label type = new Label("Type: " + item.getType());
		Label tier = new Label("Tier: " + item.getTier());
		Label rarity = new Label("Rarity: " + item.getRarity());
		Label implicit = new Label("Implicit: " + item.getImplicit());
		Label explicit1 = new Label("Explicit(1): " + item.getExplicit1());
		Label explicit2 = new Label("Explicit(2): " + item.getExplicit2());
		Label signature = new Label("Signature: " + item.getSignature());
		
		ret.addComponent(name);
		ret.addComponent(type);
		ret.addComponent(tier);
		ret.addComponent(rarity);
		ret.addComponent(implicit);
		ret.addComponent(explicit1);
		ret.addComponent(explicit2);
		ret.addComponent(signature);
		
		return ret;
	}
	
	private VerticalLayout displayRing2() {
		VerticalLayout ret = new VerticalLayout();
		RingItem item = displayedChar.getRingItem2();
		if (item == null) {
			Label noItem = new Label("No item equipped in this slot");
			ret.addComponent(noItem);
			return ret;
		}
		Label name = new Label("Name: " + item.getName());
		Label type = new Label("Type: " + item.getType());
		Label tier = new Label("Tier: " + item.getTier());
		Label rarity = new Label("Rarity: " + item.getRarity());
		Label implicit = new Label("Implicit: " + item.getImplicit());
		Label explicit1 = new Label("Explicit(1): " + item.getExplicit1());
		Label explicit2 = new Label("Explicit(2): " + item.getExplicit2());
		Label signature = new Label("Signature: " + item.getSignature());
		
		ret.addComponent(name);
		ret.addComponent(type);
		ret.addComponent(tier);
		ret.addComponent(rarity);
		ret.addComponent(implicit);
		ret.addComponent(explicit1);
		ret.addComponent(explicit2);
		ret.addComponent(signature);
		
		return ret;
	}
	
	private VerticalLayout displayAmulet() {
		VerticalLayout ret = new VerticalLayout();
		AmuletItem item = displayedChar.getAmuletItem();
		if (item == null) {
			Label noItem = new Label("No item equipped in this slot");
			ret.addComponent(noItem);
			return ret;
		}
		Label name = new Label("Name: " + item.getName());
		Label type = new Label("Type: " + item.getType());
		Label tier = new Label("Tier: " + item.getTier());
		Label rarity = new Label("Rarity: " + item.getRarity());
		Label implicit = new Label("Implicit: " + item.getImplicit());
		Label explicit1 = new Label("Explicit(1): " + item.getExplicit1());
		Label explicit2 = new Label("Explicit(2): " + item.getExplicit2());
		Label signature = new Label("Signature: " + item.getSignature());
		
		ret.addComponent(name);
		ret.addComponent(type);
		ret.addComponent(tier);
		ret.addComponent(rarity);
		ret.addComponent(implicit);
		ret.addComponent(explicit1);
		ret.addComponent(explicit2);
		ret.addComponent(signature);
		
		return ret;
	}
	
	private VerticalLayout displayMainHand() {
		VerticalLayout ret = new VerticalLayout();
		if (true) {
			Label noItem = new Label("No item equipped in this slot");
			ret.addComponent(noItem);
			return ret;
		}
		return ret;
	}
	
	private VerticalLayout displayOffHand() {
		VerticalLayout ret = new VerticalLayout();
		if (true) {
			Label noItem = new Label("No item equipped in this slot");
			ret.addComponent(noItem);
			return ret;
		}
		return ret;
	}
	
	private TabSheet refreshGear() {
		TabSheet gear = new TabSheet();
		gear.setCaption("Current Gear");
		gear.addTab(displayHeadItem(), "Head Item");
		gear.addTab(displayChestItem(), "Chest Item");
		gear.addTab(displayBootsItem(), "Boots Item");
		gear.addTab(displayRing1(), "Ring(1)");
		gear.addTab(displayRing2(), "Ring(2)");
		gear.addTab(displayAmulet(), "Amulet");
		gear.addTab(displayMainHand(), "Main Hand");
		gear.addTab(displayOffHand(), "Off-Hand");
		
		return gear;
	}
}
