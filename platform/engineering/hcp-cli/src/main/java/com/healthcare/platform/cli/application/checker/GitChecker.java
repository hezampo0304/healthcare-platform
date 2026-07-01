package com.healthcare.platform.cli.application.checker;

import com.healthcare.platform.cli.application.ports.output.CommandExecutorPort;
import com.healthcare.platform.cli.domain.model.CommandResult;
import com.healthcare.platform.cli.domain.model.Tool;
import com.healthcare.platform.cli.domain.model.ToolStatus;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GitChecker implements ToolChecker {
  private static final Pattern VERSION_PATTERN = Pattern.compile("(\\d+\\.\\d+\\.\\d+)");

  private final CommandExecutorPort commandExecutor;

  public GitChecker(CommandExecutorPort commandExecutor) {
    this.commandExecutor = commandExecutor;
  }

  @Override
  public Tool tool() {
    return Tool.GIT;
  }

  @Override
  public ToolStatus check() {
    CommandResult versionResult = commandExecutor.execute("git", "--version");
    if (versionResult.exitCode() != 0) {
      return new ToolStatus(Tool.GIT, false, "", "GIT is not installed.");
    }

    CommandResult daemonResult = commandExecutor.execute("git", "info");
    if (daemonResult.exitCode() != 0) {
      return new ToolStatus(
          Tool.GIT,
          false,
          extractVersion(versionResult.stdout()),
          "GIT is installed but the GIT daemon is not running.");
    }

    return new ToolStatus(Tool.GIT, true, extractVersion(versionResult.stdout()), "Installed");
  }

  private String extractVersion(String output) {

    Matcher matcher = VERSION_PATTERN.matcher(output);

    if (matcher.find()) {
      return matcher.group(1);
    }

    return "Unknown";
  }
}
