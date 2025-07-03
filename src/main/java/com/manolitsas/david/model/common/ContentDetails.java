package com.manolitsas.david.model.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentDetails {

  @JsonProperty private DisplayProperties displayProperties;

  @JsonProperty private Long hash;

  @JsonProperty private Integer index;
}
