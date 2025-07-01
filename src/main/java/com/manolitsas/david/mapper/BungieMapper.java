package com.manolitsas.david.mapper;

import com.manolitsas.david.dto.Manifest;
import com.manolitsas.david.model.WorldComponentContent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BungieMapper {

  Manifest toManifest(WorldComponentContent worldComponentContent);
}
