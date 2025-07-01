package com.manolitsas.david.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Property {

  public Property(String hash) {
    this.hash = hash;
  }

  @Id
  private String hash;
  private String description;
  private String name;
  private String icon;
  private Boolean hasIcon;

}
