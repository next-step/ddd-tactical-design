package kitchenpos.menus.tobe.domain;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "menu_group")
@Entity
public class MenuGroup {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    protected MenuGroup() {
    }

    public MenuGroup(String name) {
        this(UUID.randomUUID(), name);
    }

    public MenuGroup(UUID id, String name) {
        validate(name);
        this.id = id;
        this.name = name;
    }

    private void validate(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name 은 비어있을 수 없습니다.");
        }
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
