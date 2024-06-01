package kitchenpos.menus.domain.tobe;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Table(name = "menu_group")
@Entity(name = "menu_group")
public class MenuGroup {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    protected MenuGroup() {
    }

    public MenuGroup(final UUID id, final String name) {
        if (Optional.ofNullable(id).isEmpty()
                || Optional.ofNullable(name).isEmpty()) {
            throw new NoSuchElementException("메뉴그룹이 없습니다.");
        }

        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }
}
