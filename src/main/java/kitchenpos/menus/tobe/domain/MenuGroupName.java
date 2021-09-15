package kitchenpos.menus.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MenuGroupName {

    @Column(name = "name", nullable = false)
    private String name;

    protected MenuGroupName() { }

    public MenuGroupName(String name) {
        this.name = name;
    }
}
