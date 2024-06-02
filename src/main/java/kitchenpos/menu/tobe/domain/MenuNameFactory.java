package kitchenpos.menu.tobe.domain;

import kitchenpos.exception.IllegalNameException;
import kitchenpos.infra.PurgomalumClient;

public class MenuNameFactory {
    private final PurgomalumClient purgomalumClient;

    public MenuNameFactory(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    public MenuName create(String name) {
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalNameException("메뉴이름에 욕설을 포함할 수 없습니다.", name);
        }
        return new MenuName(name);
    }
}
