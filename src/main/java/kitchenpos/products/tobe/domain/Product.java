package kitchenpos.products.tobe.domain;

import kitchenpos.menus.tobe.domain.Menu;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    @Column(name = "name", nullable = false)
    private ProductName productName;

    @Embedded
    @Column(name = "price", nullable = false)
    private ProductPrice productPrice;

    public Product() {
    }

    public Product(UUID id, String name, BigDecimal price, boolean containsProfanity) {
        this.id = id;
        this.productName = new ProductName(name, containsProfanity);
        this.productPrice = new ProductPrice(price);
    }

    public UUID getId() {
        return id;
    }

    public ProductName getName() {
        return productName;
    }

    public ProductPrice getPrice() {
        return productPrice;
    }

    public BigDecimal multiplyPrice(final BigDecimal multiplicand) {
        BigDecimal price = this.productPrice.toBigDecimal();
        return price.multiply(multiplicand);
    }

    public static Product create(
        final UUID id,
        final String name,
        final BigDecimal price,
        boolean containsProfanity
    ) {
        return new Product(id, name, price, containsProfanity);
    }

    public void changePrice(final BigDecimal price, final List<Menu> menus) {
        this.productPrice = new ProductPrice(price);
        for (final Menu menu : menus) {
            if (menu.exceedsSum(menu.getBigDecimalPrice())) {
                menu.hide();
            }
        }
    }
}
