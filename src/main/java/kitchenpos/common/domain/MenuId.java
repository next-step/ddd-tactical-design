package kitchenpos.common.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MenuId implements Serializable {

    @Column(name = "id", columnDefinition = "varbinary(16)")
    private UUID value;

    protected MenuId() {
    }

    public MenuId(final UUID value) {
        this.value = value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MenuId menuId = (MenuId) o;
        return Objects.equals(value, menuId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
