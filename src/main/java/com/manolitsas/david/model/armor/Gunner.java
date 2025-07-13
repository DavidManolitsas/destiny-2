package com.manolitsas.david.model.armor;

import static com.manolitsas.david.config.Constant.*;

import com.manolitsas.david.dto.Archetype;
import com.manolitsas.david.dto.Armor;
import com.manolitsas.david.dto.ArmorSlot;
import com.manolitsas.david.dto.ArmorStats;
import com.manolitsas.david.dto.Stat;
import com.manolitsas.david.exception.ArmorBadRequestException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Gunner extends BaseArmor {

  public Gunner() {
    super(Archetype.GUNNER, Stat.WEAPONS, Stat.GRENADE);
  }

  @Override
  public ArmorStats getStats(ArmorStats stats, Armor armor, ArmorSlot slot) {
    switch (armor.getTier()) {
      case 1 -> {
        stats.setWeapons(stats.getWeapons() + T1_PRIMARY_STAT);
        stats.setGrenade(stats.getGrenade() + T1_SECONDARY_STAT);
      }
      case 2 -> {
        stats.setWeapons(stats.getWeapons() + T2_PRIMARY_STAT);
        stats.setGrenade(stats.getGrenade() + T2_SECONDARY_STAT);
      }
      case 3 -> {
        stats.setWeapons(stats.getWeapons() + T3_PRIMARY_STAT);
        stats.setGrenade(stats.getGrenade() + T3_SECONDARY_STAT);
      }
      case 4 -> {
        stats.setWeapons(stats.getWeapons() + T4_PRIMARY_STAT);
        stats.setGrenade(stats.getGrenade() + T4_SECONDARY_STAT);
      }
      case 5 -> {
        stats.setWeapons(stats.getWeapons() + T5_PRIMARY_STAT);
        stats.setGrenade(stats.getGrenade() + T5_SECONDARY_STAT);
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
      case HEALTH -> {
        stats = addTertiaryHealth(stats, armor);
        if (masterwork) {
          stats.setPropertyClass(stats.getPropertyClass() + MASTERWORK);
          stats.setSuper(stats.getSuper() + MASTERWORK);
          stats.setMelee(stats.getMelee() + MASTERWORK);
        }
      }
      case propertyClass -> {
        stats = addTertiaryClass(stats, armor);
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
          stats.setPropertyClass(stats.getPropertyClass() + MASTERWORK);
          stats.setMelee(stats.getMelee() + MASTERWORK);
        }
      }
      case MELEE -> {
        stats = addTertiaryMelee(stats, armor);
        if (masterwork) {
          stats.setHealth(stats.getHealth() + MASTERWORK);
          stats.setPropertyClass(stats.getPropertyClass() + MASTERWORK);
          stats.setSuper(stats.getSuper() + MASTERWORK);
        }
      }
    }

    return stats;
  }
}
