package kitchenpos.menu.domain.service;

import kitchenpos.common.application.PurgomalumClient;
import kitchenpos.menu.domain.model.MenuGroupName;
import org.springframework.stereotype.Service;

@Service
public class MenuGroupNameCreationService {
    private static final String MENU_GROUP_NAME_VALIDATION_EXCEPTION = "메뉴 카테고리 이름에 비속어가 존재합니다. 비속어를 제외해주세요!";
    private final PurgomalumClient purgomalumClient;

    public MenuGroupNameCreationService(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    public MenuGroupName createName(String name) {
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException(MENU_GROUP_NAME_VALIDATION_EXCEPTION);
        }
        return new MenuGroupName(name);
    }
}
