package kitchenpos.menus.domain.vo;

import kitchenpos.products.infra.PurgomalumClient;

import java.util.Objects;

public class MenuName {
    private final String name;

    public MenuName(String name, PurgomalumClient purgomalumClient) {
        if (Objects.isNull(name) || purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
