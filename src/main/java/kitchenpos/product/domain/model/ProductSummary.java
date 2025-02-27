package kitchenpos.product.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
public class ProductSummary {

    @Id
    @Column(name = "id")
    private UUID id;

    private String name;
    private long quantity;

    public ProductSummary(UUID productId, String name, long quantity) {
        this.id = productId;
        this.name = name;
        this.quantity = quantity;
    }

    protected ProductSummary() {
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductSummary that = (ProductSummary) o;
        return quantity == that.quantity && Objects.equals(id, that.id) && Objects.equals(name,
                that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, quantity);
    }
}
