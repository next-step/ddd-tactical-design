package kitchenpos.menus.domain.tobe;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class MenuGroup {
    private UUID id;
    private String name;

    protected MenuGroup() {
    }

    public MenuGroup(final String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public UUID getId() {
        return id;
    }
}
