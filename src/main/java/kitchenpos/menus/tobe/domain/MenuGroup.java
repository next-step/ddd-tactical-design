package kitchenpos.menus.tobe.domain;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import kitchenpos.common.domain.Name;
import kitchenpos.products.infra.PurgomalumClient;

@Table(name = "menu_group")
@Entity
public class MenuGroup {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private Name name;

    protected MenuGroup() { }

    private MenuGroup(UUID id, Name name) {
        this.id = id;
        this.name = name;
    }

    public static MenuGroup of(String name, PurgomalumClient purgomalumClient) {
        return new MenuGroup(UUID.randomUUID(), new Name(name, purgomalumClient));
    }
}
