package com.manolitsas.david.mapper;

import com.manolitsas.david.dto.ContentProperty;
import com.manolitsas.david.dto.DestinyDefinitions;
import com.manolitsas.david.model.common.DefinitionDetails;
import com.manolitsas.david.model.entity.Item;
import com.manolitsas.david.model.platform.WorldComponentContent;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BungieMapper {

  DestinyDefinitions toDestinyDefinitions(WorldComponentContent worldComponentContent);

  List<ContentProperty> toContentProperties(List<Item> itemList);

  default Item toItem(String hash, DefinitionDetails definitionDetails) {
    var displayProperties = definitionDetails.getDisplayProperties();

    Item item = new Item(hash);

    item.setName(displayProperties.getName());
    item.setDescription(displayProperties.getDescription());
    if (displayProperties.getIcon() != null) {
      item.setIconUrl(String.format("https://www.bungie.net%s", displayProperties.getIcon()));
    }
    if (definitionDetails.getScreenshot() != null) {
      item.setScreenshotUrl(
          String.format("https://www.bungie.net%s", definitionDetails.getScreenshot()));
    }
    item.setFlavorText(definitionDetails.getFlavorText());
    item.setItemType(definitionDetails.getItemTypeDisplayName());

    if (definitionDetails.getInventory() != null) {
      item.setItemTier(definitionDetails.getInventory().getTierTypeName());
    }

    item.setIsFeaturedItem(definitionDetails.getIsFeaturedItem());

//    if (definitionDetails.getDefaultDamageType() != null) {
//      item.setDamageType(getDamageType(definitionDetails.getDefaultDamageType()));
//    }

    return item;
  }

  private String getDamageType(int damageType) {
    return switch (damageType) {
      case 0 -> "Kinetic";
      case 1 -> "Arc";
      case 2 -> "Solar";
      case 3 -> "Void";
      case 4 -> "Stasis";
      case 5 -> "Strand";
      default -> null;
    };
  }
}
