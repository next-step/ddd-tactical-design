package kitchenpos.menus.tobe.domain;

import java.util.Objects;

public class MenuGroup {

    private String name;
    private PurgomalumClient purgomalumClient;

    public MenuGroup(String name, PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException(name);

        }
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException(name);
        }
    }
}
