package kitchenpos.eatinorders.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class EatInOrderId implements Serializable {

    @Column(name = "id", columnDefinition = "binary(16)")
    private UUID id;

    public EatInOrderId() {
        this.id = UUID.randomUUID();
    }

    public EatInOrderId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EatInOrderId)) return false;

        EatInOrderId eatInOrderId = (EatInOrderId) o;

        return getId() != null ? getId().equals(eatInOrderId.getId()) : eatInOrderId.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
