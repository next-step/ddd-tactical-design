package kitchenpos.core.shared.identifier;

import jakarta.persistence.Embeddable;
import kitchenpos.core.products.tobe.domain.exception.InvalidProductIdException;

import java.io.Serializable;

@Embeddable
public class ProductId implements Serializable {

    private String value;

    @SuppressWarnings("unused")
    protected ProductId() {}

    private ProductId(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidProductIdException("Product ID 는 null 이거나 빈 값이 될 수 없습니다.");
        }
        this.value = value.strip();
    }

    public static ProductId of(String value) {
        return new ProductId(value);
    }

    public String getValue() {
        return value;
    }
}