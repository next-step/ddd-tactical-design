package kitchenpos.order.common.exception;

public enum OrderLineItemExceptionMessage {
    ORDER_LINE_ITEM_QUANTITY_EXCEPTION("주문 내역의 메뉴 수량이 비어있습니다!"),
    ORDER_LINE_ITEM_MENU_DISPLAY_EXCEPTION("주문 내역의 메뉴가 게시되어 있지 않습니다!"),
    ORDER_LINE_ITEM_PRICE_EXCEPTION("주문 내역의 가격이 메뉴의 가격과 다릅니다!");
    private final String message;

    OrderLineItemExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
