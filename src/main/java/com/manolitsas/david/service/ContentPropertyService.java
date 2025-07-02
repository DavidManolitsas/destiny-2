package com.manolitsas.david.service;

import com.manolitsas.david.client.BungieCommonClient;
import com.manolitsas.david.model.common.ContentDetails;
import com.manolitsas.david.model.common.DisplayProperties;
import com.manolitsas.david.model.entity.Property;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContentPropertyService {

  private static final Pattern DEFINITION_URI_PATTERN = Pattern.compile("/([A-Za-z]+)-[a-f0-9\\-]+\\.json$");
  private final BungieCommonClient commonClient;
  private final MongoTemplate mongoTemplate;


  @Async("taskExecutor")
  public void saveContentProperties(String definitionUri) {
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


}
