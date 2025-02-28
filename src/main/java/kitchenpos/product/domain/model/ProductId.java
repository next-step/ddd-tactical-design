package kitchenpos.product.domain.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.global.exception.NotFoundException;

@Embeddable
public class ProductId implements Serializable {

    private static final long serialVersionUID = -4375047652823160891L;

    private UUID id;

    protected ProductId() {}

    public ProductId(UUID id) {
        this.id = id;
    }

    public static ProductId of(UUID id) {
        if (id == null) {
            throw new NotFoundException(ErrorCode.NOT_FOUND_PRODUCT.toString());
        }
        return new ProductId(id);
    }

    public UUID get() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductId productId = (ProductId) o;
        return Objects.equals(id, productId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
