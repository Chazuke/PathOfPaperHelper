package com.example.demo.Items;

import com.example.demo.Character;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class HeadItem extends Armour {
	private Long id;
	
	private Character wearer;
	
	private Integer threshMod;
	private Integer resMod;
	private Integer lifeMod;
	private Integer armourMod;
	private Integer esMod;
	private Integer maneuverMod;
	private Integer athleticsMod;
	private String implicit;
	private String explicit1;
	private String explicit2;
	private String signature;
	private Boolean transmuted;
	private Boolean exalted;
	
	public HeadItem() {
		setName("Head Item");
		setSlot("Head");
		setWearer(null);
		setType(null);
		setTier(0);
		setRarity(null);
		setDexMod(0);
		setIntMod(0);
		setStrMod(0);
		setThreshMod(0);
		setResMod(0);
		setLifeMod(0);
		setArmourMod(0);
		setEsMod(0);
		setManeuverMod(0);
		setAthleticsMod(0);
		setExplicit1("None");
		setExplicit2("None");
		setSignature("None");
		setImplicit(null);
		setTransmuted(false);
		setExalted(false);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getThreshMod() {
		return threshMod;
	}
	public void setThreshMod(Integer threshMod) {
		this.threshMod = threshMod;
	}
	public Integer getResMod() {
		return resMod;
	}
	public void setResMod(Integer resMod) {
		this.resMod = resMod;
	}
	public Integer getLifeMod() {
		return lifeMod;
	}
	public void setLifeMod(Integer lifeMod) {
		this.lifeMod = lifeMod;
	}
	public Integer getArmourMod() {
		return armourMod;
	}
	public void setArmourMod(Integer armourMod) {
		this.armourMod = armourMod;
	}
	public Integer getEsMod() {
		return esMod;
	}
	public void setEsMod(Integer esMod) {
		this.esMod = esMod;
	}
	public Integer getManeuverMod() {
		return maneuverMod;
	}
	public void setManeuverMod(Integer maneuverMod) {
		this.maneuverMod = maneuverMod;
	}
	public Integer getAthleticsMod() {
		return athleticsMod;
	}
	public void setAthleticsMod(Integer athleticsMod) {
		this.athleticsMod = athleticsMod;
	}
	public String getExplicit1() {
		return explicit1;
	}
	public void setExplicit1(String explicit1) {
		this.explicit1 = explicit1;
	}
	public String getExplicit2() {
		return explicit2;
	}
	public void setExplicit2(String explicit2) {
		this.explicit2 = explicit2;
	}
	public String getImplicit() {
		return implicit;
	}
	public void setImplicit(String implicit) {
		this.implicit = implicit;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public Character getWearer() {
		return wearer;
	}
	public void setWearer(Character wearer) {
		this.wearer = wearer;
	}
	public Boolean getTransmuted() {
		return transmuted;
	}
	public void setTransmuted(Boolean transmuted) {
		this.transmuted = transmuted;
	}
	public Boolean getExalted() {
		return exalted;
	}
	public void setExalted(Boolean exalted) {
		this.exalted = exalted;
	}
}
