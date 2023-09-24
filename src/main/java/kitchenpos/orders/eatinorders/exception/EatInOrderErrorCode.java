package kitchenpos.orders.eatinorders.exception;

public enum EatInOrderErrorCode {

    IS_NOT_ACCEPTED("접수된 매장주문만 서빙할 수 있습니다."),
    IS_NOT_SERVED("서빙된 매장주문만 완료할 수 있습니다."),
    IS_NOT_WAITING("대기중인 매장주문만 접수할 수 있습니다."),
    MENU_IS_HIDE("숨겨진 메뉴는 주문할 수 없습니다."),
    ORDER_LINE_ITEMS_IS_EMPTY("주문한 메뉴가 없습니다."),
    ORDER_PRICE_EQUAL_MENU_PRICE("메뉴 가격과 주문금액이 다릅니다."),
    ORDER_TABLE_CANNOT_CLEAR("매장 테이블을 치울 수 없습니다."),
    ORDER_TABLE_UNOCCUPIED("매장 테이블이 사용중이 아닙니다.");

    private final String message;

    EatInOrderErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
