package com.manolitsas.david.model.armor;

import com.manolitsas.david.dto.ArmorSlot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArmorError {

  private ArmorSlot slot;
  private String message;
}
