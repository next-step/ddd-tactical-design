package kitchenpos.products.tobe.domain;

import jakarta.persistence.Embeddable;
import kitchenpos.products.tobe.domain.exception.InvalidProductNameException;

@Embeddable
public record ProductName(String name) {

    public ProductName {
        if (name == null || name.isBlank()) {
            throw new InvalidProductNameException("Product name 은 null 이거나 빈 값이 될 수 없습니다.");
        }
        name = name.strip();
    }
}