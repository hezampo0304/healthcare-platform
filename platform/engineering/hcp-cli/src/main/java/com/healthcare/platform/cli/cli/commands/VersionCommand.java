package com.healthcare.platform.cli.cli.commands;

import com.healthcare.platform.cli.application.usecase.GetVersionUseCase;
import com.healthcare.platform.cli.domain.model.VersionInfo;
import picocli.CommandLine;

@CommandLine.Command(name = "version", description = "Healthcare Platform Engineering CLI")
public class VersionCommand implements Runnable {

  private final GetVersionUseCase useCase = new GetVersionUseCase();

  @Override
  public void run() {
    VersionInfo info = useCase.execute();
    System.out.printf(
        """
                %s
                Version : %s
                Java    : %s
                Build   : %s
                %n
                """,
        info.application(), info.version(), info.javaVersion(), info.build());
  }
}
