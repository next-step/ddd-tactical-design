package kitchenpos.global.exception;

public enum ErrorCode implements ErrorType {

    INTERNAL_SERVER_ERROR("500", "Internal Server Error"),

    NOT_FOUND_PRODUCT("404", "상품을 찾을 수 없습니다."),
    NOT_FOUND_ANY_PRODUCT("404", "일부상품을 찾을 수 없습니다."),
    NOT_FOUND_MENU("404", "메뉴를 찾을 수 없습니다."),
    NOT_FOUND_MENU_GROUP("404", "메뉴그룹을 찾을 수 없습니다."),
    NOT_FOUND_MENU_PRODUCT("404", "메뉴상품을 찾을 수 없습니다."),
    NOT_FOUND_ORDER("404", "주문을 찾을 수 없습니다."),
    NOT_FOUND_ORDER_MENU("404", "주문메뉴를 찾을 수 없습니다."),
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
    MENU_PRODUCT_QTY_NOT_ALLOWED("400", "메뉴 상품 수량은 0개 이상이어야 합니다."),

    DELIVERY_ADDRESS_NOT_ALLOWED("400", "배달 주소는 필수값 입니다."),
    ORDER_PRICE_MISMATCH_MENU_PRICE("400", "주문가격이 실제 메뉴 가격과 일치하지 않습니다."),
    ORDER_HIDE_MENU_NOT_ALLOWED("400", "숨김 메뉴는 주문할 수 없습니다."),
    ORDER_LINE_ITEM_QTY_NOT_ALLOWED("400", "주문 수량은 0개 이상이어야 합니다."),
    ORDER_TABLE_NAME_NOT_ALLOWED("400", "주문 테이블명은 필수값 입니다."),
    ORDER_TABLE_NUMBER_OF_GUESTS_NOT_ALLOWED("400", "주문 테이블 인원수는 0명 이상이어야 합니다."),

    ORDER_STATUS_IS_NOT_WAITING("400", "주문대기 상태가 아닙니다."),
    ORDER_STATUS_IS_NOT_ACCEPTED("400", "주문수락 상태가 아닙니다."),
    ORDER_STATUS_IS_NOT_DELIVERED("400", "배달완료 상태가 아닙니다."),
    ORDER_STATUS_IS_NOT_SERVED("400", "주문제공 상태가 아닙니다."),
    ORDER_TYPE_IS_NOT_ALLOWED("400", "유효한 주문유형이 아닙니다.");

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
