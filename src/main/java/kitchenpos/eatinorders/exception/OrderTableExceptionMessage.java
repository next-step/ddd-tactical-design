package kitchenpos.eatinorders.exception;

public class OrderTableExceptionMessage {
    public static final String ORDER_TABLE_NAME_EMPTY = "주문테이블 이름이 존재하지 않습니다.";
    public static final String NOT_FOUND_ORDER_TABLE = "주문테이블이 존재하지 않습니다.";
    public static final String NOT_EXIST_COMPLETE_ORDER = "주문완료된 주문테이블이 존재하지 않습니다.";
    public static final String NUMBER_GUESTS_NEGATIVE = "주문테이블 인원수는 음수가 될 수 없습니다.";
    public static final String NOT_OCCUPIED_GUESTS = "주문테이블이 손님이 있어야 손님수를 변경할 수 있습니다.";

}
