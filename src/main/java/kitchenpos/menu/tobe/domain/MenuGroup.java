package kitchenpos.menu.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kitchenpos.exception.IllegalNameException;

import java.util.Objects;
import java.util.UUID;

@Table(name = "menu_group")
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
        validate(menuGroupName);
    }

    private static void validate(String menuGroupName) {
        if (Objects.isNull(menuGroupName) || menuGroupName.isEmpty()) {
            throw new IllegalNameException("이름은 빈값일 수 없습니다.", menuGroupName);
        }
    }

    protected MenuGroup() {

    }

    public UUID getId() {
        return id;
    }

    public String getMenuGroupName() {
        return MenuGroupName;
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
        return Objects.hashCode(id);
    }
}
