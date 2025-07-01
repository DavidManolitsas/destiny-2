package com.manolitsas.david.config;

import com.manolitsas.david.client.interceptor.RestClientInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

  @Value("${bungie.client.bungie-platform.base-url}")
  private String bungieBaseUrl;

  @Value("${bungie.client.bungie-platform.x-api-key}")
  private String bungieApiKey;

  @Value("${bungie.client.bungie-common.base-url}")
  private String bungieCommonBaseUrl;

  private final RestClientInterceptor interceptor;

  @Bean
  public RestClient bungiePlatformRestClient(RestClient.Builder builder) {
    return builder
        .baseUrl(bungieBaseUrl)
        .requestInterceptor(interceptor)
        .defaultHeaders(httpHeaders -> {
          httpHeaders.set("Accept", "application/json");
          httpHeaders.set("x-api-key", bungieApiKey);
        })
        .build();
  }

  @Bean
  public RestClient bungieCommonRestClient(RestClient.Builder builder) {
    return builder
        .baseUrl(bungieCommonBaseUrl)
        .requestInterceptor(interceptor)
        .defaultHeader("Accept", "application/json")
        .build();
  }
}
