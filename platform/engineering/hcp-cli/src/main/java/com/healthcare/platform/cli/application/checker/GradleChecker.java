package com.healthcare.platform.cli.application.checker;

import com.healthcare.platform.cli.application.ports.output.CommandExecutorPort;
import com.healthcare.platform.cli.domain.model.CommandResult;
import com.healthcare.platform.cli.domain.model.Tool;
import com.healthcare.platform.cli.domain.model.ToolStatus;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GradleChecker implements ToolChecker {
  private static final Pattern VERSION_PATTERN = Pattern.compile("(\\d+\\.\\d+\\.\\d+)");

  private final CommandExecutorPort commandExecutor;

  public GradleChecker(CommandExecutorPort commandExecutor) {
    this.commandExecutor = commandExecutor;
  }

  @Override
  public Tool tool() {
    return Tool.GRADLE;
  }

  @Override
  public ToolStatus check() {
    CommandResult versionResult = commandExecutor.execute("gradle", "--version");
    if (versionResult.exitCode() != 0) {
      return new ToolStatus(
              Tool.GRADLE,
              false,
              "",
              "Gradle is not installed.");
    }
    return new ToolStatus(
            Tool.GRADLE,
            true,
            extractVersion(versionResult.stdout()),
            "Gradle detected successfully.");
  }

  private String extractVersion(String output) {

    Matcher matcher = VERSION_PATTERN.matcher(output);

    if (matcher.find()) {
      return matcher.group(1);
    }

    return "Unknown";
  }
}
