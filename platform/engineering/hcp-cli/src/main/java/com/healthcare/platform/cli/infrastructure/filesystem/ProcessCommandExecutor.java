package com.healthcare.platform.cli.infrastructure.filesystem;

import com.healthcare.platform.cli.application.ports.output.CommandExecutorPort;
import com.healthcare.platform.cli.domain.model.CommandResult;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessCommandExecutor implements CommandExecutorPort {
  @Override
  public CommandResult execute(String... command) {
    try {
      Process process = new ProcessBuilder(command).redirectErrorStream(false).start();

      String stdout = read(process.getInputStream());
      String stderr = read(process.getErrorStream());

      int exitCode = process.waitFor();

      return new CommandResult(exitCode, stdout, stderr);
    } catch (IOException | InterruptedException ex) {

      Thread.currentThread().interrupt();

      return new CommandResult(-1, "", ex.getMessage());
    }
  }

  private String read(InputStream stream) throws IOException {

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {

      return reader.lines().reduce("", (a, b) -> a + System.lineSeparator() + b).trim();
    }
  }
}
