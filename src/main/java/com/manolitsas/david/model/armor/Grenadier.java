package com.manolitsas.david.model.armor;

import static com.manolitsas.david.config.Constant.*;
import static com.manolitsas.david.config.Constant.T5_SECONDARY_STAT;

import com.manolitsas.david.dto.Archetype;
import com.manolitsas.david.dto.Armor;
import com.manolitsas.david.dto.ArmorSlot;
import com.manolitsas.david.dto.ArmorStats;
import com.manolitsas.david.dto.Stat;
import com.manolitsas.david.exception.ArmorBadRequestException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Grenadier extends BaseArmor {

  public Grenadier() {
    super(Archetype.GRENADIER, Stat.GRENADE, Stat.SUPER);
  }

  @Override
  public ArmorStats getStats(ArmorStats stats, Armor armor, ArmorSlot slot) {

    switch (armor.getTier()) {
      case 1 -> {
        stats.setGrenade(stats.getGrenade() + T1_PRIMARY_STAT);
        stats.setSuper(stats.getSuper() + T1_SECONDARY_STAT);
      }
      case 2 -> {
        stats.setGrenade(stats.getGrenade() + T2_PRIMARY_STAT);
        stats.setSuper(stats.getSuper() + T2_SECONDARY_STAT);
      }
      case 3 -> {
        stats.setGrenade(stats.getGrenade() + T3_PRIMARY_STAT);
        stats.setSuper(stats.getSuper() + T3_SECONDARY_STAT);
      }
      case 4 -> {
        stats.setGrenade(stats.getGrenade() + T4_PRIMARY_STAT);
        stats.setSuper(stats.getSuper() + T4_SECONDARY_STAT);
      }
      case 5 -> {
        stats.setGrenade(stats.getGrenade() + T5_PRIMARY_STAT);
        stats.setSuper(stats.getSuper() + T5_SECONDARY_STAT);
      }
    }

    stats = addArmorModStat(stats, armor.getMod());
    stats = addTertiaryStatAndMasterwork(stats, armor, slot);
    stats = addAttunement(stats, armor, slot);

    return stats;
  }

  @Override
  public ArmorStats addTertiaryStatAndMasterwork(ArmorStats stats, Armor armor, ArmorSlot slot) {
    Stat tertiary = armor.getTertiary();
    Boolean masterwork = armor.getMasterwork();

    if (tertiary.equals(getPrimaryStat()) || tertiary.equals(getSecondaryStat())) {
      String message = String.format("%s armor can not have tertiary stat in %s/%s", getArchetype(), getPrimaryStat(), getSecondaryStat());
      log.warn(message);
      throw new ArmorBadRequestException(slot, message);
    }

    switch (tertiary) {
      case WEAPONS -> {
        stats = addTertiaryWeapons(stats, armor);
        if (masterwork) {
          stats.setHealth(stats.getHealth() + MASTERWORK);
          stats.setPropertyClass(stats.getPropertyClass() + MASTERWORK);
          stats.setMelee(stats.getMelee() + MASTERWORK);
        }
      }
      case HEALTH -> {
        stats = addTertiaryHealth(stats, armor);
        if (masterwork) {
          stats.setWeapons(stats.getWeapons() + MASTERWORK);
          stats.setPropertyClass(stats.getPropertyClass() + MASTERWORK);
          stats.setMelee(stats.getMelee() + MASTERWORK);
        }
      }
      case propertyClass -> {
        stats = addTertiaryClass(stats, armor);
        if (masterwork) {
          stats.setWeapons(stats.getWeapons() + MASTERWORK);
          stats.setHealth(stats.getHealth() + MASTERWORK);
          stats.setMelee(stats.getMelee() + MASTERWORK);
        }
      }
      case MELEE -> {
        stats = addTertiaryMelee(stats, armor);
        if (masterwork) {
          stats.setWeapons(stats.getWeapons() + MASTERWORK);
          stats.setHealth(stats.getHealth() + MASTERWORK);
          stats.setPropertyClass(stats.getPropertyClass() + MASTERWORK);
        }
      }
    }

    return stats;
  }
}
