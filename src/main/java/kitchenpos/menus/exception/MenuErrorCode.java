package kitchenpos.menus.exception;

public enum MenuErrorCode {

    MENU_PRODUCT_IS_EMPTY("메뉴 상품은 1개이상 입력되어야 합니다."),
    MENU_PRODUCT_PRICE_IS_EMPTY("메뉴 상품의 가격이 입력되어야 합니다."),
    QUANTITY_IS_GREATER_THAN_ZERO("수량은 0보다 커야 합니다."),
    NAME_IS_NULL_OR_EMPTY("이름이 없거나 공백입니다. 다시 입력해주세요."),
    NAME_HAS_PROFANITY("비속어를 빼고 입력해주세요."),
    MENU_PRICE_IS_GREATER_THAN_PRODUCTS("메뉴 가격은 메뉴 상품 가격의 합보다 작아야 합니다.");
    private final String message;

    MenuErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
