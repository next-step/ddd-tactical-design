package kitchenpos.menus.tobe.domain;

import jakarta.persistence.Embeddable;

import java.util.Objects;
import java.util.UUID;

@Embeddable
public class MenuGroupId {
    private UUID id;

    public static MenuGroupId generate() {
        return new MenuGroupId(UUID.randomUUID());
    }

    protected MenuGroupId() {
    }

    public MenuGroupId(final UUID id) {
        Objects.requireNonNull(id, "menuGroup id는 필수 입력 항목입니다");
        this.id = id;
    }

    public UUID value() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuGroupId that = (MenuGroupId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
