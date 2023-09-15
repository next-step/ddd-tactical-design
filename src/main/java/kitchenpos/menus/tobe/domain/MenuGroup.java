package kitchenpos.menus.tobe.domain;

import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

import static kitchenpos.menus.exception.MenuGroupExceptionMessage.NULL_EMPTY_NAME;

@Table(name = "menu_group")
@Entity
public class MenuGroup {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    public MenuGroup() {
    }

    private MenuGroup(UUID id, String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException(NULL_EMPTY_NAME);
        }
        this.id = id;
        this.name = name;
    }

    public static MenuGroup create(UUID id, String name) {
        return new MenuGroup(id, name);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuGroup menuGroup = (MenuGroup) o;
        return Objects.equals(id, menuGroup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
