package kitchenpos.menu.domain.model;

import jakarta.persistence.Embeddable;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.menu.domain.service.MenuPurgomalumClient;
import kitchenpos.product.domain.service.ProductPurgomalumClient;

@Embeddable
public record MenuGroupName(String name) {

    public static MenuGroupName of(String name, MenuPurgomalumClient purgomalumClient) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(ErrorCode.MENU_GROUP_NAME_NOT_ALLOWED.toString());
        }

        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException(ErrorCode.MENU_GROUP_NAME_PROFANITY_NOT_ALLOWED.toString());
        }
        return new MenuGroupName(name);
    }
}
