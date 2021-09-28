package kitchenpos.common.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderTableId implements Serializable {

    @Column(name = "id", columnDefinition = "varbinary(16)")
    private UUID value;

    protected OrderTableId() {
    }

    public OrderTableId(final UUID value) {
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
        final OrderTableId that = (OrderTableId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
