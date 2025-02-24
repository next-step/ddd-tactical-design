package kitchenpos.global.exception;

public enum ErrorCode implements ErrorType {

    INTERNAL_SERVER_ERROR("500", "Internal Server Error"),

    NOT_FOUND_PRODUCT("404", "상품을 찾을 수 없습니다."),
    NOT_FOUND_ANY_PRODUCT("404", "일부상품을 찾을 수 없습니다."),
    NOT_FOUND_MENU_GROUP("404", "메뉴그룹을 찾을 수 없습니다."),
    NOT_FOUND_MENU_PRODUCT("404", "메뉴상품을 찾을 수 없습니다."),
    PRODUCT_PRICE_NOT_ALLOWED("500", "상품가격은 0원 이상이어야 합니다."),
    PRODUCT_NAME_NOT_ALLOWED("500", "상품명은 반드시 있어야 합니다."),
    PRODUCT_NAME_PROFANITY_NOT_ALLOWED("500", "상품명은 비속어를 포함할 수 없습니다."),
    PRODUCT_QTY_NOT_ALLOWED("500", "상품수량은 0개 이상이어야 합니다."),
    MENU_GROUP_NAME_NOT_ALLOWED("500", "메뉴그룹명은 반드시 있어야 합니다."),
    MENU_GROUP_NAME_PROFANITY_NOT_ALLOWED("500", "메뉴그룹명은 비속어를 포함할 수 없습니다."),
    MENU_NAME_NOT_ALLOWED("500", "메뉴명은 반드시 있어야 합니다."),
    MENU_NAME_PROFANITY_NOT_ALLOWED("500", "메뉴명은 비속어를 포함할 수 없습니다."),
    MENU_PRICE_NOT_ALLOWED("500", "메뉴가격은 0원 이상이어야 합니다."),
    MENU_PRICE_OVER_TOTAL_PRODUCTS_NOT_ALLOWED("500", "메뉴가격은 구성 상품의 총 금액보다 클 수 없습니다.");

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
