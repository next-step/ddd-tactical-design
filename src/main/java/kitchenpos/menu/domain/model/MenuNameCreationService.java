package kitchenpos.menu.domain.model;

import kitchenpos.common.application.PurgomalumClient;
import org.springframework.stereotype.Service;

import static kitchenpos.menu.exception.MenuExceptionMessage.MENU_NAME_VALIDATION_EXCEPTION;

@Service
public class MenuNameCreationService {
    private final PurgomalumClient purgomalumClient;

    public MenuNameCreationService(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    public MenuName createName(String name) {
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException(MENU_NAME_VALIDATION_EXCEPTION.getMessage());
        }
        return new MenuName(name);
    }
}
