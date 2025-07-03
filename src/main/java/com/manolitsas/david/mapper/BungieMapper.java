package com.manolitsas.david.mapper;

import com.manolitsas.david.dto.ContentProperty;
import com.manolitsas.david.dto.DestinyDefinitions;
import com.manolitsas.david.model.entity.Property;
import com.manolitsas.david.model.platform.WorldComponentContent;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BungieMapper {

  DestinyDefinitions toDestinyDefinitions(WorldComponentContent worldComponentContent);

  List<ContentProperty> toContentProperties(List<Property> propertyList);
}
