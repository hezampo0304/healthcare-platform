package com.healthcare.platform.cli.application.checker;

import com.healthcare.platform.cli.domain.model.Tool;
import com.healthcare.platform.cli.domain.model.ToolStatus;

public interface ToolChecker {
  Tool tool();

  ToolStatus check();
}
