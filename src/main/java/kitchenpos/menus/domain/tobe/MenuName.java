package kitchenpos.menus.domain.tobe;

import java.util.Objects;
import kitchenpos.products.infra.PurgomalumClient;

public class MenuName {
    private final String value;

    public MenuName(final String name, final PurgomalumClient purgomalumClient) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException("name is required");
        }

        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException("slang cannot be included");
        }
        this.value = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MenuName menuName = (MenuName) o;

        return Objects.equals(value, menuName.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
