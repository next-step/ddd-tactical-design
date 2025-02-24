package kitchenpos.menu.adapter.out.persistance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kitchenpos.menu.domain.model.MenuGroup;
import kitchenpos.shared.domain.Profanities;

import java.util.Objects;
import java.util.UUID;

@Table(name = "menu_group")
@Entity
public class MenuGroupEntity {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    public MenuGroupEntity() {
    }

    public MenuGroupEntity(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public static MenuGroupEntity of(MenuGroup menuGroup) {
        return new MenuGroupEntity(menuGroup.getId(), menuGroup.getName());
    }

    public MenuGroup toDomain(final Profanities profanities) {
        return MenuGroup.create(id, name, profanities);
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MenuGroupEntity that = (MenuGroupEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
