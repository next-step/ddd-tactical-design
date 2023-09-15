package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class ProductId implements Serializable {

    @Column(name = "id", columnDefinition = "binary(16)")
    private UUID id;

    public ProductId() {
        this.id = UUID.randomUUID();
    }

    public ProductId(UUID id) {
        this.id = id;
    }

    public UUID getValue() {
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
        return id != null ? id.hashCode() : 0;
    }
}
