package kitchenpos.products.tobe.domain;

import kitchenpos.products.common.NamePolicy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private ProductName name;

    @Embedded
    private ProductPrice price;

    public Product() {
    }

    public Product(UUID id, ProductName name, ProductPrice price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public void updatePrice(BigDecimal price) {
        this.price = ProductPrice.of(price);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id;

        private ProductName name;

        private ProductPrice price;

        public Builder() {
        }

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder name(String name, NamePolicy namePolicy) {
            this.name = new ProductName(name, namePolicy);
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = new ProductPrice(price);
            return this;
        }

        public Product build() {
            return new Product(id, name, price);
        }
    }

}
