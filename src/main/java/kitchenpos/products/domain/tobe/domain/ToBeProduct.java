package kitchenpos.products.domain.tobe.domain;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tobe_product")
@Entity
public class ToBeProduct {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Embedded
    private ProductPrice price;

    public ToBeProduct() {
    }

    public ToBeProduct(String name, BigDecimal productPrice) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = ProductPrice.of(productPrice);
    }

    public void changePrice(BigDecimal productPrice) {
        price = price.changePrice(productPrice);
    }

    public boolean isSamePrice(BigDecimal productPrice) {
        return price.isSamePrice(ProductPrice.of(productPrice));
    }

    public boolean isGreaterThanPrice(BigDecimal productPrice) {
        return price.isGreaterThan(ProductPrice.of(productPrice));
    }

}
