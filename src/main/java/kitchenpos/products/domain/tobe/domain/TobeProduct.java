package kitchenpos.products.domain.tobe.domain;

import kitchenpos.products.domain.tobe.domain.vo.ProductId;
import kitchenpos.products.domain.tobe.domain.vo.ProductName;
import kitchenpos.products.domain.tobe.domain.vo.ProductPrice;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Table(name = "product")
@Entity
public class TobeProduct {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @EmbeddedId
    private ProductId id;

    @Column(name = "name", nullable = false)
    @Embedded
    private ProductName name;

    @Column(name = "price", nullable = false)
    @Embedded
    private ProductPrice price;

    protected TobeProduct() {
    }

    private TobeProduct(ProductName name, ProductPrice price) {
        this.name = name;
        this.price = price;
    }

    private TobeProduct(ProductId id, ProductName name, ProductPrice price) {
        this(name, price);
        this.id = id;
    }

    public TobeProduct changePrice(BigDecimal price) {
        this.price = new ProductPrice(price);
        return this;
    }

    public ProductId getId() {
        return id;
    }

    public ProductName getName() {
        return name;
    }

    public ProductPrice getPrice() {
        return price;
    }

    public static class Builder {
        private ProductId productId;
        private ProductName name;
        private ProductPrice price;


        public Builder() {
            this.productId = new ProductId(UUID.randomUUID());

        }
        public Builder productId(ProductId id) {
            this.productId = id;
            return this;
        }

        public Builder name(ProductName name) {
            this.name = name;
            return this;
        }

        public Builder price(ProductPrice price) {
            this.price = price;
            return this;
        }

        public TobeProduct build() {
            if (Objects.isNull(name) || Objects.isNull(price)) {
                throw new IllegalArgumentException();
            }
            return new TobeProduct(productId, name, price);
        }
    }
}
