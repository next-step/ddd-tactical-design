package kitchenpos.menus.domain.tobe.menugroup;

import kitchenpos.menus.domain.tobe.MenuName;
import kitchenpos.products.domain.tobe.BanWordFilter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

public class MenuGroup {

    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuName name;

    protected MenuGroup() {
    }

    public MenuGroup(String name, BanWordFilter banWordFilter) {
        this(UUID.randomUUID(), name, banWordFilter);
    }

    public MenuGroup(UUID id, String name, BanWordFilter banWordFilter) {
        this.id = id;
        this.name = new MenuName(name, banWordFilter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuGroup)) {
            return false;
        }
        MenuGroup menuGroup = (MenuGroup) o;
        return Objects.equals(id, menuGroup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
