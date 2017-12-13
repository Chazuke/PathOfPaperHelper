package com.example.demo.Views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import java.util.Set;

import com.example.demo.Character;
import com.example.demo.Items.AmuletItem;
import com.example.demo.Items.BootsItem;
import com.example.demo.Items.ChestItem;
import com.example.demo.Items.HeadItem;
import com.example.demo.Items.RingItem;

public class DisplayCharacterView extends HorizontalLayout implements View {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Character displayedChar;

	public DisplayCharacterView(Character i) {
		VerticalLayout leftColumn = new VerticalLayout();
		VerticalLayout rightColumn = new VerticalLayout();
		displayedChar = i;
		
		Label charNameVal = new Label("Name:          " + i.getCharName());
		Label lvlVal = new Label     ("Level:         " + i.getLvl().toString());
		Label descVal = new Label    ("Description:   " + i.getDescription());
		Label lifeVal = new Label    ("Life:          " + Integer.toString(i.calcTotalLife()));
		Label threshVal = new Label  ("Threshold:     " + Integer.toString(i.calcThreshold()));
		Label eleResVal = new Label  ("Elemental Resistance: " + Integer.toString(i.calcER()));
		Label eShieldVal = new Label ("Energy Shield: " + i.geteShield().toString());
		Label manaVal = new Label    ("Mana:          " + i.getBaseMana().toString());
		Label wamVal = new Label     ("WAM:           " + i.getWAM().toString());
		Label intlVal = new Label    ("Intelligence:  " + i.getIntl().toString());
		Label dexVal = new Label     ("Dexterity:     " + i.getDex().toString());
		Label strVal = new Label     ("Strength:      " + i.getStr().toString());
		Label speedVal = new Label   ("Speed:         " + i.getSpeed());
		Label blank = new Label("");
		
		Map<String, Integer> skills = i.getSkills();
		Set<Entry<String, Integer>> eSet = skills.entrySet();
		List<Key> kvpair = new ArrayList<Key>();
		for (Map.Entry<String, Integer> j : eSet) {
			Key k = new Key();
			k.setSkill(j.getKey());
			k.setLevel(j.getValue());
			kvpair.add(k);
		}
		Grid<Key> skillGrid = new Grid<>("Player Skills");
		skillGrid.setItems(kvpair);
		skillGrid.addColumn(Key::getSkill).setCaption("Skill Name");
		skillGrid.addColumn(Key::getLevel).setCaption("Skill Level");
		
		Button rollDropView = new Button("Roll a drop for this character");
		rollDropView.addClickListener(clickEvent -> rollDropClickHandler());
		
		TabSheet gear = new TabSheet();
		gear.addTab(displayHeadItem(), "Head Item");
		gear.addTab(displayChestItem(), "Chest Item");
		gear.addTab(displayBootsItem(), "Boots Item");
		gear.addTab(displayRing1(), "Ring(1)");
		gear.addTab(displayRing2(), "Ring(2)");
		gear.addTab(displayAmulet(), "Amulet");
		gear.addTab(displayMainHand(), "Main Hand");
		gear.addTab(displayOffHand(), "Off-Hand");
		
		rightColumn.addComponent(rollDropView);
		rightColumn.addComponent(gear);
		
		leftColumn.addComponent(charNameVal);
		leftColumn.addComponent(lvlVal);
		leftColumn.addComponent(descVal);
		leftColumn.addComponent(lifeVal);
		leftColumn.addComponent(eShieldVal);
		leftColumn.addComponent(threshVal);
		leftColumn.addComponent(eleResVal);
		leftColumn.addComponent(manaVal);
		leftColumn.addComponent(wamVal);
		leftColumn.addComponent(intlVal);
		leftColumn.addComponent(dexVal);
		leftColumn.addComponent(strVal);
		leftColumn.addComponent(speedVal);
		leftColumn.addComponent(blank);
		leftColumn.addComponent(skillGrid);
		
		addComponent(leftColumn);
		addComponent(rightColumn);
	}
	
	//Do every time the view opens
	@Override
	public void enter(ViewChangeEvent event) {
		if (!MainUI.checkCreds()) {
			MainUI.curUser = null;
			MainUI.nav.navigateTo("LoginView");
		}
	}
	
	private void rollDropClickHandler() {
		MainUI.nav.addView("rollDrop" + displayedChar.getId().toString(), new RollDropView(displayedChar));
		MainUI.nav.navigateTo("rollDrop" + displayedChar.getId().toString());
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
}

class Key {
	String skill;
	int level;
	
	public Key() {
		
	}
	
	public String getSkill() {
		return skill;
	}
	
	public void setSkill(String skill) {
		this.skill = skill;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
}