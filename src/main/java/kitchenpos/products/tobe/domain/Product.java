package kitchenpos.products.tobe.domain;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.products.tobe.domain.vo.ProductDisplayedName;
import kitchenpos.products.tobe.domain.vo.ProductPrice;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Product {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "displayed_name", nullable = false)
    private String displayedName;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public Product(UUID id, String displayedName, BigDecimal price) {
        this.id = id;
        this.displayedName = new ProductDisplayedName(displayedName).getDisplayedName();
        this.price = new ProductPrice(price).getPrice();
    }

    public Product changePrice(BigDecimal price) {
        return new Product(this.id, this.displayedName, price);
    }

    public Product changePrice(BigDecimal price, List<Menu> menus) {
        for (final Menu menu : menus) {
            for (final MenuProduct menuProduct : menu.getMenuProducts()) {
                if (menuProduct.getProduct().getId().equals(this.id)) {
                    Product product = menuProduct.getProduct().changePrice(price);
                    menuProduct.setProduct(product);
                }
            }
        }
        return new Product(this.id, this.displayedName, price);
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
