package kitchenpos.products.tobe.domain;

import jakarta.persistence.Embeddable;

import java.util.Objects;
import java.util.UUID;

@Embeddable
public class ProductId {
    private UUID id;

    protected ProductId() {
    }

    public static ProductId generate() {
        return new ProductId(UUID.randomUUID());
    }

    public ProductId(UUID id) {
        Objects.requireNonNull(id, "product id는 필수 입력 항목입니다");
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductId productId = (ProductId) o;
        return Objects.equals(id, productId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
