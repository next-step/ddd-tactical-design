package kitchenpos.menu.application.menu.port.in;

import java.util.UUID;
import kitchenpos.menu.application.exception.NotExistMenuException;

public interface MenuDisplayingUseCase {

    /**
     * @throws NotExistMenuException id에 해당하는 메뉴가 없을 때
     * @throws IllegalStateException 메뉴가 포함한 메뉴구성음식 가격의 총 합보다 메뉴의 가격이 클 때
     */
    void display(final UUID id);

    /**
     * @throws NotExistMenuException id에 해당하는 메뉴가 없을 때
     */
    void hide(final UUID id);
}
