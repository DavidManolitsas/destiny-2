package com.manolitsas.david.service;

import com.manolitsas.david.client.BungieCommonClient;
import com.manolitsas.david.mapper.BungieMapper;
import com.manolitsas.david.model.common.DefinitionDetails;
import com.manolitsas.david.model.common.DisplayProperties;
import com.manolitsas.david.model.entity.Item;
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
  private final BungieMapper mapper;
  private final MongoTemplate mongoTemplate;

  @Async("taskExecutor")
  public void saveDefinition(String definitionUri) {
    int insertCount = 0;
    int updateCount = 0;
    String collectionName = null;
    HashMap<String, DefinitionDetails> manifestDefinitionItems =
        commonClient.getCommonContent(definitionUri);
    Matcher matcher = DEFINITION_URI_PATTERN.matcher(definitionUri);

    if (matcher.find()) {
      collectionName = matcher.group(1);
    }

    if (collectionName == null) {
      log.info("Unable to extract database name from {}", definitionUri);
      return;
    }

    for (String propertyHash : manifestDefinitionItems.keySet()) {
      var definitionItem = manifestDefinitionItems.get(propertyHash);
      DisplayProperties displayProperties = definitionItem.getDisplayProperties();

      if (displayProperties == null) {
        continue;
      }

      if ((displayProperties.getName() == null || displayProperties.getName().trim().isEmpty())
          && (displayProperties.getDescription() == null
              || displayProperties.getDescription().trim().isEmpty())) {
        // properties description and name are null/empty
        continue;
      }

      Item item = mapper.toItem(propertyHash, definitionItem);

      // check if property already exists
      Query query = new Query(Criteria.where("_id").is(propertyHash));
      Item existingItem = mongoTemplate.findOne(query, Item.class, collectionName);

      if (!mongoTemplate.exists(query, Item.class, collectionName)) {
        // Document does not exist, insert it
        mongoTemplate.save(item, collectionName);
        log.info("Added new item {} {} to {} collection", propertyHash, item.getName(), collectionName);
        insertCount++;
      } else if (!existingItem.equals(item)) {
        // Document exists and is different, update it
        mongoTemplate.save(item, collectionName);
        log.info("Updated existing item {} {} in {} collection", propertyHash, item.getName(), collectionName);
        updateCount++;
      }
    }

    if (updateCount > 0 || insertCount > 0) {
      log.info("Added {} and Updated {} items in collection {}", insertCount, updateCount, collectionName);
    }
  }
}
