package kitchenpos.menu.tobe.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "menu")
@Entity
public class Menu {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuName name;

    @Embedded
    private MenuPrice price;

    @Column(name = "mene_group_id", nullable = false)
    private UUID menuGroupId;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private MenuProducts menuProducts;

    protected Menu() {
    }

    public Menu(UUID id, String name, MenuPrice menuPrice, UUID menuGroupId, boolean displayed, List<MenuProduct> menuProducts) {
        this(id, new MenuName(name), menuPrice, menuGroupId, displayed, new MenuProducts(menuProducts));
    }

    public Menu(UUID id, MenuName name, MenuPrice price, UUID menuGroupId, boolean displayed, MenuProducts menuProducts) {
        MenuPrice totalMenuProductPrice = menuProducts.sumOfMenuProductPrice();
        if (price.isBiggerThan(totalMenuProductPrice)) {
            throw new IllegalArgumentException();
        }

        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public void checkPrice() {
        var sum = menuProducts.sumOfMenuProductPrice();
        if (this.price.isBiggerThan(sum)) {
            displayed = false;
        }
    }

    public void changePrice(MenuPrice price) {
        var sum = menuProducts.sumOfMenuProductPrice();
        if (price.isBiggerThan(sum)) {
            throw new IllegalArgumentException();
        }
        this.price = sum;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public MenuName getName() {
        return name;
    }

    public String getNameValue() {
        return name.getValue();
    }

    public void setName(final MenuName name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price.getValue();
    }

    public void setPrice(final BigDecimal price) {
        this.price = new MenuPrice(price);
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void setDisplayed(final boolean displayed) {
        this.displayed = displayed;
    }

    public MenuProducts getMenuProducts() {
        return menuProducts;
    }

    public void setMenuProducts(final MenuProducts menuProducts) {
        this.menuProducts = menuProducts;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public void setMenuGroupId(final UUID menuGroupId) {
        this.menuGroupId = menuGroupId;
    }

}
