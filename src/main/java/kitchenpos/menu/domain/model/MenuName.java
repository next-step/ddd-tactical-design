package kitchenpos.menu.domain.model;

import jakarta.persistence.Embeddable;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.global.exception.ProfanityException;
import kitchenpos.menu.domain.exception.MenuNameException;
import kitchenpos.menu.domain.service.MenuPurgomalumClient;

@Embeddable
public record MenuName(String name) {

    public static MenuName of(String name, MenuPurgomalumClient purgomalumClient) {
        if (name == null || name.isBlank()) {
            throw new MenuNameException();
        }

        if (purgomalumClient.containsProfanity(name)) {
            throw new ProfanityException(ErrorCode.MENU_NAME_PROFANITY_NOT_ALLOWED.toString());
        }
        return new MenuName(name);
    }

    public String get() {
        return name;
    }
}
