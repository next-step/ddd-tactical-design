package kitchenpos.eatinorders.tobe.domain.exception;

public class IllegalOrderTableStatusException extends IllegalStateException {

    public static final String NEGATIVE_NUMBER_OF_GUESTS = "손님 수는 0명 이상이어야 합니다.";
    public static final String UNOCCUPIED = "비점유 상태의 테이블은 손님 수를 변경할 수 없습니다.";
    public static final String ALREADY_OCCUPIED = "이미 점유된 테이블입니다.";


    public IllegalOrderTableStatusException(String message) {
        super(message);
    }
}
