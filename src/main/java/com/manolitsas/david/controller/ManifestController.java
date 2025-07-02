package com.manolitsas.david.controller;

import com.manolitsas.david.api.ManifestApi;
import com.manolitsas.david.dto.Manifest;
import com.manolitsas.david.service.ManifestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ManifestController implements ManifestApi {

  private final ManifestService service;

  @Override
  public ResponseEntity<Manifest> manifestGet() {
    return ResponseEntity.ok(service.getBungieManifest());
  }

  @Override
  public ResponseEntity<Manifest> manifestPut() {
    return ResponseEntity.ok(service.updateManifest());
  }
}
