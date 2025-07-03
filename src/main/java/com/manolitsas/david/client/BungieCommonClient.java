package com.manolitsas.david.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manolitsas.david.model.common.ContentDetails;
import java.io.IOException;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class BungieCommonClient {

  private final RestClient bungieCommonRestClient;
  private final ObjectMapper objectMapper;

  public HashMap<String, ContentDetails> getCommonContent(String uri) {
    String response = bungieCommonRestClient.get().uri(uri).retrieve().body(String.class);
    try {
      return objectMapper.readValue(
          response, new TypeReference<HashMap<String, ContentDetails>>() {});
    } catch (IOException e) {
      throw new RuntimeException(
          String.format("Failed to parse response from Bungie Common API: %s", uri), e);
    }
  }
}
