package kitchenpos.menus.tobe.domain.model;

import kitchenpos.common.domain.model.Name;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class MenuGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private Name name;

    public MenuGroup() {

    }

    public MenuGroup(Name name) {
        this.name = name;
    }

    public UUID getId() {
        return this.id;
    }

}
