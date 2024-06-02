package kitchenpos.menu.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class MenuGroup {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String MenuGroupName;

    public MenuGroup(String menuGroupName) {
        this.id = UUID.randomUUID();
        this.MenuGroupName = menuGroupName;
    }

    protected MenuGroup() {

    }

    public UUID getId() {
        return id;
    }

    public String getMenuGroupName() {
        return MenuGroupName;
    }
}
