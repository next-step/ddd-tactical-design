package kitchenpos.menus.tobe.domain.exception;

public class InvalidMenuArgumentNullPointException extends RuntimeException {
    private static final String MESSAGE = "메뉴를 생성하기 위한 구성 요소 중 하나가 null 입니다.";

    public InvalidMenuArgumentNullPointException() {
        super(MESSAGE);
    }
}
