package kitchenpos.menus.exception;

public enum MenuErrorCode {
    NAME_CONTAINS_PROFANITY("비속어가 포함된 메뉴명은 사용할 수 없습니다."),
    NAME_IS_NOT_EMPTY_OR_NULL("이름은 비어있거나 null 일 수 없습니다."),
    PRICE_IS_NEGATIVE("메뉴의 가격은 음수가 될 수 없습니다."),
    PRICE_IS_NULL("메뉴의 가격은 null 일 수 없습니다."),
    MENU_PRODUCT_QUANTITY_NEGATIVE("메뉴구성 상품의 수량은 음수가 될 수 없습니다."),
    MENU_PRODUCTS_IS_NOT_EMPTY_OR_NULL("메뉴구성 상품은 비어있거나 null 일 수 없습니다."),
    QUANTITY_IS_NEGATIVE("수량이 음수가 될 수 없습니다.");

    String message;

    MenuErrorCode(String Message) {
        this.message = Message;
    }

    public String getMessage() {
        return message;
    }


}
