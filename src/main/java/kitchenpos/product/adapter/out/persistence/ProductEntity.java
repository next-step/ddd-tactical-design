package kitchenpos.product.adapter.out.persistence;

import java.math.BigDecimal;
import java.util.UUID;

// TODO: JPA Entity로 만들기
class ProductEntity {
    private UUID id;

    private String name;

    private BigDecimal price;

    public ProductEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }
}
