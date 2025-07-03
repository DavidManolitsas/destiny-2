package com.manolitsas.david.mapper;

import com.manolitsas.david.dto.ContentProperty;
import com.manolitsas.david.dto.DestinyDefinitions;
import com.manolitsas.david.model.entity.Property;
import com.manolitsas.david.model.platform.WorldComponentContent;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BungieMapper {

  DestinyDefinitions toDestinyDefinitions(WorldComponentContent worldComponentContent);

  List<ContentProperty> toContentProperties(List<Property> propertyList);
}
