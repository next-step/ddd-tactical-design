package kitchenpos.products.tobe.domain;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;

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

    public BigDecimal multiplyPrice(BigDecimal multiplicand) {
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
        // TODO: 메뉴 리팩토링 시 메뉴쪽 메서드로 분리할 것!
        for (final Menu menu : menus) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final MenuProduct menuProduct : menu.getMenuProducts()) {
                sum = sum.add(
                    menuProduct.getProduct()
                        .multiplyPrice(BigDecimal.valueOf(menuProduct.getQuantity()))
                );
            }
            if (menu.getPrice().compareTo(sum) > 0) {
                menu.setDisplayed(false);
            }
        }
    }
}
