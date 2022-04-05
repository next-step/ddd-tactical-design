package kitchenpos.menus.domain.tobe.domain;

import kitchenpos.menus.domain.tobe.domain.vo.MenuGroupId;
import kitchenpos.menus.domain.tobe.domain.vo.MenuGroupName;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "menu_group")
@Entity
public class TobeMenuGroup {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @EmbeddedId
    private MenuGroupId id;

    @Column(name = "name", nullable = false)
    private MenuGroupName name;

    public TobeMenuGroup() {
    }

    public MenuGroupId getId() {
        return id;
    }

    public void setId(final MenuGroupId id) {
        this.id = id;
    }

    public MenuGroupName getName() {
        return name;
    }

    public void setName(final MenuGroupName name) {
        this.name = name;
    }
}
