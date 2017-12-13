package com.example.demo.Items;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.demo.Character;

@Entity
@Table(name = "amuletitems")
public class AmuletItem extends Armour {
	private Long id;
	
	private Character wearer;
	
	private Integer enemyStunSpeedMod;
	private Integer wamAgainstShock;
	private Integer lifeLeech;
	private Integer manaLeech;
	private Integer armourMod;
	private Integer lifeMod;
	private Integer critStrikeDam;
	private Integer wam;
	private Integer manaMod;
	private Integer resMod;
	private String implicit;
	private String explicit1;
	private String explicit2;
	private String signature;
	private Boolean transmuted;
	private Boolean exalted;

	public AmuletItem() {
		setName("Amulet Item");
		setSlot("Amulet");
		setType(null);
		setTier(0);
		setRarity(null);
		setWearer(null);
		setIntMod(0);
		setDexMod(0);
		setStrMod(0);
		setEnemyStunSpeedMod(0);
		setWamAgainstShock(0);
		setLifeLeech(0);
		setManaLeech(0);
		setArmourMod(0);
		setLifeMod(0);
		setCritStrikeDam(0);
		setWam(0);
		setManaMod(0);
		setResMod(0);
		setImplicit(null);
		setExplicit1("None");
		setExplicit2("None");
		setSignature("None");
		setTransmuted(false);
		setExalted(false);
	}
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="item_id", unique=true, nullable=false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="wearer", unique=true)
	public Character getWearer() {
		return wearer;
	}
	public void setWearer(Character wearer) {
		this.wearer = wearer;
	}
	public Integer getEnemyStunSpeedMod() {
		return enemyStunSpeedMod;
	}
	public void setEnemyStunSpeedMod(Integer enemyStunSpeedMod) {
		this.enemyStunSpeedMod = enemyStunSpeedMod;
	}
	public Integer getWamAgainstShock() {
		return wamAgainstShock;
	}
	public void setWamAgainstShock(Integer wamAgainstShock) {
		this.wamAgainstShock = wamAgainstShock;
	}
	public Integer getLifeLeech() {
		return lifeLeech;
	}
	public void setLifeLeech(Integer lifeLeech) {
		this.lifeLeech = lifeLeech;
	}
	public Integer getManaLeech() {
		return manaLeech;
	}
	public void setManaLeech(Integer manaLeech) {
		this.manaLeech = manaLeech;
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
	public Integer getCritStrikeDam() {
		return critStrikeDam;
	}
	public void setCritStrikeDam(Integer critStrikeDam) {
		this.critStrikeDam = critStrikeDam;
	}
	public Integer getWam() {
		return wam;
	}
	public void setWam(Integer wam) {
		this.wam = wam;
	}

	public Integer getManaMod() {
		return manaMod;
	}

	public void setManaMod(Integer manaMod) {
		this.manaMod = manaMod;
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
}
