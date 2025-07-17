package com.manolitsas.david.model.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Item {

  public Item(String hash) {
    this.hash = hash;
  }

  @Id private String hash;

  private String description;

  private String name;

  private String iconUrl;

  private String screenshotUrl;

  private String flavorText;

  private String itemType;

  private String itemTier;

  private Boolean isFeaturedItem;

  @CreatedDate private LocalDateTime createdAt;

  @LastModifiedDate private LocalDateTime updatedAt;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Item item = (Item) o;
    return Objects.equals(hash, item.hash) &&
        Objects.equals(description, item.description) &&
        Objects.equals(name, item.name) &&
        Objects.equals(iconUrl, item.iconUrl) &&
        Objects.equals(screenshotUrl, item.screenshotUrl) &&
        Objects.equals(flavorText, item.flavorText) &&
        Objects.equals(itemType, item.itemType) &&
        Objects.equals(itemTier, item.itemTier) &&
        Objects.equals(isFeaturedItem, item.isFeaturedItem);
  }
}
