package com.healthcare.platform.cli.cli.commands;

import picocli.CommandLine;

@CommandLine.Command(
    name = "hcp",
    mixinStandardHelpOptions = true,
    version = "0.1.0",
    description = "Healthcare Platform Engineering CLI",
    subcommands = {VersionCommand.class})
public class RootCommand implements Runnable {
  @Override
  public void run() {
    System.out.println("Use --help to see available commands.");
  }
}
