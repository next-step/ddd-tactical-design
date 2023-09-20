
package kitchenpos.products.domain;

import kitchenpos.products.domain.vo.ProductDisplayedName;
import kitchenpos.products.domain.vo.ProductPrice;
import kitchenpos.products.infra.PurgomalumClient;

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
    @Column(name = "displayed_name", nullable = false)
    private ProductDisplayedName displayedName;

    @Column(name = "price", nullable = false)
    private ProductPrice price;

    public Product() {
    }

    public Product(UUID id, String displayedName, BigDecimal price, PurgomalumClient purgomalumClient) {
        this.id = id;
        this.displayedName = new ProductDisplayedName(displayedName, purgomalumClient);
        this.price = new ProductPrice(price);
    }

    public Product(Product product, PurgomalumClient purgomalumClient) {
        this(
                product.getId(),
                product.getDisplayedName().getDisplayedName(),
                product.getPrice(),
                purgomalumClient
        );
    }

    public Product changePrice(BigDecimal price) {
        this.price = this.price.changePrice(price);
        return this;
    }

    public UUID getId() {
        return id;
    }

    public ProductDisplayedName getDisplayedName() {
        return displayedName;
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }
}