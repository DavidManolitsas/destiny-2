package com.manolitsas.david.model.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DisplayProperties {

  @JsonProperty
  private String description;

  @JsonProperty
  private String name;

  @JsonProperty
  private String icon;

  @JsonProperty
  private Boolean hasIcon;

}
