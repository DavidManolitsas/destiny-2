package com.manolitsas.david.client;

import com.manolitsas.david.model.platform.BungieManifest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;


@Component
@RequiredArgsConstructor
public class BungieRestClient implements BungieClient {

  private final RestClient bungiePlatformRestClient;

  @Override
  public BungieManifest getManifest() {
    return bungiePlatformRestClient.get()
        .uri("/Destiny2/Manifest")
        .retrieve()
        .body(BungieManifest.class);
  }
}
