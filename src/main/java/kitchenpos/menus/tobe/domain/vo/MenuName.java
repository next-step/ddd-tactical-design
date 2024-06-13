package kitchenpos.menus.tobe.domain.vo;

import kitchenpos.common.purgomalum.PurgomalumClient;

import java.util.Objects;

public class MenuName {
    private final String value;

    private MenuName(String value) {
        this.value = value;
    }

    public static MenuName of(String name, PurgomalumClient purgomalumClient) {
        if (Objects.isNull(name) || purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
        return new MenuName(name);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuName that = (MenuName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
