package kitchenpos.menus.tobe.menugroup.entity;

import kitchenpos.common.Name;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MenuGroup {

    @Id
    @GeneratedValue
    private Long id;

    private Name name;

    public Long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }
}
