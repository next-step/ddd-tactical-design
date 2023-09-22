package kitchenpos.menu.application.menu.port.in;

import kitchenpos.menu.application.exception.ContainsProfanityException;

public interface MenuRegistrationUseCase {

    /**
     * @throws ContainsProfanityException 이름에 비속어가 포함되어 있을 때
     * @throws IllegalArgumentException   menuGroup이 없을 때
     */
    MenuDTO register(final MenuRegistrationCommand command);
}
