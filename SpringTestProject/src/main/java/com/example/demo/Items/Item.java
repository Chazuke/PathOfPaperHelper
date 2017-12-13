package com.example.demo.Items;

//Items include weapons and Armour
public class Item {
	public String name;
	public Integer tier;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getTier() {
		return tier;
	}
	
	public void setTier(Integer tier) {
		this.tier = tier;
	}
}
