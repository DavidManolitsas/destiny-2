package com.manolitsas.david.service;

import com.manolitsas.david.dto.Manifest;
import org.springframework.stereotype.Service;

@Service
public class ManifestService {

  public Manifest getBungieManifest() {
    return new Manifest();
  }

}
