package kitchenpos.eatinorders.domain.tobe.domain.vo;

import kitchenpos.support.vo.Id;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public class OrderTableId extends Id {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    private UUID id;

    public OrderTableId(UUID id) {
        this.id = id;
    }

    protected OrderTableId() {
    }

    public UUID getValue() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        OrderTableId menuId = (OrderTableId) o;

        return id != null ? id.equals(menuId.id) : menuId.id == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
