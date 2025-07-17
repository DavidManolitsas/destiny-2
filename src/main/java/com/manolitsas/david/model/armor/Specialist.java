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
public class Specialist extends BaseArmor {

  public Specialist() {
    super(Archetype.SPECIALIST, Stat.propertyClass, Stat.WEAPONS);
  }

  @Override
  public ArmorStats getStats(ArmorStats stats, Armor armor, ArmorSlot slot) {

    switch (armor.getTier()) {
      case 1 -> {
        stats.setPropertyClass(stats.getPropertyClass() + T1_PRIMARY_STAT);
        stats.setWeapons(stats.getWeapons() + T1_SECONDARY_STAT);
      }
      case 2 -> {
        stats.setPropertyClass(stats.getPropertyClass() + T2_PRIMARY_STAT);
        stats.setWeapons(stats.getWeapons() + T2_SECONDARY_STAT);
      }
      case 3 -> {
        stats.setPropertyClass(stats.getPropertyClass() + T3_PRIMARY_STAT);
        stats.setWeapons(stats.getWeapons() + T3_SECONDARY_STAT);
      }
      case 4 -> {
        stats.setPropertyClass(stats.getPropertyClass() + T4_PRIMARY_STAT);
        stats.setWeapons(stats.getWeapons() + T4_SECONDARY_STAT);
      }
      case 5 -> {
        stats.setPropertyClass(stats.getPropertyClass() + T5_PRIMARY_STAT);
        stats.setWeapons(stats.getWeapons() + T5_SECONDARY_STAT);
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
      String message =
          String.format(
              "%s armor can not have tertiary stat in %s/%s",
              getArchetype(), getPrimaryStat(), getSecondaryStat());
      log.warn(message);
      throw new ArmorBadRequestException(slot, message);
    }

    switch (tertiary) {
      case HEALTH -> {
        stats = addTertiaryHealth(stats, armor);
        if (masterwork) {
          stats.setGrenade(stats.getGrenade() + MASTERWORK);
          stats.setSuper(stats.getSuper() + MASTERWORK);
          stats.setMelee(stats.getMelee() + MASTERWORK);
        }
      }
      case GRENADE -> {
        stats = addTertiaryGrenade(stats, armor);
        if (masterwork) {
          stats.setHealth(stats.getHealth() + MASTERWORK);
          stats.setSuper(stats.getSuper() + MASTERWORK);
          stats.setMelee(stats.getMelee() + MASTERWORK);
        }
      }
      case SUPER -> {
        stats = addTertiarySuper(stats, armor);
        if (masterwork) {
          stats.setHealth(stats.getHealth() + MASTERWORK);
          stats.setGrenade(stats.getGrenade() + MASTERWORK);
          stats.setMelee(stats.getMelee() + MASTERWORK);
        }
      }
      case MELEE -> {
        stats = addTertiaryMelee(stats, armor);
        if (masterwork) {
          stats.setHealth(stats.getHealth() + MASTERWORK);
          stats.setGrenade(stats.getGrenade() + MASTERWORK);
          stats.setSuper(stats.getSuper() + MASTERWORK);
        }
      }
    }

    return stats;
  }
}
