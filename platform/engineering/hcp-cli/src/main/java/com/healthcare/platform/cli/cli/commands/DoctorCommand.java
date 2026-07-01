package com.healthcare.platform.cli.cli.commands;

import com.healthcare.platform.cli.application.ports.input.DoctorUseCase;
import com.healthcare.platform.cli.domain.model.DoctorReport;
import picocli.CommandLine;

@CommandLine.Command(
        name = "doctor",
        description = "Checks the development environment."
)
public class DoctorCommand implements Runnable {
    private final DoctorUseCase doctorUseCase;

    public DoctorCommand(DoctorUseCase doctorUseCase) {
        this.doctorUseCase = doctorUseCase;
    }

    @Override
    public void run() {
        DoctorReport report = doctorUseCase.execute();
        System.out.println(report);
    }
}
