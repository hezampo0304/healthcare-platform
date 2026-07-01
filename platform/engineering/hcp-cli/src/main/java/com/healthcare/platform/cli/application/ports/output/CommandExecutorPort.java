package com.healthcare.platform.cli.application.ports.output;

import com.healthcare.platform.cli.domain.model.CommandResult;

public interface CommandExecutorPort {
  CommandResult execute(String... command);
}
