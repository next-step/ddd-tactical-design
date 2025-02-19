package kitchenpos.menu.domain.model;

import kitchenpos.common.application.PurgomalumClient;
import org.springframework.stereotype.Service;

@Service
public class MenuNameCreationService {
    private static final String MENU_NAME_VALIDATION_EXCEPTION = "메뉴 이름에 비속어가 존재합니다. 비속어를 제외해주세요!";
    private final PurgomalumClient purgomalumClient;

    public MenuNameCreationService(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    public MenuName createName(String name) {
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException(MENU_NAME_VALIDATION_EXCEPTION);
        }
        return new MenuName(name);
    }
}
