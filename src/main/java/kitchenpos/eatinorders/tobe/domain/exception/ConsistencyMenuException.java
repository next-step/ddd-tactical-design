package kitchenpos.eatinorders.tobe.domain.exception;

public class ConsistencyMenuException extends IllegalArgumentException {

    public ConsistencyMenuException() {
        super("요청하신 메뉴가 존재하지 않습니다.");
    }
}
