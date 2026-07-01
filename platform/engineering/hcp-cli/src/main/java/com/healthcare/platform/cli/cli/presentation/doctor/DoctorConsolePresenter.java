package com.healthcare.platform.cli.cli.presentation.doctor;

import com.healthcare.platform.cli.domain.model.DoctorReport;
import com.healthcare.platform.cli.domain.model.ToolStatus;

public class DoctorConsolePresenter implements DoctorPresenter{

    @Override
    public String present(DoctorReport report) {
        StringBuilder builder = new StringBuilder();
        builder.append("Healthcare Platform Doctor\n");
        builder.append("----------------------------------------\n");
        for (ToolStatus status : report.toolStatuses()) {

            builder.append(status.tool())
                    .append(" : ")
                    .append(status.health() ? "OK" : "FAIL");

            if (!status.version().isBlank()) {
                builder.append(" (").append(status.version()).append(")");
            }

            if (!status.message().isBlank()) {
                builder.append(" - ").append(status.message());
            }

            builder.append('\n');
        }

        return builder.toString();
    }
}
