package com.manolitsas.david.mapper;

import com.manolitsas.david.dto.DestinyDefinitions;
import com.manolitsas.david.model.platform.WorldComponentContent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BungieMapper {

  DestinyDefinitions toDestinyDefinitions(WorldComponentContent worldComponentContent);
}
