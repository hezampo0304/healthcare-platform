package com.healthcare.platform.cli.application.usecase;

import com.healthcare.platform.cli.domain.model.VersionInfo;

public class GetVersionUseCase {
  public VersionInfo execute() {
    return new VersionInfo(
        "Healthcare Platform CLI", "0.1.0", System.getProperty("java.version"), "Development");
  }
}
