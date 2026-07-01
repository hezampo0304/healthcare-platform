package com.healthcare.platform.cli.domain.model;

public record CommandResult(int exitCode, String stdout, String stderr) {}
