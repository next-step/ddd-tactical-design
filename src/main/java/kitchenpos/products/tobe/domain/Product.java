package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import static kitchenpos.products.exception.ProductExceptionMessage.PRODUCT_PRICE_MORE_ZERO;

@Entity
@Table(name = "product")
public class Product {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    @Column(name = "price", nullable = false)
    private Price price;

    @Embedded
    @Column(name = "name", nullable = false)
    private DisplayedName name;

    protected Product() {
    }

    private Product(UUID id, Price price, DisplayedName name) {
        this.id = id;
        this.price = price;
        this.name = name;
    }

    public static Product create(UUID id, BigDecimal price, String name, PurgomalumClient purgomalumClient) {
        return new Product(id, Price.of(price), DisplayedName.of(name, purgomalumClient));
    }

    public void changePrice(Long price) {
        if (price == null || price < 0) {
            throw new IllegalArgumentException(PRODUCT_PRICE_MORE_ZERO);
        }
        this.price = Price.of(BigDecimal.valueOf(price));
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public String getName() {
        return name.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
