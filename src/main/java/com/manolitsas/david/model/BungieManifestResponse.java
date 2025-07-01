package com.manolitsas.david.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BungieManifestResponse {

  @JsonProperty
  private String version;

  @JsonProperty("jsonWorldComponentContentPaths")
  private WorldComponentContentPaths worldComponentContentPaths;

}
