package com.manolitsas.david.service;

import com.manolitsas.david.client.BungieCommonClient;
import com.manolitsas.david.model.common.ContentDetails;
import com.manolitsas.david.model.common.DisplayProperties;
import com.manolitsas.david.model.entity.Property;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ManifestAsyncService {

  private static final Pattern DEFINITION_URI_PATTERN =
      Pattern.compile("/([A-Za-z]+)-[a-f0-9\\-]+\\.json$");
  private final BungieCommonClient commonClient;
  private final MongoTemplate mongoTemplate;

  @Async("taskExecutor")
  public void saveDefinition(String definitionUri) {
    int count = 0;
    String collectionName = null;
    HashMap<String, ContentDetails> traits = commonClient.getCommonContent(definitionUri);
    Matcher matcher = DEFINITION_URI_PATTERN.matcher(definitionUri);

    if (matcher.find()) {
      collectionName = matcher.group(1);
    }

    if (collectionName == null) {
      log.info("Unable to extract database name from {}", definitionUri);
      return;
    }

    for (String propertyHash : traits.keySet()) {
      var trait = traits.get(propertyHash);
      DisplayProperties displayProperties = trait.getDisplayProperties();

      if (displayProperties == null
          || displayProperties.getDescription() == null
          || displayProperties.getDescription().trim().isEmpty()) {
        continue;
      }

      Property property = new Property(propertyHash);
      property.setName(displayProperties.getName());
      property.setDescription(displayProperties.getDescription());
      if (displayProperties.getIcon() != null) {
        property.setIcon(String.format("https://www.bungie.net%s", displayProperties.getIcon()));
      }
      property.setHasIcon(displayProperties.getHasIcon());

      // check if property already exists
      Query query = new Query(Criteria.where("_id").is(propertyHash));
      boolean exists = mongoTemplate.exists(query, Property.class, collectionName);

      if (!exists) {
        // log.info("Creating new Destiny 2 property {} in {} collection", propertyHash, collectionName);
        mongoTemplate.save(property, collectionName);
        count++;
      }

    }

    log.info("Inserted {} new properties in collection {}", count, collectionName);
  }
}
