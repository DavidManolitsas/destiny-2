package com.manolitsas.david.model.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Inventory {

  @JsonProperty private Integer tierType;

  @JsonProperty private String tierTypeName;
}
