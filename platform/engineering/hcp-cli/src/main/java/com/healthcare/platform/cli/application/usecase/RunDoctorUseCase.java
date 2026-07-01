package com.healthcare.platform.cli.application.usecase;

import com.healthcare.platform.cli.application.checker.ToolChecker;
import com.healthcare.platform.cli.application.ports.input.DoctorUseCase;
import com.healthcare.platform.cli.domain.model.DoctorReport;
import com.healthcare.platform.cli.domain.model.ToolStatus;

import java.util.ArrayList;
import java.util.List;

public class RunDoctorUseCase implements DoctorUseCase {
    private final List<ToolChecker> toolCheckers;

    public RunDoctorUseCase(List<ToolChecker> toolCheckers) {
        this.toolCheckers = List.copyOf(toolCheckers);
    }

    @Override
    public DoctorReport execute() {
        List<ToolStatus> toolStatuses = new ArrayList<>();
        for (ToolChecker checker : toolCheckers) {
            toolStatuses.add(checker.check());
        }
        return new DoctorReport(toolStatuses);
    }

}
