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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class ManifestService {

  private final BungieRestClient platformClient;
  private final BungieMapper mapper;
  private final ContentPropertyService contentPropertyService;


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
      contentPropertyService.saveContentProperties(definitionUri);
    }

    manifest.setVersion(bungieManifest.getResponse().getVersion());
    return manifest;
  }

}
