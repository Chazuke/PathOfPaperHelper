package com.example.demo;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Items.AmuletItem;
import com.example.demo.Items.BootsItem;
import com.example.demo.Items.ChestItem;
import com.example.demo.Items.HeadItem;
import com.example.demo.Items.RingItem;
import com.example.demo.Repositories.AmuletItemRepository;
import com.example.demo.Repositories.BootsItemRepository;
import com.example.demo.Repositories.CharacterRepository;
import com.example.demo.Repositories.ChestItemRepository;
import com.example.demo.Repositories.HeadItemRepository;
import com.example.demo.Repositories.RingItemRepository;
import com.example.demo.Repositories.UserRepository;

@RestController
@Controller
@RequestMapping(path="/db")
public class DBServicesController {
	public static int callCounter = 0;
	static Logger log = Logger.getLogger(DBServicesController.class.getName());
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private HeadItemRepository headItemRepository;
	
	@Autowired
	private ChestItemRepository chestItemRepository;
	
	@Autowired
	private BootsItemRepository bootsItemRepository;
	
	@Autowired
	private AmuletItemRepository amuletItemRepository;
	
	@Autowired
	private RingItemRepository ringItemRepository;
	
	@Autowired
	private CharacterRepository characterRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	@Transactional
	@GetMapping(path="/createUser")
    public @ResponseBody String addNewUser (@RequestParam(value="username", required=true) String username, 
    										@RequestParam(value="password", required=true) String password) {
		User n = new User();
		n.setUsername(username);
		n.setPassword(password);
		if (userRepository.exists(n.getUsername())) {
			return "User Already Exists";
		}
		userRepository.save(n);
		return "Saved";
    }
    
	@Transactional
    @GetMapping(path="/listAllUsers")
    public @ResponseBody Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}
    
    @Transactional
    @GetMapping(path="/checkCreds")
    public @ResponseBody Boolean checkCreds(@RequestParam(value="username", required=true) String username, 
    										@RequestParam(value="password", required=true) String password) {
    	User n = userRepository.findByUsername(username);
    	if (n != null) {
    		if (n.getPassword().equals(password)) return true;
    		return false;
    	}
    	else return false;
    }
    
    @Transactional
    @RequestMapping("/rollHeadItem")
    public @ResponseBody HeadItem rollHeadItem(@RequestParam(value="tier", required=true) Integer tier, @RequestParam(value="rarity", required =false) String rarity,
    		@RequestParam(value="name", required=false) String name) {
    	HeadItem ret = new HeadItem();
    	Random rand = new Random();
    	    	
    	//Set name
    	if (name != null) ret.setName(name);
    	
    	//Set item tier
    	ret.setTier(tier);
    	
    	//Set armor type and implicit effects
    	int type = rand.nextInt(3) + 1;
    	switch (type) {
    	case 1:
    		ret.setType("Cloth");
    		ret.setEsMod(ret.getEsMod() + 10);
    		ret.setImplicit("+10 to Energy Shield");
    		break;
    	case 2:
    		ret.setType("Leather");
    		ret.setResMod(ret.getResMod() + 2);
    		ret.setImplicit("+2 to Elemental Resistance");
    		break;
    	case 3:
    		ret.setType("Heavy Plate");
    		ret.setArmourMod(ret.getArmourMod() + 3);
    		ret.setManeuverMod(ret.getManeuverMod() - 2);
    		ret.setAthleticsMod(ret.getAthleticsMod() - 2);
    		ret.setImplicit("+3 to Armour, -2 to Maneuver and Athletics");
    		break;
    	default:
    		ret.setType(null);
    		ret.setImplicit(null);
    	}
    	    	
    	//Set item rarity
    	if (rarity == null || rarity.equals("Random")) {
	    	int rarityRoll = rand.nextInt(10) + 1;
	    	if (rarityRoll == 1) ret.setRarity("Normal");
	    	if (rarityRoll > 1 && rarityRoll <= 7) ret.setRarity("Magic");
	    	else if (rarityRoll > 7 && rarityRoll < 10) ret.setRarity("Rare");
	    	else if (rarityRoll == 10)  {
	    		rarityRoll = rand.nextInt(10)+1;
	    		if (rarityRoll > 5) {
	    			ret.setRarity("Signature");
	    		} else ret.setRarity("Rare");
	    	}
    	} 	
    	else ret.setRarity(rarity);
    	
    	//Set item mods
    	switch (ret.getRarity()) {
    	case "Magic":
    		rollMod(ret);
    		break;
    		
    	case "Rare":
    		rollMod(ret);
    		rollMod(ret);
    		break;
    		
    	case "Signature":
    		rollMod(ret);
    		rollMod(ret);
    		rollSignature(ret);
    		break;
    		
    	default:
    		break;
    	}
    	
    	//Save item to db
    	headItemRepository.save(ret);

    	return ret;
    }
    
    @Transactional
    @RequestMapping("/rollChestItem")
    public @ResponseBody ChestItem rollChestItem(@RequestParam(value="tier", required=true) Integer tier, @RequestParam(value="rarity", required =false) String rarity,
    		@RequestParam(value="name", required=false) String name) {
    	ChestItem ret = new ChestItem();
    	Random rand = new Random();
    	    	
    	//Set name
    	if (name != null) ret.setName(name);
    	
    	//Set item tier
    	ret.setTier(tier);
    	
    	//Set armor type and implicit effects
    	int type = rand.nextInt(3) + 1;
    	switch (type) {
    	case 1:
    		ret.setType("Cloth");
    		ret.setEsMod(ret.getEsMod() + 20);
    		ret.setImplicit("+20 to Energy Shield");
    		break;
    	case 2:
    		ret.setType("Leather");
    		ret.setResMod(ret.getResMod() + 4);
    		ret.setImplicit("+4 to Elemental Resistance");
    		break;
    	case 3:
    		ret.setType("Heavy Plate");
    		ret.setArmourMod(ret.getArmourMod() + 6);
    		ret.setManeuverMod(ret.getManeuverMod() - 4);
    		ret.setAthleticsMod(ret.getAthleticsMod() - 4);
    		ret.setImplicit("+6 to Armour, -4 to Maneuver and Athletics");
    		break;
    	default:
    		ret.setType(null);
    		ret.setImplicit(null);
    	}
    	    	
    	//Set item rarity
    	if (rarity == null || rarity.equals("Random")) {
	    	int rarityRoll = rand.nextInt(10) + 1;
	    	if (rarityRoll == 1) ret.setRarity("Normal");
	    	if (rarityRoll > 1 && rarityRoll <= 7) ret.setRarity("Magic");
	    	else if (rarityRoll > 7 && rarityRoll < 10) ret.setRarity("Rare");
	    	else if (rarityRoll == 10)  {
	    		rarityRoll = rand.nextInt(10)+1;
	    		if (rarityRoll > 5) {
	    			ret.setRarity("Signature");
	    		} else ret.setRarity("Rare");
	    	}
    	} 	
    	else ret.setRarity(rarity);
    	
    	//Set item mods
    	switch (ret.getRarity()) {
    	case "Magic":
    		rollMod(ret);
    		break;
    		
    	case "Rare":
    		rollMod(ret);
    		rollMod(ret);
    		break;
    		
    	case "Signature":
    		rollMod(ret);
    		rollMod(ret);
    		rollSignature(ret);
    		break;
    		
    	default:
    		break;
    	}
    	
    	//Save item to db
    	chestItemRepository.save(ret);

    	return ret;
    }
    
    @Transactional
    @RequestMapping("/rollBootsItem")
    public @ResponseBody BootsItem rollBootsItem(@RequestParam(value="tier", required=true) Integer tier, @RequestParam(value="rarity", required =false) String rarity,
    		@RequestParam(value="name", required=false) String name) {
    	BootsItem ret = new BootsItem();
    	Random rand = new Random();
    	    	
    	//Set name
    	if (name != null) ret.setName(name);
    	
    	//Set item tier
    	ret.setTier(tier);
    	
    	//Set armor type and implicit effects
    	int type = rand.nextInt(3) + 1;
    	switch (type) {
    	case 1:
    		ret.setType("Cloth");
    		ret.setEsMod(ret.getEsMod() + 10);
    		ret.setImplicit("+10 to Energy Shield");
    		break;
    	case 2:
    		ret.setType("Leather");
    		ret.setResMod(ret.getResMod() + 2);
    		ret.setImplicit("+2 to Elemental Resistance");
    		break;
    	case 3:
    		ret.setType("Heavy Plate");
    		ret.setArmourMod(ret.getArmourMod() + 3);
    		ret.setManeuverMod(ret.getManeuverMod() - 2);
    		ret.setAthleticsMod(ret.getAthleticsMod() - 2);
    		ret.setImplicit("+3 to Armour, -2 to Maneuver and Athletics");
    		break;
    	default:
    		ret.setType(null);
    		ret.setImplicit(null);
    	}
    	    	
    	//Set item rarity
    	if (rarity == null || rarity.equals("Random")) {
	    	int rarityRoll = rand.nextInt(10) + 1;
	    	if (rarityRoll == 1) ret.setRarity("Normal");
	    	if (rarityRoll > 1 && rarityRoll <= 7) ret.setRarity("Magic");
	    	else if (rarityRoll > 7 && rarityRoll < 10) ret.setRarity("Rare");
	    	else if (rarityRoll == 10)  {
	    		rarityRoll = rand.nextInt(10)+1;
	    		if (rarityRoll > 5) {
	    			ret.setRarity("Signature");
	    		} else ret.setRarity("Rare");
	    	}
    	} 	
    	else ret.setRarity(rarity);
    	
    	//Set item mods
    	switch (ret.getRarity()) {
    	case "Magic":
    		rollMod(ret);
    		break;
    		
    	case "Rare":
    		rollMod(ret);
    		rollMod(ret);
    		break;
    		
    	case "Signature":
    		rollMod(ret);
    		rollMod(ret);
    		rollSignature(ret);
    		break;
    		
    	default:
    		break;
    	}
    	
    	//Save item to db
    	bootsItemRepository.save(ret);

    	return ret;
    }
    
    @Transactional
    @RequestMapping("/rollAmuletItem")
    public @ResponseBody AmuletItem rollAmuletItem(@RequestParam(value="tier", required=true) Integer tier, @RequestParam(value="rarity", required =false) String rarity,
    		@RequestParam(value="name", required=false) String name) {
    	AmuletItem ret = new AmuletItem();
    	Random rand = new Random();
    	    	
    	//Set name
    	if (name != null) ret.setName(name);
    	
    	//Set item tier
    	ret.setTier(tier);
    	
    	//Set armor type and implicit effects
    	int type = rand.nextInt(6) + 1;
    	int mod;
    	switch (type) {
    	case 1:
    		mod = rand.nextInt(4)+1;
    		ret.setType("Onyx");
    		ret.setResMod(mod + (tier*2));
    		ret.setImplicit("+" + ret.getResMod() + " to Elemental Resistances");
    		break;
    	case 2:
    		mod = rand.nextInt(10)+1;
    		ret.setType("Lapis");
    		ret.setIntMod(mod + tier*5);
    		ret.setImplicit("+" + ret.getIntMod() + " to Intelligence");
    		break;
    	case 3:
    		mod = rand.nextInt(10)+1;
    		ret.setType("Amber");
    		ret.setStrMod(mod + tier*5);
    		ret.setImplicit("+" + ret.getStrMod() + " to Strength");
    		break;
    	case 4:
    		mod = rand.nextInt(10)+1;
    		ret.setType("Jade");
    		ret.setDexMod(mod + tier*5);
    		ret.setImplicit("+" + ret.getDexMod() + " to Strength");
    		break;
    	case 5:
    		mod = rand.nextInt(10)+1;
    		ret.setType("Coral");
    		ret.setLifeMod(mod*2 + tier*10);
    		ret.setImplicit("+" + ret.getLifeMod() + " to Life");
    		break;
    	case 6:
    		mod = rand.nextInt(4)+1;
    		ret.setType("Paua");
    		ret.setManaMod(mod + tier*2);
    		ret.setImplicit("+" + ret.getManaMod() + " to Mana");
    		break;
    	default:
    		ret.setType(null);
    		ret.setImplicit(null);
    	}
    	    	
    	//Set item rarity
    	if (rarity == null || rarity.equals("Random")) {
	    	int rarityRoll = rand.nextInt(10) + 1;
	    	if (rarityRoll == 1) ret.setRarity("Normal");
	    	if (rarityRoll > 1 && rarityRoll <= 7) ret.setRarity("Magic");
	    	else if (rarityRoll > 7 && rarityRoll < 10) ret.setRarity("Rare");
	    	else if (rarityRoll == 10)  {
	    		rarityRoll = rand.nextInt(10)+1;
	    		if (rarityRoll > 5) {
	    			ret.setRarity("Signature");
	    		} else ret.setRarity("Rare");
	    	}
    	} 	
    	else ret.setRarity(rarity);
    	
    	//Set item mods
    	switch (ret.getRarity()) {
    	case "Magic":
    		rollMod(ret);
    		break;
    		
    	case "Rare":
    		rollMod(ret);
    		rollMod(ret);
    		break;
    		
    	case "Signature":
    		rollMod(ret);
    		rollMod(ret);
    		rollSignature(ret);
    		break;
    		
    	default:
    		break;
    	}
    	
    	//Save item to db
    	amuletItemRepository.save(ret);

    	return ret;
    }
    
    @Transactional
    @RequestMapping("/rollRingItem")
    public @ResponseBody RingItem rollRingItem(@RequestParam(value="tier", required=true) Integer tier, @RequestParam(value="rarity", required =false) String rarity,
    		@RequestParam(value="name", required=false) String name) {
    	RingItem ret = new RingItem();
    	Random rand = new Random();
    	    	
    	//Set name
    	if (name != null) ret.setName(name);
    	
    	//Set item tier
    	ret.setTier(tier);
    	
    	//Set armor type and implicit effects
    	int type = rand.nextInt(4) + 1;
    	int mod;
    	switch (type) {
    	case 1:
    		mod = rand.nextInt(4)+1;
    		ret.setType("Onyx");
    		ret.setResMod(mod + (tier*2));
    		ret.setImplicit("+" + ret.getResMod() + " to Elemental Resistances");
    		break;
    	case 2:
    		mod = (rand.nextInt(10)+1)*2;
    		ret.setType("Coral");
    		ret.setLifeMod(mod + tier*10);
    		ret.setImplicit("+" + ret.getLifeMod() + " to Intelligence");
    		break;
    	case 3:
    		mod = rand.nextInt(4)+1;
    		ret.setType("Paua");
    		ret.setManaMod(mod + tier*2);
    		ret.setImplicit("+" + ret.getManaMod() + " to Strength");
    		break;
    	case 4:
    		ret.setType("Iron");
    		ret.setWam(tier);
    		ret.setImplicit("+" + ret.getWam() + " to Strength");
    		break;
    	default:
    		ret.setType(null);
    		ret.setImplicit(null);
    	}
    	    	
    	//Set item rarity
    	if (rarity == null || rarity.equals("Random")) {
	    	int rarityRoll = rand.nextInt(10) + 1;
	    	if (rarityRoll == 1) ret.setRarity("Normal");
	    	if (rarityRoll > 1 && rarityRoll <= 7) ret.setRarity("Magic");
	    	else if (rarityRoll > 7 && rarityRoll < 10) ret.setRarity("Rare");
	    	else if (rarityRoll == 10)  {
	    		rarityRoll = rand.nextInt(10)+1;
	    		if (rarityRoll > 5) {
	    			ret.setRarity("Signature");
	    		} else ret.setRarity("Rare");
	    	}
    	} 	
    	else ret.setRarity(rarity);
    	
    	//Set item mods
    	switch (ret.getRarity()) {
    	case "Magic":
    		rollMod(ret);
    		break;
    		
    	case "Rare":
    		rollMod(ret);
    		rollMod(ret);
    		break;
    		
    	case "Signature":
    		rollMod(ret);
    		rollMod(ret);
    		rollSignature(ret);
    		break;
    		
    	default:
    		break;
    	}
    	
    	//Save item to db
    	ringItemRepository.save(ret);

    	return ret;
    }
    
    @Transactional
    @RequestMapping("/applyItem")
    public @ResponseBody String applyItemToChar(@RequestParam(value="itemType", required=true) String type, 
    											@RequestParam(value="id", required=true) Long id, 
    											@RequestParam(value="char_id", required=true) Long charId,
    											@RequestParam(value="overwriteRing", required=false) Integer overwrite) {
    	if (type == null) return "Failed: Type was NULL";
    	Character character = characterRepository.findById(charId);
    	//log.info("Call Counter: " + callCounter++);
    	//log.info("Applying Item Type: " + type + " with Item ID: " + id + " to Character with Character ID: " + charId);
    	switch(type) {
    	case "Head":
    		HeadItem headItem = headItemRepository.findById(id);
    		//log.info("Confirm New Head Item ID: " + item.getId());
    		//log.info("Confirm Character ID: " + character.getId());
    		if (headItem == null || character == null) return "Failed: Object(s) were null";
    		//If character is already wearing a head item, remove it
    		if (character.getHeadItem() != null) {
        		//log.info("Removing Old Head Item with ID: " + character.getHeadItem().getId());
    			removeHeadItem(character, character.getHeadItem());
    		}
    		//Tell the character that it now wears the new head item
    		character.setHeadItem(headItem);
    		//Tell the item that it is now being worn by the character
    		headItem.setWearer(character);
    		//Update the values for the character
    		applyHeadItem(character, headItem);
    		return "Applied Successfully";
    	
    	case "Chest":
    		ChestItem chestItem = chestItemRepository.findById(id);
    		if (chestItem == null || character == null) return "Failed: Object(s) were null";
    		if (character.getChestItem() != null) {
        		//log.info("Removing Old Chest Item with ID: " + character.getChestItem().getId());
    			removeChestItem(character, character.getChestItem());
    		}
    		character.setChestItem(chestItem);
    		chestItem.setWearer(character);
    		applyChestItem(character, chestItem);
    		return "Applied Successfully";
    		
    	case "Boots":
    		BootsItem bootsItem = bootsItemRepository.findById(id);
    		if (bootsItem == null || character == null) return "Failed: Object(s) were null";
    		if (character.getBootsItem() != null) {
        		//log.info("Removing Old Boots Item with ID: " + character.getBootsItem().getId());
    			removeBootsItem(character, character.getBootsItem());
    		}
    		character.setBootsItem(bootsItem);
    		bootsItem.setWearer(character);
    		applyBootsItem(character, bootsItem);
    		return "Applied Successfully";
    		
    	case "Amulet":
    		AmuletItem amuletItem = amuletItemRepository.findById(id);
    		if (amuletItem == null || character == null) return "Failed: Object(s) were null";
    		if (character.getAmuletItem() != null) {
        		//log.info("Removing Old Amulet Item with ID: " + character.getAmuletItem().getId());
    			removeAmuletItem(character, character.getAmuletItem());
    		}
    		character.setAmuletItem(amuletItem);
    		amuletItem.setWearer(character);
    		applyAmuletItem(character, amuletItem);
    		return "Applied Successfully";
    		
    	case "Ring":
    		RingItem ringItem = ringItemRepository.findById(id);
    		if (ringItem == null || character == null) return "Failed: Object(s) were null";
    		
    		if (character.getRingItem1() == null) {
    			character.setRingItem1(ringItem);
        		ringItem.setWearer(character);
        		applyRingItem(character, ringItem);
    		}
    		else if (character.getRingItem1() != null && character.getRingItem2() == null) {
    			//log.info("Removing Old Ring Item with ID: " + character.getRingItem().getId());
    			removeRingItem(character, character.getRingItem2());
    			character.setRingItem2(ringItem);
        		ringItem.setWearer(character);
        		applyRingItem(character, ringItem);
    		}
    		if (character.getRingItem1() != null && character.getRingItem2() != null) {
    			if (overwrite == null) return "Failed: Cannot Determine Which Ring to Overwrite";
        		//log.info("Removing Old Ring Item with ID: " + character.getRingItem().getId());
    			switch (overwrite) {
    			case 1:
    				removeRingItem(character, character.getRingItem1());
	    			character.setRingItem1(ringItem);
	    			break;
	    			
    			case 2:
    				removeRingItem(character, character.getRingItem2());
	    			character.setRingItem2(ringItem);
	    			break;
	    			
    			default:
    				return "Failed: Bad Argument for Ring Overwrite";
    			}
        		ringItem.setWearer(character);
        		applyRingItem(character, ringItem);
    		}
    		return "Applied Successfully";
    		
    	default:
    		return "Failed: Bad Type";
    	}
    }
    
    @Transactional
    @RequestMapping("/createCharacter")
    public @ResponseBody String createCharacter(@RequestBody User owner, @RequestParam(value="name", required=true) String name, @RequestParam(value="description", required=false) String desc) {
    	Character newChar;
    	if (desc == null) newChar = new Character(owner, name);
    	else newChar = new Character(owner, name, desc);
    	
    	characterRepository.save(newChar);
    	return "Saved";
    }
    
    @Transactional
    @RequestMapping("/getCharacters")
    public @ResponseBody List<Character> getCharacters(@RequestBody User owner) {
    	List<Character> chars = characterRepository.findAllByOwner(owner.getUsername());
    	return chars;
    }
    
    private void rollSignature(HeadItem item) {
    	String[] signatures = {"Ghost", "Ogre", "Soul Hunter", "Spider",
    							"Feral", "Living Item", "Pre-Cognisant",
    							"Camouflaged", "Water Walking", "Lucky"};
    	
    	Random rand = new Random();
    	int roll = rand.nextInt(10);
    	item.setSignature(signatures[roll]);
    	
    }
    
    private void rollSignature(ChestItem item) {
    	String[] signatures = {"Ghost", "Ogre", "Soul Hunter", "Spider",
    							"Feral", "Living Item", "Pre-Cognisant",
    							"Camouflaged", "Water Walking", "Lucky"};
    	
    	Random rand = new Random();
    	int roll = rand.nextInt(10);
    	item.setSignature(signatures[roll]);
    	
    }
    
    private void rollSignature(BootsItem item) {
    	String[] signatures = {"Ghost", "Ogre", "Soul Hunter", "Spider",
    							"Feral", "Living Item", "Pre-Cognisant",
    							"Camouflaged", "Water Walking", "Lucky"};
    	
    	Random rand = new Random();
    	int roll = rand.nextInt(10);
    	item.setSignature(signatures[roll]);
    	
    }
    
    private void rollSignature(AmuletItem item) {
    	String[] signatures = {"Ghost", "Ogre", "Soul Hunter", "Spider",
    							"Feral", "Living Item", "Pre-Cognisant",
    							"Camouflaged", "Water Walking", "Lucky"};
    	
    	Random rand = new Random();
    	int roll = rand.nextInt(10);
    	item.setSignature(signatures[roll]);
    	
    }
    
    private void rollSignature(RingItem item) {
    	String[] signatures = {"Ghost", "Ogre", "Soul Hunter", "Spider",
    							"Feral", "Living Item", "Pre-Cognisant",
    							"Camouflaged", "Water Walking", "Lucky"};
    	
    	Random rand = new Random();
    	int roll = rand.nextInt(10);
    	item.setSignature(signatures[roll]);
    	
    }
    
    private void rollMod(HeadItem item) {
    	String[] mods = {"str", "int", "dex",
    					"thresh", "res", "life",
    					"special"};
    	
    	Random rand = new Random();
		int mod = rand.nextInt(7);
		int tierBonus;
		switch (mods[mod]) {
		case "str":
			mod = rand.nextInt(10)+1;
			tierBonus = item.getTier()*5;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to Strength");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to Strength");
			}
			item.setStrMod(item.getStrMod() + (mod + tierBonus));
			break;
			
		case "int":
			mod = rand.nextInt(10)+1;
			tierBonus = item.getTier()*5;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to Intelligence");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to Intelligence");
			}
			item.setIntMod(item.getIntMod() + (mod + tierBonus));
			break;
			
		case "dex":
			mod = rand.nextInt(10)+1;
			tierBonus = item.getTier()*5;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to Dexterity");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to Dexterity");
			}
			item.setDexMod(item.getDexMod() + (mod + tierBonus));
			break;
			
		case "thresh":
			mod = rand.nextInt(10)+1;
			tierBonus = item.getTier()*6;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to Threshold");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to Threshold");
			}
			item.setThreshMod(item.getThreshMod() + (mod + tierBonus));
			break;
			
		case "res":
			mod = rand.nextInt(4)+1;
			tierBonus = item.getTier()*2;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to Elemental Resistance");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to Elemental Resistance");
			}
			item.setResMod(item.getResMod() + (mod + tierBonus));
			break;
			
		case "life":
			mod = (rand.nextInt(10)+1)*2;
			tierBonus = item.getTier()*10;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to Life");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to Life");
			}
			item.setLifeMod(item.getLifeMod() + (mod + tierBonus));
			break;
			
		case "special":
			switch (item.getType()) {
			case "Cloth":
				mod = (rand.nextInt(10)+1)*2;
				tierBonus = item.getTier()*10;
				if (item.getExplicit1().equals("None")) {
					item.setExplicit1("+" + (mod + tierBonus) + " to Energy Shield (Cloth Only)");
				}
				else {
					item.setExplicit2("+" + (mod + tierBonus) + " to Energy Shield (Cloth Only)");
				}
				item.setEsMod(item.getEsMod() + (mod + tierBonus));
				break;
				
			case "Leather":
				mod = rand.nextInt(4)+1;
				tierBonus = item.getTier()*2;
				if (item.getExplicit1().equals("None")) {
					item.setExplicit1("+" + (mod + tierBonus) + " to Elemental Resistance (Leather Only)");
				}
				else {
					item.setExplicit2("+" + (mod + tierBonus) + " to Elemental Resistance (Leather Only)");
				}
				item.setResMod(item.getResMod() + (mod + tierBonus));
				break;
				
			case "Heavy Plate":
				mod = rand.nextInt(4)+1;
				tierBonus = item.getTier()*2;
				if (item.getExplicit1().equals("None")) {
					item.setExplicit1("+" + (mod + tierBonus) + " to Armour (Heavy Plate Only)");
				}
				else {
					item.setExplicit2("+" + (mod + tierBonus) + " to Armour (Heavy Plate Only)");
				}
				item.setResMod(item.getResMod() + (mod + tierBonus));
				break;
			}
			break;
			
		}
    	
    }
    
    private void rollMod(ChestItem item) {
    	String[] mods = {"str", "int", "dex",
    					"thresh", "res", "life",
    					"special"};
    	
    	Random rand = new Random();
		int mod = rand.nextInt(7);
		int tierBonus;
		switch (mods[mod]) {
		case "str":
			mod = rand.nextInt(10)+1;
			tierBonus = item.getTier()*5;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to Strength");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to Strength");
			}
			item.setStrMod(item.getStrMod() + (mod + tierBonus));
			break;
			
		case "int":
			mod = rand.nextInt(10)+1;
			tierBonus = item.getTier()*5;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to Intelligence");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to Intelligence");
			}
			item.setIntMod(item.getIntMod() + (mod + tierBonus));
			break;
			
		case "dex":
			mod = rand.nextInt(10)+1;
			tierBonus = item.getTier()*5;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to Dexterity");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to Dexterity");
			}
			item.setDexMod(item.getDexMod() + (mod + tierBonus));
			break;
			
		case "thresh":
			mod = rand.nextInt(10)+1;
			tierBonus = item.getTier()*6;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to Threshold");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to Threshold");
			}
			item.setThreshMod(item.getThreshMod() + (mod + tierBonus));
			break;
			
		case "res":
			mod = rand.nextInt(4)+1;
			tierBonus = item.getTier()*2;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to Elemental Resistance");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to Elemental Resistance");
			}
			item.setResMod(item.getResMod() + (mod + tierBonus));
			break;
			
		case "life":
			mod = (rand.nextInt(10)+1)*2;
			tierBonus = item.getTier()*10;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to Life");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to Life");
			}
			item.setLifeMod(item.getLifeMod() + (mod + tierBonus));
			break;
			
		case "special":
			switch (item.getType()) {
			case "Cloth":
				mod = (rand.nextInt(10)+1)*2;
				tierBonus = item.getTier()*10;
				if (item.getExplicit1().equals("None")) {
					item.setExplicit1("+" + (mod + tierBonus) + " to Energy Shield (Cloth Only)");
				}
				else {
					item.setExplicit2("+" + (mod + tierBonus) + " to Energy Shield (Cloth Only)");
				}
				item.setEsMod(item.getEsMod() + (mod + tierBonus));
				break;
				
			case "Leather":
				mod = rand.nextInt(4)+1;
				tierBonus = item.getTier()*2;
				if (item.getExplicit1().equals("None")) {
					item.setExplicit1("+" + (mod + tierBonus) + " to Elemental Resistance (Leather Only)");
				}
				else {
					item.setExplicit2("+" + (mod + tierBonus) + " to Elemental Resistance (Leather Only)");
				}
				item.setResMod(item.getResMod() + (mod + tierBonus));
				break;
				
			case "Heavy Plate":
				mod = rand.nextInt(4)+1;
				tierBonus = item.getTier()*2;
				if (item.getExplicit1().equals("None")) {
					item.setExplicit1("+" + (mod + tierBonus) + " to Armour (Heavy Plate Only)");
				}
				else {
					item.setExplicit2("+" + (mod + tierBonus) + " to Armour (Heavy Plate Only)");
				}
				item.setResMod(item.getResMod() + (mod + tierBonus));
				break;
			}
			break;
			
		}
    	
    }
    
    private void rollMod(BootsItem item) {
    	String[] mods = {"str", "int", "dex",
    					"thresh", "res", "life",
    					"special", "speed", "maneuver"};
    	
    	Random rand = new Random();
		int mod = rand.nextInt(9);
		int tierBonus;
		switch (mods[mod]) {
		case "str":
			mod = rand.nextInt(10)+1;
			tierBonus = item.getTier()*5;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to Strength");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to Strength");
			}
			item.setStrMod(item.getStrMod() + (mod + tierBonus));
			break;
			
		case "int":
			mod = rand.nextInt(10)+1;
			tierBonus = item.getTier()*5;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to Intelligence");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to Intelligence");
			}
			item.setIntMod(item.getIntMod() + (mod + tierBonus));
			break;
			
		case "dex":
			mod = rand.nextInt(10)+1;
			tierBonus = item.getTier()*5;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to Dexterity");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to Dexterity");
			}
			item.setDexMod(item.getDexMod() + (mod + tierBonus));
			break;
			
		case "thresh":
			mod = rand.nextInt(10)+1;
			tierBonus = item.getTier()*6;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to Threshold");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to Threshold");
			}
			item.setThreshMod(item.getThreshMod() + (mod + tierBonus));
			break;
			
		case "res":
			mod = rand.nextInt(4)+1;
			tierBonus = item.getTier()*2;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to Elemental Resistance");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to Elemental Resistance");
			}
			item.setResMod(item.getResMod() + (mod + tierBonus));
			break;
			
		case "life":
			mod = (rand.nextInt(10)+1)*2;
			tierBonus = item.getTier()*10;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to Life");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to Life");
			}
			item.setLifeMod(item.getLifeMod() + (mod + tierBonus));
			break;
			
		case "special":
			switch (item.getType()) {
			case "Cloth":
				mod = (rand.nextInt(10)+1)*2;
				tierBonus = item.getTier()*10;
				if (item.getExplicit1().equals("None")) {
					item.setExplicit1("+" + (mod + tierBonus) + " to Energy Shield (Cloth Only)");
				}
				else {
					item.setExplicit2("+" + (mod + tierBonus) + " to Energy Shield (Cloth Only)");
				}
				item.setEsMod(item.getEsMod() + (mod + tierBonus));
				break;
				
			case "Leather":
				mod = rand.nextInt(4)+1;
				tierBonus = item.getTier()*2;
				if (item.getExplicit1().equals("None")) {
					item.setExplicit1("+" + (mod + tierBonus) + " to Elemental Resistance (Leather Only)");
				}
				else {
					item.setExplicit2("+" + (mod + tierBonus) + " to Elemental Resistance (Leather Only)");
				}
				item.setResMod(item.getResMod() + (mod + tierBonus));
				break;
				
			case "Heavy Plate":
				mod = rand.nextInt(4)+1;
				tierBonus = item.getTier()*2;
				if (item.getExplicit1().equals("None")) {
					item.setExplicit1("+" + (mod + tierBonus) + " to Armour (Heavy Plate Only)");
				}
				else {
					item.setExplicit2("+" + (mod + tierBonus) + " to Armour (Heavy Plate Only)");
				}
				item.setResMod(item.getResMod() + (mod + tierBonus));
				break;
			}
			break;
			
		case "speed":
			mod = 1;
			tierBonus = item.getTier();
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to Speed (Boots Only)");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to Speed (Boots Only)");
			}
			item.setSpeedMod(item.getSpeedMod() + (mod + tierBonus));
			break;
			
		case "maneuver":
			mod = rand.nextInt(10)+1;
			tierBonus = item.getTier()*5;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to Maneuver Skill (Boots Only)");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to Maneuver Skill (Boots Only)");
			}
			item.setManeuverMod(item.getManeuverMod() + (mod + tierBonus));
			break;
			
		}
    }
    
    private void rollMod(AmuletItem item) {
    	String[] mods = {"str", "int", "dex",
    					"stun", "shock", "lifeleech",
    					"manaleech", "armour", "life",
    					"crit", "wam"};
    	
    	Random rand = new Random();
		int mod = rand.nextInt(11);
		int tierBonus;
		switch (mods[mod]) {
		case "str":
			mod = rand.nextInt(10)+1;
			tierBonus = item.getTier()*5;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to Strength");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to Strength");
			}
			item.setStrMod(item.getStrMod() + (mod + tierBonus));
			break;
			
		case "int":
			mod = rand.nextInt(10)+1;
			tierBonus = item.getTier()*5;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to Intelligence");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to Intelligence");
			}
			item.setIntMod(item.getIntMod() + (mod + tierBonus));
			break;
			
		case "dex":
			mod = rand.nextInt(10)+1;
			tierBonus = item.getTier()*5;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to Dexterity");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to Dexterity");
			}
			item.setDexMod(item.getDexMod() + (mod + tierBonus));
			break;
			
		case "stun":
			tierBonus = item.getTier();
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("Reduce Stunned Enemy Speed by " + tierBonus);
			}
			else {
				item.setExplicit2("Reduce Stunned Enemy Speed by " + tierBonus);
			}
			item.setEnemyStunSpeedMod(tierBonus);
			break;
			
		case "shock":
			tierBonus = item.getTier();
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + tierBonus + " WAM Against Shocked Enemies For You and Your Allies");
			}
			else {
				item.setExplicit2("+" + tierBonus + " WAM Against Shocked Enemies For You and Your Allies");
			}
			item.setWamAgainstShock(tierBonus);
			break;
			
		case "lifeleech":
			tierBonus = item.getTier()*2;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + tierBonus + " to Life Leech");
			}
			else {
				item.setExplicit2("+" + tierBonus + " to Life Leech");
			}
			item.setLifeLeech(tierBonus);
			break;
			
		case "manaleech":
			tierBonus = item.getTier();
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + tierBonus + " to Mana Leech");
			}
			else {
				item.setExplicit2("+" + tierBonus + " to Mana Leech");
			}
			item.setManaLeech(tierBonus);
			break;
			
		case "armour":
			mod = rand.nextInt(4)+1;
			tierBonus = item.getTier()*2;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to Armour");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to Armour");
			}
			item.setArmourMod(mod + tierBonus);
			break;
			
		case "life":
			mod = (rand.nextInt(10)+1)*2;
			tierBonus = item.getTier()*10;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to Life");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to Life");
			}
			item.setLifeMod(mod + tierBonus);
			break;
			
		case "crit":
			mod = 10;
			tierBonus = item.getTier()*10;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to Critical Strike Damage");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to Critical Strike Damage");
			}
			item.setCritStrikeDam(mod + tierBonus);
			break;
			
		case "wam":
			mod = 1;
			tierBonus = item.getTier();
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to WAM");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to WAM");
			}
			item.setWam(mod + tierBonus);
			break;
			
		}
    }

    private void rollMod(RingItem item) {
    	String[] mods = {"str", "int", "dex",
    					"dam", "lifeleech", "res",
    					"armour", "life", "wam"};
    	
    	Random rand = new Random();
		int mod = rand.nextInt(9);
		int tierBonus;
		switch (mods[mod]) {
		case "str":
			mod = rand.nextInt(10)+1;
			tierBonus = item.getTier()*5;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to Strength");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to Strength");
			}
			item.setStrMod(item.getStrMod() + (mod + tierBonus));
			break;
			
		case "int":
			mod = rand.nextInt(10)+1;
			tierBonus = item.getTier()*5;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to Intelligence");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to Intelligence");
			}
			item.setIntMod(item.getIntMod() + (mod + tierBonus));
			break;
			
		case "dex":
			mod = rand.nextInt(10)+1;
			tierBonus = item.getTier()*5;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to Dexterity");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to Dexterity");
			}
			item.setDexMod(item.getDexMod() + (mod + tierBonus));
			break;
			
		case "dam":
			mod = rand.nextInt(4)+1;
			tierBonus = item.getTier()*2;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod+tierBonus) + " to Damage");
			}
			else {
				item.setExplicit2("+" + (mod+tierBonus) + " to Damage");
			}
			item.setFlatDam(mod+tierBonus);
			break;
			
		case "lifeleech":
			tierBonus = item.getTier()*2;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + tierBonus + " to Life Leech");
			}
			else {
				item.setExplicit2("+" + tierBonus + " to Life Leech");
			}
			item.setLifeLeech(tierBonus);
			break;
			
		case "res":
			mod = rand.nextInt(4)+1;
			tierBonus = item.getTier()*2;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod+tierBonus) + " to Elemental Resistance");
			}
			else {
				item.setExplicit2("+" + (mod+tierBonus) + " to Elemental Resistance");
			}
			item.setFlatDam(mod+tierBonus);
			break;
			
		case "armour":
			mod = rand.nextInt(4)+1;
			tierBonus = item.getTier()*2;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to Armour");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to Armour");
			}
			item.setArmourMod(mod + tierBonus);
			break;
			
		case "life":
			mod = (rand.nextInt(10)+1)*2;
			tierBonus = item.getTier()*10;
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to Life");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to Life");
			}
			item.setLifeMod(mod + tierBonus);
			break;
			
		case "wam":
			mod = 1;
			tierBonus = item.getTier();
			if (item.getExplicit1().equals("None")) {
				item.setExplicit1("+" + (mod + tierBonus) + " to WAM");
			}
			else {
				item.setExplicit2("+" + (mod + tierBonus) + " to WAM");
			}
			item.setWam(mod + tierBonus);
			break;
			
		}
    }
    
    @Transactional
    private void applyHeadItem(Character character, HeadItem item) {
    	//Update Dex if there has been a change
    	if (item.getDexMod() != 0) {
    		character.setDex(character.getDex() + item.getDexMod());
    		entityManager.createNativeQuery("UPDATE characters SET dex = " + character.getDex() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update Intl if there has been a change
    	if (item.getIntMod() != 0) {
    		character.setIntl(character.getIntl() + item.getIntMod());
    		entityManager.createNativeQuery("UPDATE characters SET intl = " + character.getIntl() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update Str if there has been a change
    	if (item.getStrMod() != 0) {
    		character.setStr(character.getStr() + item.getStrMod());
    		entityManager.createNativeQuery("UPDATE characters SET str = " + character.getStr() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update ThreshMod if there has been a change
    	if (item.getThreshMod() != 0) {
    		character.setThreshMod(character.getThreshMod() + item.getThreshMod());
    		entityManager.createNativeQuery("UPDATE characters SET thresh_mod = " + character.getThreshMod() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update ResMod if there has been a change
    	if (item.getResMod() != 0) {
    		character.setResMod(character.getResMod() + item.getResMod());
    		entityManager.createNativeQuery("UPDATE characters SET res_mod = " + character.getResMod() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update BaseLife if there has been a change
    	if (item.getLifeMod() != 0) {
    		character.setBaseLife(character.getBaseLife() + item.getLifeMod());
    		entityManager.createNativeQuery("UPDATE characters SET base_life = " + character.getBaseLife() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update ArmourMod if there has been a change
    	if (item.getArmourMod() != 0) {
    		character.setArmourMod(character.getArmourMod() + item.getArmourMod());
    		entityManager.createNativeQuery("UPDATE characters SET armour_mod = " + character.getArmourMod() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update EShield if there has been a change
    	if (item.getEsMod() != 0) {
    		character.seteShield(character.geteShield() + item.getEsMod());
    		entityManager.createNativeQuery("UPDATE characters SET e_shield = " + character.geteShield() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update Maneuver skill if there has been a change
    	if (item.getManeuverMod() != 0) {
    		Map<String, Integer> skills = character.getSkills();
    		skills.put("Maneuver", skills.get("Maneuver") + item.getManeuverMod());
    		entityManager.createNativeQuery("UPDATE skills_table SET skill_level=" + skills.get("Maneuver") 
    										+ " WHERE char_id=" + character.getId() + " AND skill='Maneuver'").executeUpdate();

    	}
    	//Update Athletics skill if there has been a change
    	if (item.getAthleticsMod() != 0) {
    		Map<String, Integer> skills = character.getSkills();
    		skills.put("Athletics", skills.get("Athletics") + item.getAthleticsMod());
    		entityManager.createNativeQuery("UPDATE skills_table SET skill_level=" + skills.get("Athletics") 
    										+ " WHERE char_id=" + character.getId() + " AND skill='Athletics'").executeUpdate();
    	}

    }
    
    @Transactional
    private void applyChestItem(Character character, ChestItem item) {
    	//Update Dex if there has been a change
    	if (item.getDexMod() != 0) {
    		character.setDex(character.getDex() + item.getDexMod());
    		entityManager.createNativeQuery("UPDATE characters SET dex = " + character.getDex() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update Intl if there has been a change
    	if (item.getIntMod() != 0) {
    		character.setIntl(character.getIntl() + item.getIntMod());
    		entityManager.createNativeQuery("UPDATE characters SET intl = " + character.getIntl() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update Str if there has been a change
    	if (item.getStrMod() != 0) {
    		character.setStr(character.getStr() + item.getStrMod());
    		entityManager.createNativeQuery("UPDATE characters SET str = " + character.getStr() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update ThreshMod if there has been a change
    	if (item.getThreshMod() != 0) {
    		character.setThreshMod(character.getThreshMod() + item.getThreshMod());
    		entityManager.createNativeQuery("UPDATE characters SET thresh_mod = " + character.getThreshMod() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update ResMod if there has been a change
    	if (item.getResMod() != 0) {
    		character.setResMod(character.getResMod() + item.getResMod());
    		entityManager.createNativeQuery("UPDATE characters SET res_mod = " + character.getResMod() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update BaseLife if there has been a change
    	if (item.getLifeMod() != 0) {
    		character.setBaseLife(character.getBaseLife() + item.getLifeMod());
    		entityManager.createNativeQuery("UPDATE characters SET base_life = " + character.getBaseLife() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update ArmourMod if there has been a change
    	if (item.getArmourMod() != 0) {
    		character.setArmourMod(character.getArmourMod() + item.getArmourMod());
    		entityManager.createNativeQuery("UPDATE characters SET armour_mod = " + character.getArmourMod() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update EShield if there has been a change
    	if (item.getEsMod() != 0) {
    		character.seteShield(character.geteShield() + item.getEsMod());
    		entityManager.createNativeQuery("UPDATE characters SET e_shield = " + character.geteShield() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update Maneuver skill if there has been a change
    	if (item.getManeuverMod() != 0) {
    		Map<String, Integer> skills = character.getSkills();
    		skills.put("Maneuver", skills.get("Maneuver") + item.getManeuverMod());
    		entityManager.createNativeQuery("UPDATE skills_table SET skill_level=" + skills.get("Maneuver") 
    										+ " WHERE char_id=" + character.getId() + " AND skill='Maneuver'").executeUpdate();

    	}
    	//Update Athletics skill if there has been a change
    	if (item.getAthleticsMod() != 0) {
    		Map<String, Integer> skills = character.getSkills();
    		skills.put("Athletics", skills.get("Athletics") + item.getAthleticsMod());
    		entityManager.createNativeQuery("UPDATE skills_table SET skill_level=" + skills.get("Athletics") 
    										+ " WHERE char_id=" + character.getId() + " AND skill='Athletics'").executeUpdate();
    	}

    }
    
    @Transactional
    private void applyBootsItem(Character character, BootsItem item) {
    	//Update Dex if there has been a change
    	if (item.getDexMod() != 0) {
    		character.setDex(character.getDex() + item.getDexMod());
    		entityManager.createNativeQuery("UPDATE characters SET dex = " + character.getDex() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update Intl if there has been a change
    	if (item.getIntMod() != 0) {
    		character.setIntl(character.getIntl() + item.getIntMod());
    		entityManager.createNativeQuery("UPDATE characters SET intl = " + character.getIntl() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update Str if there has been a change
    	if (item.getStrMod() != 0) {
    		character.setStr(character.getStr() + item.getStrMod());
    		entityManager.createNativeQuery("UPDATE characters SET str = " + character.getStr() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update ThreshMod if there has been a change
    	if (item.getThreshMod() != 0) {
    		character.setThreshMod(character.getThreshMod() + item.getThreshMod());
    		entityManager.createNativeQuery("UPDATE characters SET thresh_mod = " + character.getThreshMod() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update ResMod if there has been a change
    	if (item.getResMod() != 0) {
    		character.setResMod(character.getResMod() + item.getResMod());
    		entityManager.createNativeQuery("UPDATE characters SET res_mod = " + character.getResMod() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update BaseLife if there has been a change
    	if (item.getLifeMod() != 0) {
    		character.setBaseLife(character.getBaseLife() + item.getLifeMod());
    		entityManager.createNativeQuery("UPDATE characters SET base_life = " + character.getBaseLife() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update ArmourMod if there has been a change
    	if (item.getArmourMod() != 0) {
    		character.setArmourMod(character.getArmourMod() + item.getArmourMod());
    		entityManager.createNativeQuery("UPDATE characters SET armour_mod = " + character.getArmourMod() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update EShield if there has been a change
    	if (item.getEsMod() != 0) {
    		character.seteShield(character.geteShield() + item.getEsMod());
    		entityManager.createNativeQuery("UPDATE characters SET e_shield = " + character.geteShield() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update Maneuver skill if there has been a change
    	if (item.getManeuverMod() != 0) {
    		Map<String, Integer> skills = character.getSkills();
    		skills.put("Maneuver", skills.get("Maneuver") + item.getManeuverMod());
    		entityManager.createNativeQuery("UPDATE skills_table SET skill_level=" + skills.get("Maneuver") 
    										+ " WHERE char_id=" + character.getId() + " AND skill='Maneuver'").executeUpdate();

    	}
    	//Update Athletics skill if there has been a change
    	if (item.getAthleticsMod() != 0) {
    		Map<String, Integer> skills = character.getSkills();
    		skills.put("Athletics", skills.get("Athletics") + item.getAthleticsMod());
    		entityManager.createNativeQuery("UPDATE skills_table SET skill_level=" + skills.get("Athletics") 
    										+ " WHERE char_id=" + character.getId() + " AND skill='Athletics'").executeUpdate();
    	}
    	//Update Speed if there has been a change
    	if (item.getSpeedMod() != 0) {
    		character.setSpeed(character.getSpeed() + item.getSpeedMod());
    		entityManager.createNativeQuery("UPDATE characters SET e_shield = " + character.getSpeed() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}

    }
    
    @Transactional
    private void applyAmuletItem(Character character, AmuletItem item) {
    	//Update Dex if there has been a change
    	if (item.getDexMod() != 0) {
    		character.setDex(character.getDex() + item.getDexMod());
    		entityManager.createNativeQuery("UPDATE characters SET dex = " + character.getDex() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update Intl if there has been a change
    	if (item.getIntMod() != 0) {
    		character.setIntl(character.getIntl() + item.getIntMod());
    		entityManager.createNativeQuery("UPDATE characters SET intl = " + character.getIntl() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update Str if there has been a change
    	if (item.getStrMod() != 0) {
    		character.setStr(character.getStr() + item.getStrMod());
    		entityManager.createNativeQuery("UPDATE characters SET str = " + character.getStr() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update StunnedEnemySpeed if there has been a change
    	if (item.getEnemyStunSpeedMod() != 0) {
    		character.setStunnedEnemySpeed(character.getStunnedEnemySpeed() + item.getEnemyStunSpeedMod());
    		entityManager.createNativeQuery("UPDATE characters SET stunned_enemy_speed = " + character.getStunnedEnemySpeed()
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update WamAgainstShock if there has been a change
    	if (item.getWamAgainstShock() != 0) {
    		character.setWamAgainstShock(character.getWamAgainstShock() + item.getWamAgainstShock());
    		entityManager.createNativeQuery("UPDATE characters SET wam_against_shock = " + character.getWamAgainstShock() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update LifeLeech if there has been a change
    	if (item.getLifeLeech() != 0) {
    		character.setLifeLeech(character.getLifeLeech() + item.getLifeLeech());
    		entityManager.createNativeQuery("UPDATE characters SET life_leech = " + character.getLifeLeech() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update ManaLeech if there has been a change
    	if (item.getManaLeech() != 0) {
    		character.setManaLeech(character.getManaLeech() + item.getManaLeech());
    		entityManager.createNativeQuery("UPDATE characters SET mana_leech = " + character.getManaLeech() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update ArmourMod if there has been a change
    	if (item.getArmourMod() != 0) {
    		character.setArmourMod(character.getArmourMod() + item.getArmourMod());
    		entityManager.createNativeQuery("UPDATE characters SET armour_mod = " + character.getArmourMod() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update Life if there has been a change
    	if (item.getLifeMod() != 0) {
    		character.setBaseLife(character.getBaseLife() + item.getLifeMod());
    		entityManager.createNativeQuery("UPDATE characters SET base_life=" + character.getBaseLife() 
    										+ " WHERE char_id=" + character.getId()).executeUpdate();

    	}
    	//Update CritStrikeDamage skill if there has been a change
    	if (item.getCritStrikeDam() != 0) {
    		character.setCritStrikeDamMod(character.getCritStrikeDamMod() + item.getCritStrikeDam());
    		entityManager.createNativeQuery("UPDATE characters SET base_life=" + character.getCritStrikeDamMod() 
    										+ " WHERE char_id=" + character.getId()).executeUpdate();
    	}
    	//Update ResMod if there has been a change
    	if (item.getResMod() != 0) {
    		character.setResMod(character.getResMod() + item.getResMod());
    		entityManager.createNativeQuery("UPDATE characters SET res_mod=" + character.getResMod() 
    										+ " WHERE char_id=" + character.getId()).executeUpdate();
    	}
    	//Update Mana if there has been a change
    	if (item.getManaMod() != 0) {
    		character.setBaseMana(character.getBaseMana() + item.getManaMod());
    		entityManager.createNativeQuery("UPDATE characters SET res_mod=" + character.getBaseMana() 
    										+ " WHERE char_id=" + character.getId()).executeUpdate();
    	}

    }
    
    @Transactional
    private void applyRingItem(Character character, RingItem item) {
    	//Update Dex if there has been a change
    	if (item.getDexMod() != 0) {
    		character.setDex(character.getDex() + item.getDexMod());
    		entityManager.createNativeQuery("UPDATE characters SET dex = " + character.getDex() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update Intl if there has been a change
    	if (item.getIntMod() != 0) {
    		character.setIntl(character.getIntl() + item.getIntMod());
    		entityManager.createNativeQuery("UPDATE characters SET intl = " + character.getIntl() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update Str if there has been a change
    	if (item.getStrMod() != 0) {
    		character.setStr(character.getStr() + item.getStrMod());
    		entityManager.createNativeQuery("UPDATE characters SET str = " + character.getStr() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update FlatDam if there has been a change
    	if (item.getFlatDam() != 0) {
    		character.setFlatDam(character.getFlatDam() + item.getFlatDam());
    		entityManager.createNativeQuery("UPDATE characters SET flat_dam = " + character.getFlatDam() 
											+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update LifeLeech if there has been a change
    	if (item.getLifeLeech() != 0) {
    		character.setLifeLeech(character.getLifeLeech() + item.getLifeLeech());
    		entityManager.createNativeQuery("UPDATE characters SET life_leech = " + character.getLifeLeech() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update ArmourMod if there has been a change
    	if (item.getArmourMod() != 0) {
    		character.setArmourMod(character.getArmourMod() + item.getArmourMod());
    		entityManager.createNativeQuery("UPDATE characters SET armour_mod = " + character.getArmourMod() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update Life if there has been a change
    	if (item.getLifeMod() != 0) {
    		character.setBaseLife(character.getBaseLife() + item.getLifeMod());
    		entityManager.createNativeQuery("UPDATE characters SET base_life=" + character.getBaseLife() 
    										+ " WHERE char_id=" + character.getId()).executeUpdate();

    	}
    	//Update ResMod if there has been a change
    	if (item.getResMod() != 0) {
    		character.setResMod(character.getResMod() + item.getResMod());
    		entityManager.createNativeQuery("UPDATE characters SET res_mod=" + character.getResMod() 
    										+ " WHERE char_id=" + character.getId()).executeUpdate();
    	}
    	//Update Mana if there has been a change
    	if (item.getManaMod() != 0) {
    		character.setBaseMana(character.getBaseMana() + item.getManaMod());
    		entityManager.createNativeQuery("UPDATE characters SET res_mod=" + character.getBaseMana() 
    										+ " WHERE char_id=" + character.getId()).executeUpdate();
    	}

    }
    
    @Transactional
    private void removeHeadItem(Character character, HeadItem item) {
    	//Update the headitems table to set the wearer column to null
    	entityManager.createNativeQuery("UPDATE headitems SET wearer=NULL WHERE item_id=" + item.getId()).executeUpdate();
    	//Update Dex if there has been a change
    	if (item.getDexMod() != 0) {
    		character.setDex(character.getDex() - item.getDexMod());
    		entityManager.createNativeQuery("UPDATE characters SET dex=" + character.getDex() 
    										+ " WHERE char_id=" + character.getId()).executeUpdate();
    	}
    	//Update Intl if there has been a change
    	if (item.getIntMod() != 0) {
    		character.setIntl(character.getIntl() - item.getIntMod());
    		entityManager.createNativeQuery("UPDATE characters SET intl=" + character.getIntl() 
    										+ " WHERE char_id=" + character.getId()).executeUpdate();
    	}
    	//Update Str if there has been a change
    	if (item.getStrMod() != 0) {
    		character.setStr(character.getStr() - item.getStrMod());
    		entityManager.createNativeQuery("UPDATE characters SET str=" + character.getStr() 
    										+ " WHERE char_id=" + character.getId()).executeUpdate();
    	}
    	//Update ThreshMod if there has been a change
    	if (item.getThreshMod() != 0) {
    		character.setThreshMod(character.getThreshMod() - item.getThreshMod());
    		entityManager.createNativeQuery("UPDATE characters SET thresh_mod=" + character.getThreshMod() 
    										+ " WHERE char_id=" + character.getId()).executeUpdate();
    	}
    	//Update ResMod if there has been a change
    	if (item.getResMod() != 0) {
    		character.setResMod(character.getResMod() - item.getResMod());
    		entityManager.createNativeQuery("UPDATE characters SET res_mod=" + character.getResMod() 
    										+ " WHERE char_id=" + character.getId()).executeUpdate();
    	}
    	//Update BaseLife if there has been a change
    	if (item.getLifeMod() != 0) {
    		character.setBaseLife(character.getBaseLife() - item.getLifeMod());
    		entityManager.createNativeQuery("UPDATE characters SET base_life = " + character.getBaseLife() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update ArmourMod if there has been a change
    	if (item.getArmourMod() != 0) {
    		character.setArmourMod(character.getArmourMod() - item.getArmourMod());
    		entityManager.createNativeQuery("UPDATE characters SET armour_mod = " + character.getArmourMod() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update EShield if there has been a change
    	if (item.getEsMod() != 0) {
    		character.seteShield(character.geteShield() - item.getEsMod());
    		entityManager.createNativeQuery("UPDATE characters SET e_shield=" + character.geteShield() 
    										+ " WHERE char_id=" + character.getId()).executeUpdate();
    	}
    	//Update Maneuver skill if there has been a change
    	if (item.getManeuverMod() != 0) {
    		Map<String, Integer> skills = character.getSkills();
    		skills.put("Maneuver", skills.get("Maneuver") - item.getManeuverMod());
    		entityManager.createNativeQuery("UPDATE skills_table SET skill_level=" + skills.get("Maneuver") 
    										+ " WHERE char_id=" + character.getId() + " AND skill='Maneuver'").executeUpdate();

    	}
    	//Update Athletics skill if there has been a change
    	if (item.getAthleticsMod() != 0) {
    		Map<String, Integer> skills = character.getSkills();
    		skills.put("Athletics", skills.get("Athletics") - item.getAthleticsMod());
    		entityManager.createNativeQuery("UPDATE skills_table SET skill_level=" + skills.get("Athletics") 
    										+ " WHERE char_id=" + character.getId() + " AND skill='Athletics'").executeUpdate();
    	}
    }
    
    @Transactional
    private void removeChestItem(Character character, ChestItem item) {
    	//Update the chestitems table to set the wearer column to null
    	entityManager.createNativeQuery("UPDATE chestitems SET wearer=NULL WHERE item_id=" + item.getId()).executeUpdate();
    	//Update Dex if there has been a change
    	if (item.getDexMod() != 0) {
    		character.setDex(character.getDex() - item.getDexMod());
    		entityManager.createNativeQuery("UPDATE characters SET dex=" + character.getDex() 
    										+ " WHERE char_id=" + character.getId()).executeUpdate();
    	}
    	//Update Intl if there has been a change
    	if (item.getIntMod() != 0) {
    		character.setIntl(character.getIntl() - item.getIntMod());
    		entityManager.createNativeQuery("UPDATE characters SET intl=" + character.getIntl() 
    										+ " WHERE char_id=" + character.getId()).executeUpdate();
    	}
    	//Update Str if there has been a change
    	if (item.getStrMod() != 0) {
    		character.setStr(character.getStr() - item.getStrMod());
    		entityManager.createNativeQuery("UPDATE characters SET str=" + character.getStr() 
    										+ " WHERE char_id=" + character.getId()).executeUpdate();
    	}
    	//Update ThreshMod if there has been a change
    	if (item.getThreshMod() != 0) {
    		character.setThreshMod(character.getThreshMod() - item.getThreshMod());
    		entityManager.createNativeQuery("UPDATE characters SET thresh_mod=" + character.getThreshMod() 
    										+ " WHERE char_id=" + character.getId()).executeUpdate();
    	}
    	//Update ResMod if there has been a change
    	if (item.getResMod() != 0) {
    		character.setResMod(character.getResMod() - item.getResMod());
    		entityManager.createNativeQuery("UPDATE characters SET res_mod=" + character.getResMod() 
    										+ " WHERE char_id=" + character.getId()).executeUpdate();
    	}
    	//Update BaseLife if there has been a change
    	if (item.getLifeMod() != 0) {
    		character.setBaseLife(character.getBaseLife() - item.getLifeMod());
    		entityManager.createNativeQuery("UPDATE characters SET base_life = " + character.getBaseLife() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update ArmourMod if there has been a change
    	if (item.getArmourMod() != 0) {
    		character.setArmourMod(character.getArmourMod() - item.getArmourMod());
    		entityManager.createNativeQuery("UPDATE characters SET armour_mod = " + character.getArmourMod() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update EShield if there has been a change
    	if (item.getEsMod() != 0) {
    		character.seteShield(character.geteShield() - item.getEsMod());
    		entityManager.createNativeQuery("UPDATE characters SET e_shield=" + character.geteShield() 
    										+ " WHERE char_id=" + character.getId()).executeUpdate();
    	}
    	//Update Maneuver skill if there has been a change
    	if (item.getManeuverMod() != 0) {
    		Map<String, Integer> skills = character.getSkills();
    		skills.put("Maneuver", skills.get("Maneuver") - item.getManeuverMod());
    		entityManager.createNativeQuery("UPDATE skills_table SET skill_level=" + skills.get("Maneuver") 
    										+ " WHERE char_id=" + character.getId() + " AND skill='Maneuver'").executeUpdate();

    	}
    	//Update Athletics skill if there has been a change
    	if (item.getAthleticsMod() != 0) {
    		Map<String, Integer> skills = character.getSkills();
    		skills.put("Athletics", skills.get("Athletics") - item.getAthleticsMod());
    		entityManager.createNativeQuery("UPDATE skills_table SET skill_level=" + skills.get("Athletics") 
    										+ " WHERE char_id=" + character.getId() + " AND skill='Athletics'").executeUpdate();
    	}
    }

    @Transactional
    private void removeBootsItem(Character character, BootsItem item) {
    	//Update the bootsitems table to set the wearer column to null
    	entityManager.createNativeQuery("UPDATE bootsitems SET wearer=NULL WHERE item_id=" + item.getId()).executeUpdate();
    	//Update Dex if there has been a change
    	if (item.getDexMod() != 0) {
    		character.setDex(character.getDex() - item.getDexMod());
    		entityManager.createNativeQuery("UPDATE characters SET dex=" + character.getDex() 
    										+ " WHERE char_id=" + character.getId()).executeUpdate();
    	}
    	//Update Intl if there has been a change
    	if (item.getIntMod() != 0) {
    		character.setIntl(character.getIntl() - item.getIntMod());
    		entityManager.createNativeQuery("UPDATE characters SET intl=" + character.getIntl() 
    										+ " WHERE char_id=" + character.getId()).executeUpdate();
    	}
    	//Update Str if there has been a change
    	if (item.getStrMod() != 0) {
    		character.setStr(character.getStr() - item.getStrMod());
    		entityManager.createNativeQuery("UPDATE characters SET str=" + character.getStr() 
    										+ " WHERE char_id=" + character.getId()).executeUpdate();
    	}
    	//Update ThreshMod if there has been a change
    	if (item.getThreshMod() != 0) {
    		character.setThreshMod(character.getThreshMod() - item.getThreshMod());
    		entityManager.createNativeQuery("UPDATE characters SET thresh_mod=" + character.getThreshMod() 
    										+ " WHERE char_id=" + character.getId()).executeUpdate();
    	}
    	//Update ResMod if there has been a change
    	if (item.getResMod() != 0) {
    		character.setResMod(character.getResMod() - item.getResMod());
    		entityManager.createNativeQuery("UPDATE characters SET res_mod=" + character.getResMod() 
    										+ " WHERE char_id=" + character.getId()).executeUpdate();
    	}
    	//Update BaseLife if there has been a change
    	if (item.getLifeMod() != 0) {
    		character.setBaseLife(character.getBaseLife() - item.getLifeMod());
    		entityManager.createNativeQuery("UPDATE characters SET base_life = " + character.getBaseLife() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update ArmourMod if there has been a change
    	if (item.getArmourMod() != 0) {
    		character.setArmourMod(character.getArmourMod() - item.getArmourMod());
    		entityManager.createNativeQuery("UPDATE characters SET armour_mod = " + character.getArmourMod() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update EShield if there has been a change
    	if (item.getEsMod() != 0) {
    		character.seteShield(character.geteShield() - item.getEsMod());
    		entityManager.createNativeQuery("UPDATE characters SET e_shield=" + character.geteShield() 
    										+ " WHERE char_id=" + character.getId()).executeUpdate();
    	}
    	//Update Maneuver skill if there has been a change
    	if (item.getManeuverMod() != 0) {
    		Map<String, Integer> skills = character.getSkills();
    		skills.put("Maneuver", skills.get("Maneuver") - item.getManeuverMod());
    		entityManager.createNativeQuery("UPDATE skills_table SET skill_level=" + skills.get("Maneuver") 
    										+ " WHERE char_id=" + character.getId() + " AND skill='Maneuver'").executeUpdate();

    	}
    	//Update Athletics skill if there has been a change
    	if (item.getAthleticsMod() != 0) {
    		Map<String, Integer> skills = character.getSkills();
    		skills.put("Athletics", skills.get("Athletics") - item.getAthleticsMod());
    		entityManager.createNativeQuery("UPDATE skills_table SET skill_level=" + skills.get("Athletics") 
    										+ " WHERE char_id=" + character.getId() + " AND skill='Athletics'").executeUpdate();
    	}
    	//Update Speed if there has been a change
    	if (item.getSpeedMod() != 0) {
    		character.setSpeed(character.getSpeed() - item.getSpeedMod());
    		entityManager.createNativeQuery("UPDATE characters SET e_shield = " + character.getSpeed() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    }

    @Transactional
    private void removeAmuletItem(Character character, AmuletItem item) {
    	//Update the aumletitems table to set the wearer column to null
    	entityManager.createNativeQuery("UPDATE amuletitems SET wearer=NULL WHERE item_id=" + item.getId()).executeUpdate();
    	//Update Dex if there has been a change
    	if (item.getDexMod() != 0) {
    		character.setDex(character.getDex() - item.getDexMod());
    		entityManager.createNativeQuery("UPDATE characters SET dex = " + character.getDex() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update Intl if there has been a change
    	if (item.getIntMod() != 0) {
    		character.setIntl(character.getIntl() - item.getIntMod());
    		entityManager.createNativeQuery("UPDATE characters SET intl = " + character.getIntl() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update Str if there has been a change
    	if (item.getStrMod() != 0) {
    		character.setStr(character.getStr() - item.getStrMod());
    		entityManager.createNativeQuery("UPDATE characters SET str = " + character.getStr() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update StunnedEnemySpeed if there has been a change
    	if (item.getEnemyStunSpeedMod() != 0) {
    		character.setStunnedEnemySpeed(character.getStunnedEnemySpeed() - item.getEnemyStunSpeedMod());
    		entityManager.createNativeQuery("UPDATE characters SET stunned_enemy_speed = " + character.getStunnedEnemySpeed()
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update WamAgainstShock if there has been a change
    	if (item.getWamAgainstShock() != 0) {
    		character.setWamAgainstShock(character.getWamAgainstShock() - item.getWamAgainstShock());
    		entityManager.createNativeQuery("UPDATE characters SET wam_against_shock = " + character.getWamAgainstShock() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update LifeLeech if there has been a change
    	if (item.getLifeLeech() != 0) {
    		character.setLifeLeech(character.getLifeLeech() - item.getLifeLeech());
    		entityManager.createNativeQuery("UPDATE characters SET life_leech = " + character.getLifeLeech() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update ManaLeech if there has been a change
    	if (item.getManaLeech() != 0) {
    		character.setManaLeech(character.getManaLeech() - item.getManaLeech());
    		entityManager.createNativeQuery("UPDATE characters SET mana_leech = " + character.getManaLeech() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update ArmourMod if there has been a change
    	if (item.getArmourMod() != 0) {
    		character.setArmourMod(character.getArmourMod() - item.getArmourMod());
    		entityManager.createNativeQuery("UPDATE characters SET armour_mod = " + character.getArmourMod() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update Life if there has been a change
    	if (item.getLifeMod() != 0) {
    		character.setBaseLife(character.getBaseLife() - item.getLifeMod());
    		entityManager.createNativeQuery("UPDATE characters SET base_life=" + character.getBaseLife() 
    										+ " WHERE char_id=" + character.getId()).executeUpdate();

    	}
    	//Update CritStrikeDamage skill if there has been a change
    	if (item.getCritStrikeDam() != 0) {
    		character.setCritStrikeDamMod(character.getCritStrikeDamMod() - item.getCritStrikeDam());
    		entityManager.createNativeQuery("UPDATE characters SET base_life=" + character.getCritStrikeDamMod() 
    										+ " WHERE char_id=" + character.getId()).executeUpdate();
    	}
    	//Update ResMod if there has been a change
    	if (item.getResMod() != 0) {
    		character.setResMod(character.getResMod() - item.getResMod());
    		entityManager.createNativeQuery("UPDATE characters SET res_mod=" + character.getResMod() 
    										+ " WHERE char_id=" + character.getId()).executeUpdate();
    	}
    	//Update Mana if there has been a change
    	if (item.getManaMod() != 0) {
    		character.setBaseMana(character.getBaseMana() - item.getManaMod());
    		entityManager.createNativeQuery("UPDATE characters SET res_mod=" + character.getBaseMana() 
    										+ " WHERE char_id=" + character.getId()).executeUpdate();
    	}

    }

    @Transactional
    private void removeRingItem(Character character, RingItem item) {
    	//Update the aumletitems table to set the wearer column to null
    	entityManager.createNativeQuery("UPDATE ringitems SET wearer=NULL WHERE item_id=" + item.getId()).executeUpdate();
    	//Update Dex if there has been a change
    	if (item.getDexMod() != 0) {
    		character.setDex(character.getDex() - item.getDexMod());
    		entityManager.createNativeQuery("UPDATE characters SET dex = " + character.getDex() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update Intl if there has been a change
    	if (item.getIntMod() != 0) {
    		character.setIntl(character.getIntl() - item.getIntMod());
    		entityManager.createNativeQuery("UPDATE characters SET intl = " + character.getIntl() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update Str if there has been a change
    	if (item.getStrMod() != 0) {
    		character.setStr(character.getStr() - item.getStrMod());
    		entityManager.createNativeQuery("UPDATE characters SET str = " + character.getStr() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update FlatDam if there has been a change
    	if (item.getFlatDam() != 0) {
    		character.setFlatDam(character.getFlatDam() - item.getFlatDam());
    		entityManager.createNativeQuery("UPDATE characters SET flat_dam = " + character.getFlatDam() 
											+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update LifeLeech if there has been a change
    	if (item.getLifeLeech() != 0) {
    		character.setLifeLeech(character.getLifeLeech() - item.getLifeLeech());
    		entityManager.createNativeQuery("UPDATE characters SET life_leech = " + character.getLifeLeech() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update ArmourMod if there has been a change
    	if (item.getArmourMod() != 0) {
    		character.setArmourMod(character.getArmourMod() - item.getArmourMod());
    		entityManager.createNativeQuery("UPDATE characters SET armour_mod = " + character.getArmourMod() 
    										+ " WHERE char_id = " + character.getId()).executeUpdate();
    	}
    	//Update Life if there has been a change
    	if (item.getLifeMod() != 0) {
    		character.setBaseLife(character.getBaseLife() - item.getLifeMod());
    		entityManager.createNativeQuery("UPDATE characters SET base_life=" + character.getBaseLife() 
    										+ " WHERE char_id=" + character.getId()).executeUpdate();

    	}
    	//Update ResMod if there has been a change
    	if (item.getResMod() != 0) {
    		character.setResMod(character.getResMod() - item.getResMod());
    		entityManager.createNativeQuery("UPDATE characters SET res_mod=" + character.getResMod() 
    										+ " WHERE char_id=" + character.getId()).executeUpdate();
    	}
    	//Update Mana if there has been a change
    	if (item.getManaMod() != 0) {
    		character.setBaseMana(character.getBaseMana() - item.getManaMod());
    		entityManager.createNativeQuery("UPDATE characters SET res_mod=" + character.getBaseMana() 
    										+ " WHERE char_id=" + character.getId()).executeUpdate();
    	}

    }
}
