package kitchenpos.products.domain.tobe.domain.vo;

import kitchenpos.support.vo.Id;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class ProductId extends Id implements Serializable {
    @Column(name = "id")
    private UUID id;

    public ProductId(UUID id) {
        this.id = id;
    }

    protected ProductId() {
    }

    public UUID getValue() {
        return id;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ProductId productId = (ProductId) o;

        return id != null ? id.equals(productId.id) : productId.id == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
