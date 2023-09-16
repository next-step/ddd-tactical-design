package kitchenpos.ordertables.exception;

public enum OrderTableErrorCode {

    NUMBER_OF_GUEST_IS_GREATER_THAN_ZERO("방문한 손님 수는 0이상이어야 합니다."),
    NON_OCCUPIED_CANNOT_CHANGE_NUMBER_OF_GUESTS("미사용 테이블은 방문한 손님 수를 변경할 수 없습니다."),
    NOT_COMPLETED_ORDER("완료되지 않은 주문이 있습니다."),
    NAME_IS_EMTPY("주문테이블 이름이 공백입니다.");

    private final String message;

    OrderTableErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
