package kitchenpos.common.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;

@Embeddable
public class MenuGroupId implements Serializable {

    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID value;

    protected MenuGroupId() {
    }

    public MenuGroupId(final UUID value) {
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
        final MenuGroupId that = (MenuGroupId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
