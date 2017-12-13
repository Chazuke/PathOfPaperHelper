package com.example.demo.Items;

//Armour includes head, chest, rings, amulets, boots
public class Armour extends Item {
	public String slot;
	public String type;
	public String rarity;
	public Integer dexMod;
	public Integer intMod;
	public Integer strMod;
	
	public String getSlot() {
		return slot;
	}
	public void setSlot(String slot) {
		this.slot = slot;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRarity() {
		return rarity;
	}
	public void setRarity(String rarity) {
		this.rarity = rarity;
	}
	public Integer getDexMod() {
		return dexMod;
	}
	public void setDexMod(Integer dexMod) {
		this.dexMod = dexMod;
	}
	public Integer getIntMod() {
		return intMod;
	}
	public void setIntMod(Integer intMod) {
		this.intMod = intMod;
	}
	public Integer getStrMod() {
		return strMod;
	}
	public void setStrMod(Integer strMod) {
		this.strMod = strMod;
	}
}
