package com.healthcare.platform.cli.configuration.modules;

import com.healthcare.platform.cli.application.checker.*;
import com.healthcare.platform.cli.application.ports.input.DoctorUseCase;
import com.healthcare.platform.cli.application.ports.output.CommandExecutorPort;
import com.healthcare.platform.cli.application.usecase.RunDoctorUseCase;
import com.healthcare.platform.cli.cli.commands.DoctorCommand;
import com.healthcare.platform.cli.cli.presentation.doctor.DoctorConsolePresenter;
import com.healthcare.platform.cli.cli.presentation.doctor.DoctorPresenter;
import com.healthcare.platform.cli.infrastructure.filesystem.ProcessCommandExecutor;

import java.util.List;

public final class DoctorModule {
    public DoctorCommand doctorCommand() {
        return new DoctorCommand(doctorUseCase(), doctorPresenter());
    }

    public DoctorPresenter doctorPresenter() {
        return new DoctorConsolePresenter();
    }

    public DoctorUseCase doctorUseCase() {
        return new RunDoctorUseCase(toolCheckers());
    }

    public List<ToolChecker> toolCheckers() {
        CommandExecutorPort executor = commandExecutor();

        return List.of(
                new JavaChecker(executor),
                new GitChecker(executor),
                new GradleChecker(executor),
                new DockerChecker(executor)
        );
    }

    public CommandExecutorPort commandExecutor() {
        return new ProcessCommandExecutor();
    }
}
