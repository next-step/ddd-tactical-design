package kitchenpos.global;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kitchenpos.global.error.dto.ErrorResponse;

import java.util.HashMap;
import java.util.Map;

@JsonSerialize(using = ApiResultResponseSerializer.class)
public class ApiResultResponse<T> {

    private final boolean success;
    private final Map<String, T> data = new HashMap<>();
    private final Map<String, ErrorResponse> error = new HashMap<>();

    public ApiResultResponse(boolean success,
                             T data, String domainName,
                             ErrorResponse error, String errorMessage) {
        this.success = success;
        if (data != null) {
            this.data.put(domainName, data);
        }
        if (error != null) {
            this.error.put(errorMessage, error);
        }
    }

    // 사용 안함
    public static <T> ApiResultResponse<T> success(T data, String domainName) {
        return new ApiResultResponse<>(true, data, domainName, null, null);
    }

    public static ApiResultResponse<ErrorResponse> failure(ErrorResponse errorResponse, String error) {
        return new ApiResultResponse<>(false, null, null, errorResponse, error);
    }

    public boolean isSuccess() {
        return success;
    }

    public Map<String, T> getData() {
        return data;
    }

    public Map<String, ErrorResponse> getError() {
        return error;
    }
}
