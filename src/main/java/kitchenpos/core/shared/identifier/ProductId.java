package kitchenpos.core.shared.identifier;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import kitchenpos.core.products.tobe.domain.exception.InvalidProductIdException;
import kitchenpos.core.shared.domain.ValueObject;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class ProductId extends ValueObject<ProductId> {

    private UUID value;

    @SuppressWarnings("unused")
    protected ProductId() {}

    private ProductId(UUID value) {
        if (value == null) {
            throw new InvalidProductIdException("Product ID 는 null 이거나 빈 값이 될 수 없습니다.");
        }
        this.value = value;
    }

    public static ProductId of(UUID value) {
        return new ProductId(value);
    }

    public UUID getValue() {
        return value;
    }

    @Override
    @Transient
    protected Object[] getEqualityFields() {
        return new Object[] { value };
    }
}