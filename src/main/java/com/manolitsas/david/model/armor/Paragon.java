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
public class Paragon extends BaseArmor {

  public Paragon() {
    super(Archetype.PARAGON, Stat.SUPER, Stat.MELEE);
  }

  @Override
  public ArmorStats getStats(ArmorStats stats, Armor armor, ArmorSlot slot) {

    switch (armor.getTier()) {
      case 1 -> {
        stats.setSuper(stats.getSuper() + T1_PRIMARY_STAT);
        stats.setMelee(stats.getMelee() + T1_SECONDARY_STAT);
      }
      case 2 -> {
        stats.setSuper(stats.getSuper() + T2_PRIMARY_STAT);
        stats.setMelee(stats.getMelee() + T2_SECONDARY_STAT);
      }
      case 3 -> {
        stats.setSuper(stats.getSuper() + T3_PRIMARY_STAT);
        stats.setMelee(stats.getMelee() + T3_SECONDARY_STAT);
      }
      case 4 -> {
        stats.setSuper(stats.getSuper() + T4_PRIMARY_STAT);
        stats.setMelee(stats.getMelee() + T4_SECONDARY_STAT);
      }
      case 5 -> {
        stats.setSuper(stats.getSuper() + T5_PRIMARY_STAT);
        stats.setMelee(stats.getMelee() + T5_SECONDARY_STAT);
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
          stats.setGrenade(stats.getGrenade() + MASTERWORK);
        }
      }
      case HEALTH -> {
        stats = addTertiaryHealth(stats, armor);
        if (masterwork) {
          stats.setWeapons(stats.getWeapons() + MASTERWORK);
          stats.setPropertyClass(stats.getPropertyClass() + MASTERWORK);
          stats.setGrenade(stats.getGrenade() + MASTERWORK);
        }
      }
      case propertyClass -> {
        stats = addTertiaryClass(stats, armor);
        if (masterwork) {
          stats.setWeapons(stats.getWeapons() + MASTERWORK);
          stats.setHealth(stats.getHealth() + MASTERWORK);
          stats.setGrenade(stats.getGrenade() + MASTERWORK);
        }
      }
      case GRENADE -> {
        stats = addTertiaryGrenade(stats, armor);
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
