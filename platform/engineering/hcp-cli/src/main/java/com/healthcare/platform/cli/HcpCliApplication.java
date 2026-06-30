package com.healthcare.platform.cli;

import com.healthcare.platform.cli.cli.commands.RootCommand;
import picocli.CommandLine;

public class HcpCliApplication {

  private HcpCliApplication() {
    throw new IllegalStateException("Utility class");
  }

  public static void main(String[] args) {
    int exitCode = new CommandLine(new RootCommand()).execute(args);

    System.exit(exitCode);
  }
}
