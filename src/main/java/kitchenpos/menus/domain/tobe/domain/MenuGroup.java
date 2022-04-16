package kitchenpos.menus.domain.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Table(name = "menu_group")
@Entity
public class MenuGroup {

    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    private MenuGroupName name;

    protected MenuGroup() {
    }

    protected MenuGroup(String name) {
        this.name = new MenuGroupName(name);
    }
}
