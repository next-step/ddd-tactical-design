package kitchenpos.menus.tobe.domain;

import jakarta.persistence.Embeddable;

import java.util.Objects;
import java.util.UUID;

@Embeddable
public class MenuId {
    private UUID id;

    public static MenuId generate() {
        return new MenuId(UUID.randomUUID());
    }

    protected MenuId() {
    }

    public MenuId(final UUID id) {
        Objects.requireNonNull(id, "menu id는 필수 입력 항목입니다");
        this.id = id;
    }

    public UUID value() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuId menuId = (MenuId) o;
        return Objects.equals(id, menuId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
