package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

import java.util.Objects;

public class Name {

    private final String name;

    public Name(final String name) {
        verify(name);
        this.name = name;
    }

    private void verify(String name) {

        if (Objects.isNull(name)) {
            throw new IllegalArgumentException();
        }
    }

}
