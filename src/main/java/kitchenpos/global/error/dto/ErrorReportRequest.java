package kitchenpos.global.error.dto;


import jakarta.servlet.http.HttpServletRequest;

public record ErrorReportRequest(HttpServletRequest request, Exception exception) {
    private static final String ERROR_REPORT_FORMAT = "[RequestUri: %s] Method: %s";

    public String getLogMessage() {
        return String.format(ERROR_REPORT_FORMAT, request.getRequestURI(), request.getMethod());
    }
}
