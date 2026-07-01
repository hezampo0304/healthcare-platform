package com.healthcare.platform.cli;

import com.healthcare.platform.cli.application.ports.output.CommandExecutorPort;
import com.healthcare.platform.cli.cli.commands.RootCommand;
import com.healthcare.platform.cli.configuration.ApplicationModule;
import com.healthcare.platform.cli.domain.model.CommandResult;
import com.healthcare.platform.cli.infrastructure.filesystem.ProcessCommandExecutor;
import picocli.CommandLine;

public class HcpCliApplication {

  private HcpCliApplication() {
    throw new IllegalStateException("Utility class");
  }

  public static void main(String[] args) {
    ApplicationModule applicationModule = new ApplicationModule();
    int exitCode = applicationModule.commandLine().execute(args);
    System.exit(exitCode);
  }
}
