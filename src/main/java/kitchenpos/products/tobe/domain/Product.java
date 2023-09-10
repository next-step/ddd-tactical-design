package kitchenpos.products.tobe.domain;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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

    public Product() {
    }

    public Product(UUID id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
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

    public void validate(final boolean containsProfanity) {
        validatePrice();
        validateName(containsProfanity);
    }

    public void validatePrice() {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }

    private void validateName(final boolean containsProfanity) {
        if (Objects.isNull(name) || containsProfanity) {
            throw new IllegalArgumentException();
        }
    }

    public static Product create(final UUID id, final String name, final BigDecimal price) {
        return new Product(id, name, price);
    }

    public void changePrice(BigDecimal price, List<Menu> menus) {
        this.price = price;
        // TODO: 메뉴 리팩토링 시 메뉴쪽 메서드로 분리할 것!
        for (final Menu menu : menus) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final MenuProduct menuProduct : menu.getMenuProducts()) {
                sum = sum.add(
                        menuProduct.getProduct()
                                .getPrice()
                                .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
                );
            }
            if (menu.getPrice().compareTo(sum) > 0) {
                menu.setDisplayed(false);
            }
        }
    }
}
