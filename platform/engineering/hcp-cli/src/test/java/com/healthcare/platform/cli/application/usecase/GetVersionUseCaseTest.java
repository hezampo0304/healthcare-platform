package com.healthcare.platform.cli.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import com.healthcare.platform.cli.domain.model.VersionInfo;
import org.junit.jupiter.api.Test;

class GetVersionUseCaseTest {

  @Test
  void shouldReturnApplicationInformation() {
    GetVersionUseCase getVersionUseCase = new GetVersionUseCase();
    VersionInfo info = getVersionUseCase.execute();

    assertThat(info).isNotNull();
    assertThat(info.application()).isEqualTo("Healthcare Platform CLI");
    assertThat(info.version()).isEqualTo("0.1.0");
    assertThat(info.javaVersion()).isNotBlank();
    assertThat(info.build()).isEqualTo("Development");
  }
}
