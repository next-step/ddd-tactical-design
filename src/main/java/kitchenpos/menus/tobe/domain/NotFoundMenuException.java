package kitchenpos.menus.tobe.domain;

import java.util.NoSuchElementException;

public class NotFoundMenuException extends NoSuchElementException {
    public NotFoundMenuException() {
        super("메뉴를 찾을 수 없습니다.");
    }
}
