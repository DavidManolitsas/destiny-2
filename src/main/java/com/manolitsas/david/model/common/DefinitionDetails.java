package com.manolitsas.david.model.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DefinitionDetails {

  @JsonProperty private DisplayProperties displayProperties;

  @JsonProperty private Inventory inventory;

  @JsonProperty private String itemTypeDisplayName;

  @JsonProperty private String flavorText;

  @JsonProperty private String screenshot;

  @JsonProperty private Long hash;

  @JsonProperty private Integer index;

  @JsonProperty private Boolean isFeaturedItem;
}
