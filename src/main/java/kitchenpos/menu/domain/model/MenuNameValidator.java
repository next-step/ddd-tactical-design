package kitchenpos.menu.domain.model;

import kitchenpos.global.exception.ErrorCode;
import kitchenpos.menu.domain.service.MenuPurgomalumClient;
import kitchenpos.product.domain.service.ProductPurgomalumClient;

public record MenuNameValidator(
    String name,
    MenuPurgomalumClient purgomalumClient
) {
    public MenuNameValidator {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(ErrorCode.MENU_GROUP_NAME_NOT_ALLOWED.toString());
        }

        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException(ErrorCode.MENU_GROUP_NAME_PROFANITY_NOT_ALLOWED.toString());
        }
    }
}
