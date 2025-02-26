package kitchenpos.product.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.UUID;

@Entity
public class ProductSummary {

    @Id
    @Column(name = "id")
    private UUID id;

    private String name;
    private long quantity;

    public ProductSummary(String name, long quantity) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.quantity = quantity;
    }

    protected ProductSummary() {
    }
}
