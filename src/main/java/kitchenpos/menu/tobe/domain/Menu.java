package kitchenpos.menu.tobe.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import kitchenpos.common.Price;

@Table(name = "menu")
@Entity
public class Menu {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuName name;

    @Embedded
    private Price price;

    @Column(name = "menu_group_id", nullable = false)
    private UUID menuGroupId;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private MenuProducts menuProducts;

    protected Menu() {
    }

    public Menu(UUID id, String name, Price price, UUID menuGroupId, boolean displayed, List<MenuProduct> menuProducts) {
        this(id, new MenuName(name), price, menuGroupId, displayed, new MenuProducts(menuProducts));
    }

    public Menu(UUID id, MenuName name, Price price, UUID menuGroupId, boolean displayed, MenuProducts menuProducts) {
        Price totalMenuProductPrice = menuProducts.sumOfMenuProductPrice();
        if (totalMenuProductPrice.isLowerThan(price)) {
            throw new IllegalArgumentException();
        }

        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public void changeMenuProductPrice(UUID productId, Price price) {
        this.menuProducts.changeMenuProductPrice(productId, price);
        var sum = menuProducts.sumOfMenuProductPrice();
        if (sum.isLowerThan(this.price)) {
            displayed = false;
        }
    }

    public void changePrice(Price price) {
        var sum = menuProducts.sumOfMenuProductPrice();
        if (sum.isLowerThan(price)) {
            throw new IllegalArgumentException();
        }
        this.price = price;
    }

    public void display() {
        var sum = menuProducts.sumOfMenuProductPrice();
        if (sum.isLowerThan(price)) {
            throw new IllegalStateException();
        }
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }

    public UUID getId() {
        return id;
    }

    public MenuName getName() {
        return name;
    }

    public String getNameValue() {
        return name.getValue();
    }

    public BigDecimal getPrice() {
        return price.getValue();
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public MenuProducts getMenuProducts() {
        return menuProducts;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }
}
