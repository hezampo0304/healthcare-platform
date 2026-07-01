package com.healthcare.platform.cli.cli.commands;

import com.healthcare.platform.cli.application.ports.input.DoctorUseCase;
import com.healthcare.platform.cli.cli.presentation.doctor.DoctorPresenter;
import com.healthcare.platform.cli.domain.model.DoctorReport;
import picocli.CommandLine;

@CommandLine.Command(
        name = "doctor",
        description = "Checks the development environment."
)
public class DoctorCommand implements Runnable {
    private final DoctorUseCase doctorUseCase;
    private final DoctorPresenter presenter;

    public DoctorCommand(DoctorUseCase doctorUseCase, DoctorPresenter presenter) {
        this.doctorUseCase = doctorUseCase;
        this.presenter = presenter;
    }

    @Override
    public void run() {
        DoctorReport report = doctorUseCase.execute();
        String output = presenter.present(report);
        System.out.println(output);
    }
}
