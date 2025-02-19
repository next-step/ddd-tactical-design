package kitchenpos.order.eatinorder.exception;

public enum EatInOrderExceptionMessage {
    ORDER_TABLE_NAME_CREATION_EXCEPTION("주문 테이블 이름을 채워주세요!"),
    EMPTY_ORDER_TABLE_EXCEPTION("주문 테이블이 비어있습니다!"),
    NUMBER_OF_GUESTS_EXCEPTION("손님 수가 음수일 수 없습니다!"),
    EAT_IN_ORDER_EMPTY_ORDER_LINE_ITEM_EXCEPTION("매장 주문에 주문 내역이 비어있습니다!"),
    EAT_IN_ORDER_FLOW_EXCEPTION("잘못된 매장 주문 순서입니다. 주문 순서를 지켜주세요!"),
    EAT_IN_ORDER_FLOW_NOT_FOUND_EXCEPTION("매장 주문 상태에 맞는 주문 순서를 찾을 수 없습니다.");

    private final String message;

    EatInOrderExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
