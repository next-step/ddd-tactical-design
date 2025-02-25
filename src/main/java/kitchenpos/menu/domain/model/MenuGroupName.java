package kitchenpos.menu.domain.model;

import jakarta.persistence.Embeddable;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.global.exception.ProfanityException;
import kitchenpos.menu.domain.exception.MenuGroupNameException;
import kitchenpos.menu.domain.service.MenuPurgomalumClient;

@Embeddable
public record MenuGroupName(String name) {

    public static MenuGroupName of(String name, MenuPurgomalumClient purgomalumClient) {
        if (name == null || name.isBlank()) {
            throw new MenuGroupNameException();
        }

        if (purgomalumClient.containsProfanity(name)) {
            throw new ProfanityException(ErrorCode.MENU_GROUP_NAME_PROFANITY_NOT_ALLOWED.toString());
        }
        return new MenuGroupName(name);
    }
}
