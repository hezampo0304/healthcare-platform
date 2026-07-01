package com.healthcare.platform.cli.configuration;

import com.healthcare.platform.cli.application.checker.*;
import com.healthcare.platform.cli.application.ports.output.CommandExecutorPort;
import com.healthcare.platform.cli.application.ports.input.DoctorUseCase;
import com.healthcare.platform.cli.application.usecase.RunDoctorUseCase;
import com.healthcare.platform.cli.cli.commands.DoctorCommand;
import com.healthcare.platform.cli.cli.commands.RootCommand;
import com.healthcare.platform.cli.cli.commands.VersionCommand;
import com.healthcare.platform.cli.infrastructure.filesystem.ProcessCommandExecutor;
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(
        name = "hcp",
        mixinStandardHelpOptions = true,
        version = "0.1.0",
        description = "Healthcare Platform Engineering CLI"
)
public class ApplicationModule {
    public CommandLine commandLine() {
        CommandLine commandLine =
                new CommandLine(rootCommand());

        commandLine.addSubcommand(
                "version",
                new VersionCommand());

        commandLine.addSubcommand(
                "doctor",
                doctorCommand());

        return commandLine;
    }

    private RootCommand rootCommand() {
        return new RootCommand();
    }

    private DoctorCommand doctorCommand() {
        return new DoctorCommand(doctorUseCase());
    }

    private DoctorUseCase doctorUseCase() {
        return new RunDoctorUseCase(toolCheckers());
    }

    private List<ToolChecker> toolCheckers() {
        CommandExecutorPort executor = commandExecutor();

        return List.of(
                new JavaChecker(executor),
                new GitChecker(executor),
                new GradleChecker(executor),
                new DockerChecker(executor)
        );
    }

    private CommandExecutorPort commandExecutor() {
        return new ProcessCommandExecutor();
    }
}
