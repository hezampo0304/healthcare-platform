package com.healthcare.platform.cli.cli.presentation.doctor;

import com.healthcare.platform.cli.domain.model.DoctorReport;

public interface  DoctorPresenter {
    String present(DoctorReport report);
}
