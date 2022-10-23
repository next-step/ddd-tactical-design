package kitchenpos.menus.menugroup.tobe.domain;

import kitchenpos.common.domain.vo.Name;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Table(name = "menu_group")
@Entity
public class MenuGroup {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private Name name;

    protected MenuGroup() {
    }

    private MenuGroup(final UUID id, final Name name) {
        this.id = id;
        this.name = name;
    }

    public static MenuGroup create(final String name) {
        return new MenuGroup(UUID.randomUUID(), Name.valueOf(name));
    }

    public UUID id() {
        return id;
    }

    public Name name() {
        return name;
    }
}
