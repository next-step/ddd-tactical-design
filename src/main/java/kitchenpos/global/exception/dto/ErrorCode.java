package kitchenpos.global.exception.dto;

public enum ErrorCode {

    NO_SUCH_ELEMENT("E001", "데이터를 찾지 못했습니다.");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
