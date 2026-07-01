package com.healthcare.platform.cli.domain.model;

import java.util.List;

public record DoctorReport(
        List<ToolStatus> toolStatuses
) {
    public DoctorReport {
        toolStatuses = List.copyOf(toolStatuses);
    }
}
