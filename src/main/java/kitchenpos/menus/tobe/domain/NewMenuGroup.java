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
public class NewMenuGroup {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    public NewMenuGroup() {
    }

    private NewMenuGroup(UUID id, String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException(NULL_EMPTY_NAME);
        }
        this.id = id;
        this.name = name;
    }

    public static NewMenuGroup create(UUID id, String name) {
        return new NewMenuGroup(id, name);
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
        NewMenuGroup newMenuGroup = (NewMenuGroup) o;
        return Objects.equals(id, newMenuGroup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
