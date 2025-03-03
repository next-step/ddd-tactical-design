package kitchenpos.tobe.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class ProductId implements Serializable {

    @Column(name = "id", columnDefinition = "binary(16)")
    private UUID id;

    protected ProductId() {
    }

    public ProductId(UUID id) {
        this.id = id;
    }

    public static ProductId of(UUID id) {
        return new ProductId(id);
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
        return Objects.hash(id);
    }

    public static ProductId newId() {
        return new ProductId(UUID.randomUUID());
    }

}
