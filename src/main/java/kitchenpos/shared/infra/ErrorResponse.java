package kitchenpos.shared.infra;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    private int status;
    private String error;
    private String message;

    public ErrorResponse(HttpStatus status, String error, String message) {
        this.status = status.value();
        this.error = error;
        this.message = message;
    }

    // Getters
    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
