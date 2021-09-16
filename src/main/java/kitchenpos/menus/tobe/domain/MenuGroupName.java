package kitchenpos.menus.tobe.domain;

import kitchenpos.tobeinfra.TobePurgomalumClient;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MenuGroupName {

    @Column(name = "name", nullable = false)
    private String name;

    protected MenuGroupName() { }

    public MenuGroupName(String name, TobePurgomalumClient purgomalumClient) {
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }
}
