package kitchenpos.menu.exception;

public enum MenuExceptionMessage {

    NONE_MARGIN_EXCEPTION("마진이 남지 않습니다! 마진을 남기게 만들어주세요!"),
    MENU_PRODUCTS_EXISTS_EXCEPTION("메뉴 상품이 존재하지 않습니다!"),
    MENU_GROUP_EXISTS_EXCEPTION("메뉴 그룹이 존재하지 않습니다!"),
    MENU_CATEGORY_NAME_CREATION_EXCEPTION("메뉴 카테고리 이름을 채워주세요!"),
    MENU_GROUP_NAME_VALIDATION_EXCEPTION("메뉴 카테고리 이름에 비속어가 존재합니다. 비속어를 제외해주세요!"),
    MENU_NAME_CREATION_EXCEPTION("메뉴 이름을 채워주세요!"),
    MENU_NAME_VALIDATION_EXCEPTION("메뉴 이름에 비속어가 존재합니다. 비속어를 제외해주세요!"),
    MENU_PRICE_CREATION_EXCEPTION("메뉴 가격을 채워주세요!"),
    MENU_PRODUCT_QUANTITY_CREATION_EXCEPTION("메뉴 상품의 수량은 0보다 커야 합니다!");

    private final String message;

    MenuExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
