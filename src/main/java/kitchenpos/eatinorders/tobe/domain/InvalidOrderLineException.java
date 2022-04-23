package kitchenpos.eatinorders.tobe.domain;

public class InvalidOrderLineException extends RuntimeException {

    private static final long serialVersionUID = 4860545663528575593L;

    public InvalidOrderLineException() {
        super("주문한 메뉴에 유효하지 않은 메뉴가 입력되었습니다.");
    }
}
