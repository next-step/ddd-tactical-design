package kitchenpos.menus.tobe.domain;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import kitchenpos.products.infra.PurgomalumClient;

@Table(name = "menu_group")
@Entity
public class MenuGroup {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private MenuGroupName name;

    protected MenuGroup() { }

    private MenuGroup(UUID id, MenuGroupName name) {
        this.id = id;
        this.name = name;
    }

    public static MenuGroup create(String name, PurgomalumClient purgomalumClient) {
        return new MenuGroup(UUID.randomUUID(), new MenuGroupName(name, purgomalumClient));
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.value();
    }
}
