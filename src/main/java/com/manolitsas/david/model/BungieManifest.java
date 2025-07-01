package com.manolitsas.david.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BungieManifest {

  @JsonProperty("Response")
  private BungieManifestResponse response;

  @JsonProperty("ErrorCode")
  private Integer errorCode;

  @JsonProperty("ThrottleSeconds")
  private Integer throttleSeconds;

  @JsonProperty("ErrorStatus")
  private String errorStatus;

  @JsonProperty("Message")
  private String message;

  //  @JsonProperty("MessageData")
  //  private String messageData;
}
