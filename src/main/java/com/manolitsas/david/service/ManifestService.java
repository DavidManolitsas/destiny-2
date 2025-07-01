package com.manolitsas.david.service;

import com.manolitsas.david.client.BungieCommonClient;
import com.manolitsas.david.client.BungieRestClient;
import com.manolitsas.david.dto.DestinyDefinitions;
import com.manolitsas.david.dto.Manifest;
import com.manolitsas.david.mapper.BungieMapper;
import com.manolitsas.david.model.common.ContentDetails;
import com.manolitsas.david.model.common.DisplayProperties;
import com.manolitsas.david.model.entity.Property;
import com.manolitsas.david.model.platform.BungieManifest;
import com.manolitsas.david.model.platform.WorldComponentContent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class ManifestService {

  private static final Pattern DEFINITION_URI_PATTERN = Pattern.compile("/([A-Za-z]+)-[a-f0-9\\-]+\\.json$");

  private final BungieRestClient platformClient;
  private final BungieCommonClient commonClient;
  private final BungieMapper mapper;
  private final MongoTemplate mongoTemplate;

  public Manifest getBungieManifest() {
    Manifest manifest = new Manifest();
    BungieManifest bungieManifest = platformClient.getManifest();

    if (bungieManifest == null || bungieManifest.getResponse() == null) {
      return manifest;
    }

    manifest.setVersion(bungieManifest.getResponse().getVersion());
    DestinyDefinitions definitions = mapper.toDestinyDefinitions(bungieManifest.getResponse().getWorldComponentContentPaths().getContent());
    manifest.setDestinyDefinitions(definitions);

    return manifest;
  }


  public Manifest updateManifest() {
    Manifest manifest = new Manifest();
    BungieManifest bungieManifest = platformClient.getManifest();

    if (bungieManifest == null || bungieManifest.getResponse() == null) {
      return manifest;
    }

    WorldComponentContent worldComponentContent = bungieManifest.getResponse().getWorldComponentContentPaths().getContent();

    for (String definitionUri : worldComponentContent.getAllDefinitions()) {
      int count = 0;
      String collectionName = null;
      HashMap<String, ContentDetails> traits = commonClient.getCommonContent(definitionUri);
      Matcher matcher = DEFINITION_URI_PATTERN.matcher(definitionUri);

      if (matcher.find()) {
        collectionName = matcher.group(1);
      }

      for (String propertyHash : traits.keySet()) {
        var trait = traits.get(propertyHash);
        DisplayProperties displayProperties = trait.getDisplayProperties();

        if (displayProperties == null || displayProperties.getDescription() == null ||displayProperties.getDescription().trim().isEmpty()) {
          continue;
        }

        Property property = new Property(propertyHash);
        property.setName(displayProperties.getName());
        property.setDescription(displayProperties.getDescription());
        if (displayProperties.getIcon() != null) {
          property.setIcon(String.format("https://www.bungie.net%s",displayProperties.getIcon()));
        }
        property.setHasIcon(displayProperties.getHasIcon());

        if (collectionName != null) {
          mongoTemplate.save(property, collectionName);
          count++;
          continue;
        }
        log.info("Unable to extract database name from {}", definitionUri);
      }

      log.info("Saved {} properties in collection {}", count, collectionName);
    }

    manifest.setVersion(bungieManifest.getResponse().getVersion());
    return manifest;
  }

}
