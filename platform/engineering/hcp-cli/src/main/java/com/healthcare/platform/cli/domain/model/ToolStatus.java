package com.healthcare.platform.cli.domain.model;

public record ToolStatus(Tool tool, boolean health, String version, String message) {}
