package kitchenpos.products.domain;

import kitchenpos.products.domain.vo.ProductDisplayedName;
import kitchenpos.products.domain.vo.ProductPrice;
import kitchenpos.products.infra.PurgomalumClient;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

public class Product {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "displayed_name", nullable = false)
    private String displayedName;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public Product() {
    }

    public Product(UUID id, String displayedName, BigDecimal price, PurgomalumClient purgomalumClient) {
        this.id = id;
        this.displayedName = new ProductDisplayedName(displayedName, purgomalumClient).getDisplayedName();
        this.price = new ProductPrice(price).getPrice();
    }


    public Product changePrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public UUID getId() {
        return id;
    }

    public String getDisplayedName() {
        return displayedName;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
