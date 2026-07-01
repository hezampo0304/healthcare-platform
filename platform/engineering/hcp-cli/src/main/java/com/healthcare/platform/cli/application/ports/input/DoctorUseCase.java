package com.healthcare.platform.cli.application.ports.input;

import com.healthcare.platform.cli.domain.model.DoctorReport;

public interface DoctorUseCase {
  DoctorReport execute();
}
