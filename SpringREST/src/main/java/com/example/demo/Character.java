package com.example.demo;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.*;

import com.example.demo.Items.AmuletItem;
import com.example.demo.Items.BootsItem;
import com.example.demo.Items.ChestItem;
import com.example.demo.Items.HeadItem;
import com.example.demo.Items.RingItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.demo.User;

@Entity
@Table(name="characters")
public class Character {
	private Long id;
	
	private User owner;
	
	private HeadItem headItem;
	private ChestItem chestItem;
	private BootsItem bootsItem;
	private AmuletItem amuletItem;
	private RingItem ringItem1;
	private RingItem ringItem2;

	private String charName;
	private String description;
	private Integer lvl;
	private Integer baseLife;
	private Integer damageTaken;
	private Integer baseMana;
	private Integer eShield;
	private Integer str;
	private Integer intl;
	private Integer dex;
	private Integer WAM;
	private Integer transmutes;
	private Integer chaos;
	private Integer divines;
	private Integer exalts;
	private Integer threshMod;
	private Integer armourMod;
	private Integer resMod;
	private Integer speed;
	private Integer stunnedEnemySpeed;
	private Integer wamAgainstShock;
	private Integer lifeLeech;
	private Integer manaLeech;
	private Integer critStrikeDamMod;
	private Integer flatDam;

	private Map<String, Integer> skills;
	
	public Character(User owner, String name) {
		this.setOwner(owner);
		this.setCharName(name);
		setLvl(0);
		setBaseLife(10);
		setBaseMana(2);
		setStr(10);
		setIntl(10);
		setDex(10);
		setDamageTaken(0);
		seteShield(0);
		setWAM(1);
		setDescription("N/A");
		setHeadItem(null);
		setChestItem(null);
		setBootsItem(null);
		setAmuletItem(null);
		setRingItem1(null);
		setRingItem2(null);
		setTransmutes(0);
		setChaos(0);
		setDivines(0);
		setExalts(0);
		setThreshMod(0);
		setArmourMod(0);
		setResMod(0);
		setSpeed(2);
		setStunnedEnemySpeed(0);
		setWamAgainstShock(0);
		setLifeLeech(0);
		setManaLeech(0);
		setCritStrikeDamMod(0);
		setFlatDam(0);
		skills = new HashMap<String, Integer>(0);
		skills.put("Athletics", 0);
		skills.put("Soul Endurance", 0);
		skills.put("Survival", 0);
		skills.put("Stealth", 0);
		skills.put("Soul Manipulation", 0);
		skills.put("Maneuver", 0);
		skills.put("Initiative", 0);
		skills.put("Perception", 0);
		skills.put("Lore", 0);
		skills.put("Soulcraft", 0);
		skills.put("Negotiation", 0);
		skills.put("Charm", 0);
		skills.put("Intimidate", 0);
		
	}
	
	public Character(User owner, String name, String desc) {
		this(owner, name);
		setDescription(desc);
	}
	
	public Character() {
		
	}
	
	public int calcArmor() {
		return dex/2 + armourMod;
	}
	
	public int calcER() {
		return intl/2 + resMod;
	}
	
	public int calcTotalLife() {
		return baseLife + str;
	}
	
	public int calcThreshold() {
		return calcTotalLife()/2 + threshMod;
	}
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="char_id", unique=true, nullable=false)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="owner", referencedColumnName="username")
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getCharName() {
		return charName;
	}

	public void setCharName(String charName) {
		this.charName = charName;
	}

	public Integer getLvl() {
		return lvl;
	}

	public void setLvl(Integer lvl) {
		this.lvl = lvl;
	}

	public Integer getDamageTaken() {
		return damageTaken;
	}

	public void setDamageTaken(Integer damageTaken) {
		this.damageTaken = damageTaken;
	}

	public Integer getBaseMana() {
		return baseMana;
	}

	public void setBaseMana(Integer baseMana) {
		this.baseMana = baseMana;
	}

	public Integer geteShield() {
		return eShield;
	}

	public void seteShield(Integer eShield) {
		this.eShield = eShield;
	}

	public Integer getBaseLife() {
		return baseLife;
	}

	public void setBaseLife(Integer baseLife) {
		this.baseLife = baseLife;
	}

	public Integer getIntl() {
		return intl;
	}

	public void setIntl(Integer intl) {
		this.intl = intl;
	}

	public Integer getDex() {
		return dex;
	}

	public void setDex(Integer dex) {
		this.dex = dex;
	}

	public Integer getStr() {
		return str;
	}

	public void setStr(Integer str) {
		this.str = str;
	}
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name = "skills_table", joinColumns = @JoinColumn(name = "char_id"))
	@MapKeyColumn(name="skill")
	@Column(name = "skill_level")
	public Map<String, Integer> getSkills() {
		return skills;
	}

	public void setSkills(Map<String, Integer> skills) {
		this.skills = skills;
	}

	public Integer getWAM() {
		return WAM;
	}

	public void setWAM(Integer WAM) {
		this.WAM = WAM;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@OneToOne(mappedBy="wearer")
	public HeadItem getHeadItem() {
		return headItem;
	}

	public void setHeadItem(HeadItem headItem) {
		this.headItem = headItem;
	}
	
	public Integer getTransmutes() {
		return transmutes;
	}

	public void setTransmutes(Integer transmutes) {
		this.transmutes = transmutes;
	}

	public Integer getChaos() {
		return chaos;
	}

	public void setChaos(Integer chaos) {
		this.chaos = chaos;
	}

	public Integer getDivines() {
		return divines;
	}

	public void setDivines(Integer divines) {
		this.divines = divines;
	}

	public Integer getExalts() {
		return exalts;
	}

	public void setExalts(Integer exalts) {
		this.exalts = exalts;
	}

	public Integer getThreshMod() {
		return threshMod;
	}

	public void setThreshMod(Integer threshMod) {
		this.threshMod = threshMod;
	}

	public Integer getArmourMod() {
		return armourMod;
	}

	public void setArmourMod(Integer armourMod) {
		this.armourMod = armourMod;
	}

	public Integer getResMod() {
		return resMod;
	}

	public void setResMod(Integer resMod) {
		this.resMod = resMod;
	}
	
	@OneToOne(mappedBy="wearer")
	public ChestItem getChestItem() {
		return chestItem;
	}

	public void setChestItem(ChestItem chestItem) {
		this.chestItem = chestItem;
	}

	@OneToOne(mappedBy="wearer")
	public BootsItem getBootsItem() {
		return bootsItem;
	}

	public void setBootsItem(BootsItem bootsItem) {
		this.bootsItem = bootsItem;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}
	
	@OneToOne(mappedBy="wearer")
	public AmuletItem getAmuletItem() {
		return amuletItem;
	}

	public void setAmuletItem(AmuletItem amuletItem) {
		this.amuletItem = amuletItem;
	}

	public Integer getStunnedEnemySpeed() {
		return stunnedEnemySpeed;
	}

	public void setStunnedEnemySpeed(Integer stunnedEnemySpeed) {
		this.stunnedEnemySpeed = stunnedEnemySpeed;
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

	public Integer getCritStrikeDamMod() {
		return critStrikeDamMod;
	}

	public void setCritStrikeDamMod(Integer critStrikeDamMod) {
		this.critStrikeDamMod = critStrikeDamMod;
	}

	@OneToOne(mappedBy="wearer")
	public RingItem getRingItem1() {
		return ringItem1;
	}

	public void setRingItem1(RingItem ringItem1) {
		this.ringItem1 = ringItem1;
	}

	@OneToOne(mappedBy="wearer")
	public RingItem getRingItem2() {
		return ringItem2;
	}

	public void setRingItem2(RingItem ringItem2) {
		this.ringItem2 = ringItem2;
	}

	public Integer getFlatDam() {
		return flatDam;
	}

	public void setFlatDam(Integer flatDam) {
		this.flatDam = flatDam;
	}

}
