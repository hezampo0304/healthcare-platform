package com.healthcare.platform.cli.application.checker;

import com.healthcare.platform.cli.application.ports.output.CommandExecutorPort;
import com.healthcare.platform.cli.domain.model.CommandResult;
import com.healthcare.platform.cli.domain.model.Tool;
import com.healthcare.platform.cli.domain.model.ToolStatus;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaChecker implements ToolChecker {
  private static final Pattern VERSION_PATTERN = Pattern.compile("(\\d+\\.\\d+\\.\\d+)");

  private final CommandExecutorPort commandExecutor;

  public JavaChecker(CommandExecutorPort commandExecutor) {
    this.commandExecutor = commandExecutor;
  }

  @Override
  public Tool tool() {
    return Tool.JAVA;
  }

  @Override
  public ToolStatus check() {
    CommandResult versionResult = commandExecutor.execute("java", "--version");
    if (versionResult.exitCode() == 0) {
      return new ToolStatus(Tool.JAVA, true, versionResult.stdout(), "Java detected successfully.");
    }

    CommandResult daemonResult = commandExecutor.execute("java", "info");
    if (daemonResult.exitCode() != 0) {
      return new ToolStatus(
          Tool.JAVA,
          false,
          extractVersion(versionResult.stdout()),
          "Java is installed but the Java daemon is not running.");
    }

    return new ToolStatus(Tool.JAVA, true, extractVersion(versionResult.stdout()), "Installed");
  }

  private String extractVersion(String output) {

    Matcher matcher = VERSION_PATTERN.matcher(output);

    if (matcher.find()) {
      return matcher.group(1);
    }

    return "Unknown";
  }
}
