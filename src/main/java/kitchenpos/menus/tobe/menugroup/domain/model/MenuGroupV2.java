package kitchenpos.menus.tobe.menugroup.domain.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import kitchenpos.common.tobe.domain.DisplayedName;

@Table(name = "menu_group")
@Entity
public class MenuGroupV2 {

    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private DisplayedName name;

    protected MenuGroupV2() {
    }

    public MenuGroupV2(final DisplayedName name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public DisplayedName getName() {
        return name;
    }
}
