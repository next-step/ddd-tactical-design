package kitchenpos.global.exception;

public enum ErrorCode implements ErrorType {

    INTERNAL_SERVER_ERROR("500", "Internal Server Error"),
    PRODUCT_PRICE_NOT_ALLOWED("500", "상품가격은 0원 이상이어야 합니다."),
    PRODUCT_NAME_NOT_ALLOWED("500", "상품명은 반드시 있어야 합니다."),
    PRODUCT_NAME_PROFANITY_NOT_ALLOWED("500", "상품명은 반드시 있어야 합니다."),
    MENU_GROUP_NAME_NOT_ALLOWED("500", "메뉴그룹명은 반드시 있어야 합니다."),
    MENU_GROUP_NAME_PROFANITY_NOT_ALLOWED("500", "메뉴그룹명은 반드시 있어야 합니다."),
    MENU_PRICE_NOT_ALLOWED("500", "메뉴가격은 0원 이상이어야 합니다.");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", code, message);
    }
}
