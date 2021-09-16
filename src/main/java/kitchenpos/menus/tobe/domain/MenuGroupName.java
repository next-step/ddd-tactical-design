package kitchenpos.menus.tobe.domain;

import kitchenpos.tobeinfra.TobePurgomalumClient;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuGroupName {

    @Column(name = "name", nullable = false)
    private String name;

    protected MenuGroupName() { }

    public MenuGroupName(String name, TobePurgomalumClient purgomalumClient) {
        validationName(name);
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    private void validationName(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
