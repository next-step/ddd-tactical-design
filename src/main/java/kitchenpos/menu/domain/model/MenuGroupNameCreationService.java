package kitchenpos.menu.domain.model;

import static kitchenpos.menu.exception.MenuExceptionMessage.MENU_GROUP_NAME_VALIDATION_EXCEPTION;

import kitchenpos.common.application.PurgomalumClient;
import org.springframework.stereotype.Service;

import static kitchenpos.menu.exception.MenuExceptionMessage.MENU_GROUP_NAME_VALIDATION_EXCEPTION;

@Service
public class MenuGroupNameCreationService {
    private final PurgomalumClient purgomalumClient;

    public MenuGroupNameCreationService(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    public MenuGroupName createName(String name) {
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException(MENU_GROUP_NAME_VALIDATION_EXCEPTION.getMessage());
        }
        return new MenuGroupName(name);
    }
}
