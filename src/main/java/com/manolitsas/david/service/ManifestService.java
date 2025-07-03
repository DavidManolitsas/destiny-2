package com.manolitsas.david.service;

import com.manolitsas.david.client.BungieRestClient;
import com.manolitsas.david.dto.ContentPropertyResponse;
import com.manolitsas.david.dto.DestinyDefinitions;
import com.manolitsas.david.dto.Manifest;
import com.manolitsas.david.mapper.BungieMapper;
import com.manolitsas.david.model.entity.Property;
import com.manolitsas.david.model.platform.BungieManifest;
import com.manolitsas.david.model.platform.WorldComponentContent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class ManifestService {

  private final BungieRestClient platformClient;
  private final BungieMapper mapper;
  private final MongoTemplate mongoTemplate;
  private final ManifestAsyncService manifestAsyncService;


  /**
   * Get the Bungie Destiny 2 manifest.
   *
   * @return JSON data for the Destiny 2 manifest
   */
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

  /**
   * Retrieve the latest Bungie Destiny 2 manifest and update the definitions in the Mongo collection
   *
   * @return JSON data for the Destiny 2 manifest
   */
  public Manifest updateManifest() {
    Manifest manifest = new Manifest();
    BungieManifest bungieManifest = platformClient.getManifest();

    if (bungieManifest == null || bungieManifest.getResponse() == null) {
      return manifest;
    }

    WorldComponentContent worldComponentContent = bungieManifest.getResponse().getWorldComponentContentPaths().getContent();

    for (String definitionUri : worldComponentContent.getAllDefinitions()) {
      manifestAsyncService.saveDefinition(definitionUri);
    }

    manifest.setVersion(bungieManifest.getResponse().getVersion());
    return manifest;
  }


  /**
   * Retrieve the definition from the Mongo collection by the provided definition name.
   *
   * @param definitionName name of the Destiny manifest definition to be retrieved
   * @return all definitions under the given definition name
   */
  public ContentPropertyResponse getContentPropertiesByDefinition(String definitionName) {
    ContentPropertyResponse response = new ContentPropertyResponse();

    List<Property> allProperties = mongoTemplate.findAll(Property.class, definitionName);
    response.setContentProperties(mapper.toContentProperties(allProperties));
    response.setDefinition(definitionName);

    log.info("{} properties found in the {} collection", allProperties.size(), definitionName);

    return response;
  }

}
