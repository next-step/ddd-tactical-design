package kitchenpos.eatinorders.exception;

public enum KitchenPosExceptionMessage {
    INVALID_ORDER_TABLE_NAME("주문테이블 이름은 비워둘 수 없습니다."),
    INVALID_ORDER_TABLE_GUESTS_NUMBER("주문테이블에 착석한 고객의 수는 0명 이상입니다. Invalid value = %s"),
    ORDER_TABLE_UNOCCUPIED_STATUS("주문테이블이 점유상태로 변경해야 합니다. OrderTableID = %s"),
    ORDER_TABLE_CONTAINS_INVALID_ORDER("해당 주문 테이블을 초기화 할 수 없는 주문의 주문상태가 포함되었습니다. OrderTableID = %s"),
    MENU_IS_HIDE("해당 메뉴는 숨김상태 입니다. MenuID = %s"),
    MENU_PRICE_IS_NOT_SAME("해당 메뉴의 가격은 주문을 요청한 메뉴의 가격과 다르다. MenuID = %s, MenuPrice( %s 원) != MenuPriceRequest( %s 원)"),
    INVALID_ORDER_LINE_ITEM_SIZE("주문아이템 목록을 구성하는 메뉴의 개수는 이미 등록된 메뉴의 개수와 일치하지 않는다. menuSize( %s 개) != orderLineItemSize( %s 개)"),
    INVALID_ORDER_LINE_ITEMS_SIZE("주문아이템 목록은 1개 이상의 주문아이템을 가져야 한다."),
    INVALID_ORDER_STATUS("현재 \"%s\" 주문상태가 아니어서 주문의 다음단계로 진행할 수 없습니다."),
    INVALID_ORDERLINEITEM_QUANTITY("해당 주문아이템의 수량은 음수일 수 없습니다. quantity : %s 개"),
    ;

    private final String message;

    KitchenPosExceptionMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
