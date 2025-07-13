package com.manolitsas.david.model.armor;

import static com.manolitsas.david.config.Constant.*;

import com.manolitsas.david.dto.Archetype;
import com.manolitsas.david.dto.Armor;
import com.manolitsas.david.dto.ArmorSlot;
import com.manolitsas.david.dto.ArmorStats;
import com.manolitsas.david.dto.Stat;
import com.manolitsas.david.exception.ArmorBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@AllArgsConstructor
public abstract class BaseArmor {

  private Archetype archetype;
  private Stat primaryStat;
  private Stat secondaryStat;

  public abstract ArmorStats getStats(ArmorStats stats, Armor armor, ArmorSlot slot);

  public abstract ArmorStats addTertiaryStatAndMasterwork(ArmorStats stats, Armor armor, ArmorSlot slot);

  public ArmorStats addArmorModStat(ArmorStats stats, Stat mod) {
    switch (mod) {
      case WEAPONS -> stats.setWeapons(stats.getWeapons() + MAJOR_MOD);
      case HEALTH -> stats.setHealth(stats.getHealth() + MAJOR_MOD);
      case propertyClass -> stats.setPropertyClass(stats.getPropertyClass() + MAJOR_MOD);
      case GRENADE -> stats.setGrenade(stats.getGrenade() + MAJOR_MOD);
      case SUPER -> stats.setSuper(stats.getSuper() + MAJOR_MOD);
      case MELEE -> stats.setMelee(stats.getMelee() + MAJOR_MOD);
    }

    return stats;
  }

  public ArmorStats addTertiaryWeapons(ArmorStats stats, Armor armor) {
    switch (armor.getTier()) {
      case 1 -> stats.setWeapons(stats.getWeapons() + T1_TERTIARY_STAT);
      case 2 -> stats.setWeapons(stats.getWeapons() + T2_TERTIARY_STAT);
      case 3 -> stats.setWeapons(stats.getWeapons() + T3_TERTIARY_STAT);
      case 4 -> stats.setWeapons(stats.getWeapons() + T4_TERTIARY_STAT);
      case 5 -> stats.setWeapons(stats.getWeapons() + T5_TERTIARY_STAT);
    }

    return stats;
  }

  public ArmorStats addTertiaryHealth(ArmorStats stats, Armor armor) {
    switch (armor.getTier()) {
      case 1 -> stats.setHealth(stats.getHealth() + T1_TERTIARY_STAT);
      case 2 -> stats.setHealth(stats.getHealth() + T2_TERTIARY_STAT);
      case 3 -> stats.setHealth(stats.getHealth() + T3_TERTIARY_STAT);
      case 4 -> stats.setHealth(stats.getHealth() + T4_TERTIARY_STAT);
      case 5 -> stats.setHealth(stats.getHealth() + T5_TERTIARY_STAT);
    }

    return stats;
  }

  public ArmorStats addTertiaryClass(ArmorStats stats, Armor armor) {
    switch (armor.getTier()) {
      case 1 -> stats.setPropertyClass(stats.getPropertyClass() + T1_TERTIARY_STAT);
      case 2 -> stats.setPropertyClass(stats.getPropertyClass() + T2_TERTIARY_STAT);
      case 3 -> stats.setPropertyClass(stats.getPropertyClass() + T3_TERTIARY_STAT);
      case 4 -> stats.setPropertyClass(stats.getPropertyClass() + T4_TERTIARY_STAT);
      case 5 -> stats.setPropertyClass(stats.getPropertyClass() + T5_TERTIARY_STAT);
    }

    return stats;
  }

  public ArmorStats addTertiaryGrenade(ArmorStats stats, Armor armor) {
    switch (armor.getTier()) {
      case 1 -> stats.setGrenade(stats.getGrenade() + T1_TERTIARY_STAT);
      case 2 -> stats.setGrenade(stats.getGrenade() + T2_TERTIARY_STAT);
      case 3 -> stats.setGrenade(stats.getGrenade() + T3_TERTIARY_STAT);
      case 4 -> stats.setGrenade(stats.getGrenade() + T4_TERTIARY_STAT);
      case 5 -> stats.setGrenade(stats.getGrenade() + T5_TERTIARY_STAT);
    }

    return stats;
  }

  public ArmorStats addTertiarySuper(ArmorStats stats, Armor armor) {
    switch (armor.getTier()) {
      case 1 -> stats.setSuper(stats.getSuper() + T1_TERTIARY_STAT);
      case 2 -> stats.setSuper(stats.getSuper() + T2_TERTIARY_STAT);
      case 3 -> stats.setSuper(stats.getSuper() + T3_TERTIARY_STAT);
      case 4 -> stats.setSuper(stats.getSuper() + T4_TERTIARY_STAT);
      case 5 -> stats.setSuper(stats.getSuper() + T5_TERTIARY_STAT);
    }

    return stats;
  }

  public ArmorStats addTertiaryMelee(ArmorStats stats, Armor armor) {
    switch (armor.getTier()) {
      case 1 -> stats.setMelee(stats.getMelee() + T1_TERTIARY_STAT);
      case 2 -> stats.setMelee(stats.getMelee() + T2_TERTIARY_STAT);
      case 3 -> stats.setMelee(stats.getMelee() + T3_TERTIARY_STAT);
      case 4 -> stats.setMelee(stats.getMelee() + T4_TERTIARY_STAT);
      case 5 -> stats.setMelee(stats.getMelee() + T5_TERTIARY_STAT);
    }

    return stats;
  }

  public ArmorStats addAttunement(ArmorStats stats, Armor armor, ArmorSlot slot) {

    if (armor.getTuneAdd() == null || armor.getTuneSubtract() == null) {
      String message = "Both Add and Subtract attunement must be provided";
      log.warn(message);
      throw new ArmorBadRequestException(slot, message);
    }

    if (armor.getTuneAdd().equals(armor.getTuneSubtract())) {
      String message = "Armor Tuning can not Add and Subtract from the same stat";
      log.warn(message);
      throw new ArmorBadRequestException(slot, message);
    }

    // add tune
    switch (armor.getTuneAdd()) {
      case WEAPONS -> stats.setWeapons(stats.getWeapons() + ARMOR_TUNE);
      case HEALTH -> stats.setHealth(stats.getHealth() + ARMOR_TUNE);
      case propertyClass -> stats.setPropertyClass(stats.getPropertyClass() + ARMOR_TUNE);
      case GRENADE -> stats.setGrenade(stats.getGrenade() + ARMOR_TUNE);
      case SUPER -> stats.setSuper(stats.getSuper() + ARMOR_TUNE);
      case MELEE -> stats.setMelee(stats.getMelee() + ARMOR_TUNE);
    }

    // subtract tune
    switch (armor.getTuneSubtract()) {
      case WEAPONS -> stats.setWeapons(stats.getWeapons() - ARMOR_TUNE);
      case HEALTH -> stats.setHealth(stats.getHealth() - ARMOR_TUNE);
      case propertyClass -> stats.setPropertyClass(stats.getPropertyClass() - ARMOR_TUNE);
      case GRENADE -> stats.setGrenade(stats.getGrenade() - ARMOR_TUNE);
      case SUPER -> stats.setSuper(stats.getSuper() - ARMOR_TUNE);
      case MELEE -> stats.setMelee(stats.getMelee() - ARMOR_TUNE);
    }

    return stats;
  }
}
