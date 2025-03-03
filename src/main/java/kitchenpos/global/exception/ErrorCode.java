package kitchenpos.global.exception;

public enum ErrorCode implements ErrorType {

    INTERNAL_SERVER_ERROR("500", "Internal Server Error"),

    NOT_FOUND_PRODUCT("404", "상품을 찾을 수 없습니다."),
    NOT_FOUND_ANY_PRODUCT("404", "일부상품을 찾을 수 없습니다."),
    NOT_FOUND_MENU("404", "메뉴를 찾을 수 없습니다."),
    NOT_FOUND_MENU_GROUP("404", "메뉴그룹을 찾을 수 없습니다."),
    NOT_FOUND_MENU_PRODUCT("404", "메뉴상품을 찾을 수 없습니다."),
    NOT_FOUND_ORDER("404", "주문을 찾을 수 없습니다."),
    NOT_FOUND_ORDER_TABLE("404", "주문테이블을 찾을 수 없습니다."),
    NOT_FOUND_ORDER_ITEM("404", "주문항목을 찾을 수 없습니다."),

    PRODUCT_PRICE_NOT_ALLOWED("400", "상품가격은 0원 이상이어야 합니다."),
    PRODUCT_NAME_NOT_ALLOWED("400", "상품명은 필수값 입니다."),
    PRODUCT_NAME_PROFANITY_NOT_ALLOWED("400", "상품명은 비속어를 포함할 수 없습니다."),
    PRODUCT_QTY_NOT_ALLOWED("400", "상품수량은 0개 이상이어야 합니다."),

    MENU_GROUP_NAME_NOT_ALLOWED("400", "메뉴그룹명은 필수값 입니다."),
    MENU_GROUP_NAME_PROFANITY_NOT_ALLOWED("400", "메뉴그룹명은 비속어를 포함할 수 없습니다."),

    MENU_NAME_NOT_ALLOWED("400", "메뉴명은 필수값 입니다."),
    MENU_NAME_PROFANITY_NOT_ALLOWED("400", "메뉴명은 비속어를 포함할 수 없습니다."),
    MENU_PRICE_NOT_ALLOWED("400", "메뉴가격은 0원 이상이어야 합니다."),
    MENU_PRICE_OVER_TOTAL_PRODUCTS_NOT_ALLOWED("400", "메뉴가격은 구성 상품의 총 금액보다 클 수 없습니다."),

    MENU_PRODUCT_NOT_ALLOWED("400", "메뉴 상품은 1개 이상 입력해야 합니다."),
    MENU_PRODUCT_QTY_NOT_ALLOWED("400", "메뉴 상품 수량은 0개 이상이어야 합니다.");

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
