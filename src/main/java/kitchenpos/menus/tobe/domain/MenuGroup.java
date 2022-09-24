package kitchenpos.menus.tobe.domain;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import kitchenpos.menus.tobe.domain.vo.MenuGroupName;

@Table(name = "menu_group")
@Entity
public class MenuGroup {

    @Id
    @Column(name = "id", columnDefinition = "binary(16)")
    private UUID id;

    @Embedded
    private MenuGroupName name;

    protected MenuGroup() {
        id = UUID.randomUUID();
    }

    public MenuGroup(MenuGroupName menuGroupName) {
        this();
        name = menuGroupName;
    }

    public UUID getId() {
        return id;
    }

    public MenuGroupName getName() {
        return name;
    }
}
