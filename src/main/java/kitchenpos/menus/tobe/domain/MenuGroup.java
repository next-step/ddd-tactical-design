package kitchenpos.menus.tobe.domain;

import java.util.Objects;

public class MenuGroup {

    private String name;

    public MenuGroup(String name, PurgomalumClient purgomalumClient) {
        validateName(name, purgomalumClient);
        this.name = name;
    }

    private void validateName(String name, PurgomalumClient purgomalumClient) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException(name);

        }
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException(name);
        }
    }
}
