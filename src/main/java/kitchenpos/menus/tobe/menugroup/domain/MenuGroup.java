package kitchenpos.menus.tobe.menugroup.domain;

import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Table(name = "menu_group")
@Entity
public class MenuGroup {

    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    protected MenuGroup() {
    }

    public MenuGroup(final String name) {
        this(randomUUID(), name);
    }

    public MenuGroup(final UUID id, final String name) {
        verify(name);
        this.id = id;
        this.name = name;
    }

    private void verify(final String name) {
        if(!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("메뉴 그룹의 이름은 비워둘 수 없습니다.");
        }
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final MenuGroup menuGroup = (MenuGroup) o;
        return Objects.equals(id, menuGroup.id) && Objects.equals(name, menuGroup.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
