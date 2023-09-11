package kitchenpos.menus.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

import java.util.Objects;

public class MenuName {

    private String name;
    private final PurgomalumClient purgomalumClient;

    public MenuName(String name, PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;

        checkMenuName(name);
        this.name = name;
    }

    public void checkMenuName(String name) {
        if (Objects.isNull(name) || purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
    }

    public String getMenuName() {
        return this.name;
    }


}
