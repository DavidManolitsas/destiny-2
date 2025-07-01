package com.manolitsas.david.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorldComponentContentPaths {

  @JsonProperty("en")
  private WorldComponentContent content;

}
