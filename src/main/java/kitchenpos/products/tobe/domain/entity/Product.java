package kitchenpos.products.tobe.domain.entity;

import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.common.AggregateRoot;
import kitchenpos.products.tobe.domain.vo.ProductId;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Product implements AggregateRoot<ProductId> {
    @EmbeddedId
    private ProductId id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected Product() {
    }

    private Product(UUID id, String name, BigDecimal price) {
        this.id = new ProductId(id);
        this.name = name;
        this.price = price;
    }

    public ProductId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public static Product createProductByNameAndPrice(String name, BigDecimal price, PurgomalumClient purgomalumClient) {
        if (isInvalidPrice(price)) {
            throw new IllegalArgumentException();
        }

        if (isInvalidName(purgomalumClient, name)) {
            throw new IllegalArgumentException();
        }

        return new Product(UUID.randomUUID(), name, price);
    }

    public void changePrice(BigDecimal price) {
        if (isInvalidPrice(price)) {
            throw new IllegalArgumentException();
        }
        this.price = price;
    }

    private static boolean isInvalidPrice(BigDecimal price) {
        return Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0;
    }

    private static boolean isInvalidName(PurgomalumClient purgomalumClient, String name) {
        return Objects.isNull(name) || (purgomalumClient != null && purgomalumClient.containsProfanity(name));
    }
}
