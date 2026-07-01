package com.healthcare.platform.cli.configuration;

import com.healthcare.platform.cli.cli.commands.RootCommand;
import com.healthcare.platform.cli.cli.commands.VersionCommand;
import com.healthcare.platform.cli.configuration.modules.DoctorModule;
import picocli.CommandLine;

@CommandLine.Command(
        name = "hcp",
        mixinStandardHelpOptions = true,
        version = "0.1.0",
        description = "Healthcare Platform Engineering CLI"
)
public class ApplicationModule {
    private final DoctorModule doctorModule =
            new DoctorModule();
    public CommandLine commandLine() {
        CommandLine commandLine =
                new CommandLine(rootCommand());

        commandLine.addSubcommand(
                "version",
                new VersionCommand());

        commandLine.addSubcommand(
                "doctor",
                doctorModule.doctorCommand());

        return commandLine;
    }

    private RootCommand rootCommand() {
        return new RootCommand();
    }


}
