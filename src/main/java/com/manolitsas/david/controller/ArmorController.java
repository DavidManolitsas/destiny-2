package com.manolitsas.david.controller;

import com.manolitsas.david.api.ArmorApi;
import com.manolitsas.david.dto.ArmorRequest;
import com.manolitsas.david.dto.ArmorResponse;
import com.manolitsas.david.service.ArmorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArmorController implements ArmorApi {

  private final ArmorService service;

  @Override
  public ResponseEntity<ArmorResponse> armorGet(ArmorRequest armorRequest) {
    return ResponseEntity.ok(service.calculateArmorStats(armorRequest));
  }
}
