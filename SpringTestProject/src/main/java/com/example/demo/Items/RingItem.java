package com.example.demo.Items;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

import com.example.demo.Character;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class RingItem extends Armour implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private RingItemPK ringItemPK;

	private Long id;
	
	private Character wearer;
	
	private Integer flatDam;
	private Integer lifeLeech;
	private Integer armourMod;
	private Integer lifeMod;
	private Integer wam;
	private Integer resMod;
	private Integer manaMod;
	private String implicit;
	private String explicit1;
	private String explicit2;
	private String signature;
	private Boolean transmuted;
	private Boolean exalted;

	public RingItem() {
		setName("Ring Item");
		setSlot("Ring");
		setType(null);
		setFlatDam(0);
		setTier(0);
		setRarity(null);
		setWearer(null);
		setIntMod(0);
		setDexMod(0);
		setStrMod(0);
		setLifeLeech(0);
		setArmourMod(0);
		setLifeMod(0);
		setManaMod(0);
		setWam(0);
		setResMod(0);
		setImplicit(null);
		setExplicit1("None");
		setExplicit2("None");
		setSignature("None");
		setTransmuted(false);
		setExalted(false);
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Character getWearer() {
		return wearer;
	}
	public void setWearer(Character wearer) {
		this.wearer = wearer;
	}
	public Integer getLifeLeech() {
		return lifeLeech;
	}
	public void setLifeLeech(Integer lifeLeech) {
		this.lifeLeech = lifeLeech;
	}
	public Integer getArmourMod() {
		return armourMod;
	}
	public void setArmourMod(Integer armourMod) {
		this.armourMod = armourMod;
	}
	public Integer getLifeMod() {
		return lifeMod;
	}
	public void setLifeMod(Integer lifeMod) {
		this.lifeMod = lifeMod;
	}
	public Integer getWam() {
		return wam;
	}
	public void setWam(Integer wam) {
		this.wam = wam;
	}
	public Integer getResMod() {
		return resMod;
	}

	public void setResMod(Integer resMod) {
		this.resMod = resMod;
	}
	public String getImplicit() {
		return implicit;
	}

	public void setImplicit(String implicit) {
		this.implicit = implicit;
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

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
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

	public Integer getFlatDam() {
		return flatDam;
	}

	public void setFlatDam(Integer flatDam) {
		this.flatDam = flatDam;
	}

	public Integer getManaMod() {
		return manaMod;
	}

	public void setManaMod(Integer manaMod) {
		this.manaMod = manaMod;
	}
	public RingItemPK getRingItemPK() {
		return ringItemPK;
	}

	public void setRingItemPK(RingItemPK ringItemPK) {
		this.ringItemPK = ringItemPK;
	}
}

class RingItemPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Boolean isRing1;
	
	public RingItemPK (Long id, Boolean isRing1) {
		setId(id);
		setIsRing1(isRing1);
	}
	
	public RingItemPK () {
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Boolean getIsRing1() {
		return isRing1;
	}
	public void setIsRing1(Boolean isRing1) {
		this.isRing1 = isRing1;
	}
	
	
}