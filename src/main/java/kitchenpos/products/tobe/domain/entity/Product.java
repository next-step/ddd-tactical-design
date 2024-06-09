package kitchenpos.products.tobe.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.common.domainevent.DomainEventPublisher;
import kitchenpos.common.domainevent.event.ProductPriceChanged;
import kitchenpos.common.purgomalum.PurgomalumClient;
import kitchenpos.products.tobe.domain.vo.ProductName;
import kitchenpos.products.tobe.domain.vo.ProductPrice;

@Table(name = "product")
@Entity
public class Product {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected Product() {
    }

    private Product(UUID id, ProductName name, ProductPrice price) {
        this.id = id;
        this.name = name.getValue();
        this.price = price.getValue();
    }

    public static Product createProduct(UUID id, String name, BigDecimal price, PurgomalumClient purgomalumClient) {
        return new Product(id, ProductName.of(name, purgomalumClient), ProductPrice.of(price));
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void changePrice(final ProductPrice price, DomainEventPublisher domainEventPublisher) {
        this.price = price.getValue();
        domainEventPublisher.publishEvent(new ProductPriceChanged(this.id));
    }
}
