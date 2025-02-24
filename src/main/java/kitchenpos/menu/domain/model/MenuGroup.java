package kitchenpos.menu.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Table(name = "menu_group")
@Entity
public class MenuGroup {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuGroupName name;

    public MenuGroup(MenuGroupName name) {
        this.name = name;
        this.id = UUID.randomUUID();
    }

    public MenuGroup(String name) {
        this.name = new MenuGroupName(name);
        this.id = UUID.randomUUID();
    }

    protected MenuGroup() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getName() {
        return name.getValue();
    }
}
