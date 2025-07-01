package com.manolitsas.david.service;

import com.manolitsas.david.client.BungieRestClient;
import com.manolitsas.david.dto.Manifest;
import com.manolitsas.david.mapper.BungieMapper;
import com.manolitsas.david.model.BungieManifest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManifestService {

  private final BungieRestClient client;
  private final BungieMapper mapper;

  public Manifest getBungieManifest() {
    BungieManifest bungieManifest = client.getManifest();

    if (bungieManifest == null || bungieManifest.getResponse() == null) {
      return null;
    }

    return mapper.toManifest(bungieManifest.getResponse().getWorldComponentContentPaths().getContent());
  }

}
