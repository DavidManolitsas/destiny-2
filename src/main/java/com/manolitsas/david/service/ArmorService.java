package com.manolitsas.david.service;

import static com.manolitsas.david.config.Constant.*;

import com.manolitsas.david.dto.*;
import com.manolitsas.david.model.armor.Brawler;
import com.manolitsas.david.model.armor.Bulwark;
import com.manolitsas.david.model.armor.Grenadier;
import com.manolitsas.david.model.armor.Gunner;
import com.manolitsas.david.model.armor.Paragon;
import com.manolitsas.david.model.armor.Specialist;
import org.springframework.stereotype.Service;

@Service
public class ArmorService {

  private final Gunner gunner = new Gunner();
  private final Bulwark bulwark = new Bulwark();
  private final Specialist specialist = new Specialist();
  private final Grenadier grenadier = new Grenadier();
  private final Paragon paragon = new Paragon();
  private final Brawler brawler = new Brawler();

  public ArmorResponse calculateArmorStats(ArmorRequest request) {
    ArmorResponse response = new ArmorResponse();
    ArmorRequestArmor armor = request.getArmor();

    if (request.getExotic() != null && request.getExotic().getSlot().equals(ArmorSlot.HELMET)) {
      response.setHelmet(getExoticArmorStats(request.getExotic()));
    } else {
      if ((armor.getHelmet() != null)) {
        ArmorStats stats = setBaseArmorStats(request);
        response.setHelmet(getArmorStats(stats, armor.getHelmet(), ArmorSlot.HELMET));
      }
    }

    if (request.getExotic() != null && request.getExotic().getSlot().equals(ArmorSlot.GAUNTLETS)) {
      response.setGauntlets(getExoticArmorStats(request.getExotic()));
    } else {
      if (armor.getGauntlets() != null) {
        ArmorStats stats = setBaseArmorStats(request);
        response.setGauntlets(getArmorStats(stats, armor.getGauntlets(), ArmorSlot.GAUNTLETS));
      }
    }

    if (request.getExotic() != null && request.getExotic().getSlot().equals(ArmorSlot.CHEST)) {
      response.setChest(getExoticArmorStats(request.getExotic()));
    } else {
      if (armor.getChest() != null) {
        ArmorStats stats = setBaseArmorStats(request);
        response.setChest(getArmorStats(stats, armor.getChest(), ArmorSlot.CHEST));
      }
    }

    if (request.getExotic() != null && request.getExotic().getSlot().equals(ArmorSlot.LEGS)) {
      response.setLegs(getExoticArmorStats(request.getExotic()));
    } else {
      if (armor.getLegs() != null) {
        ArmorStats stats = setBaseArmorStats(request);
        response.setLegs(getArmorStats(stats, armor.getLegs(), ArmorSlot.LEGS));
      }
    }

    if (request.getExotic() != null && request.getExotic().getSlot().equals(ArmorSlot.CLASS_ITEM)) {
      response.setPropertyClass(getExoticArmorStats(request.getExotic()));
    } else {
      if (armor.getPropertyClass() != null) {
        ArmorStats stats = setBaseArmorStats(request);
        response.setPropertyClass(getArmorStats(stats, armor.getPropertyClass(), ArmorSlot.CLASS_ITEM));
      }
    }

    response.setTotal(addAllArmorPieces(response));

    return response;
  }

  private ArmorStats getExoticArmorStats(ArmorRequestExotic requestExotic) {
    ArmorStats exotic = new ArmorStats();
    exotic.setWeapons(requestExotic.getStats().getWeapons());
    exotic.setHealth(requestExotic.getStats().getHealth());
    exotic.setPropertyClass(requestExotic.getStats().getPropertyClass());
    exotic.setGrenade(requestExotic.getStats().getGrenade());
    exotic.setSuper(requestExotic.getStats().getSuper());
    exotic.setMelee(requestExotic.getStats().getMelee());

    return exotic;
  }

  private ArmorStats setBaseArmorStats(ArmorRequest request) {
    ArmorStats stats = new ArmorStats();
    stats.setWeapons(0);
    stats.setHealth(0);
    stats.setPropertyClass(0);
    stats.setGrenade(0);
    stats.setSuper(0);
    stats.setMelee(0);

    return stats;
  }

  public ArmorStats getArmorStats(ArmorStats stats, Armor armor, ArmorSlot slot) {
    Archetype archetype = armor.getArchetype();

    return switch (archetype) {
      case GUNNER -> gunner.getStats(stats, armor, slot);
      case BULWARK -> bulwark.getStats(stats, armor, slot);
      case SPECIALIST -> specialist.getStats(stats, armor, slot);
      case GRENADIER -> grenadier.getStats(stats, armor, slot);
      case PARAGON -> paragon.getStats(stats, armor, slot);
      case BRAWLER -> brawler.getStats(stats, armor, slot);
    };
  }

  private ArmorStats addAllArmorPieces(ArmorResponse allArmor) {
    Integer weapons = 0;
    Integer health = 0;
    Integer classStat = 0;
    Integer grenade = 0;
    Integer superStat = 0;
    Integer melee = 0;

    // add all armor pieces together
    //helmet
    if (allArmor.getHelmet() != null) {
      weapons += allArmor.getHelmet().getWeapons();
      health += allArmor.getHelmet().getHealth();
      classStat += allArmor.getHelmet().getPropertyClass();
      grenade += allArmor.getHelmet().getGrenade();
      superStat += allArmor.getHelmet().getSuper();
      melee += allArmor.getHelmet().getMelee();
    }

    // gauntlets
    if (allArmor.getGauntlets() != null) {
      weapons += allArmor.getGauntlets().getWeapons();
      health += allArmor.getGauntlets().getHealth();
      classStat += allArmor.getGauntlets().getPropertyClass();
      grenade += allArmor.getGauntlets().getGrenade();
      superStat += allArmor.getGauntlets().getSuper();
      melee += allArmor.getGauntlets().getMelee();
    }

    // chest
    if (allArmor.getChest() != null) {
      weapons += allArmor.getChest().getWeapons();
      health += allArmor.getChest().getHealth();
      classStat += allArmor.getChest().getPropertyClass();
      grenade += allArmor.getChest().getGrenade();
      superStat += allArmor.getChest().getSuper();
      melee += allArmor.getChest().getMelee();
    }

    // legs
    if (allArmor.getLegs() != null) {
      weapons += allArmor.getLegs().getWeapons();
      health += allArmor.getLegs().getHealth();
      classStat += allArmor.getLegs().getPropertyClass();
      grenade += allArmor.getLegs().getGrenade();
      superStat += allArmor.getLegs().getSuper();
      melee += allArmor.getLegs().getMelee();
    }

    // class item
    if (allArmor.getPropertyClass() != null) {
      weapons += allArmor.getPropertyClass().getWeapons();
      health += allArmor.getPropertyClass().getHealth();
      classStat += allArmor.getPropertyClass().getPropertyClass();
      grenade += allArmor.getPropertyClass().getGrenade();
      superStat += allArmor.getPropertyClass().getSuper();
      melee += allArmor.getPropertyClass().getMelee();
    }

    ArmorStats stats = new ArmorStats();
    stats.setWeapons(weapons);
    stats.setHealth(health);
    stats.setPropertyClass(classStat);
    stats.setGrenade(grenade);
    stats.setSuper(superStat);
    stats.setMelee(melee);
    return stats;
  }
}
