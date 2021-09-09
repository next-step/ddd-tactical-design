package kitchenpos.menus.tobe.domain.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import kitchenpos.common.tobe.domain.DisplayedName;

@Table(name = "menu_group")
@Entity
public class MenuGroup {

    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private DisplayedName name;

    protected MenuGroup() {
    }

    public MenuGroup(final DisplayedName name) {
        this.name = name;
    }

}
