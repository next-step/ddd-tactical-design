package kitchenpos.menu.domain.model;

import jakarta.persistence.Embeddable;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.menu.domain.service.MenuPurgomalumClient;

@Embeddable
public record MenuName(String name) {

    public static MenuName of(String name, MenuPurgomalumClient purgomalumClient) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(ErrorCode.MENU_NAME_NOT_ALLOWED.toString());
        }

        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException(ErrorCode.MENU_NAME_PROFANITY_NOT_ALLOWED.toString());
        }
        return new MenuName(name);
    }
}
