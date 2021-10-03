package kitchenpos.menus.tobe.menu.domain.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import kitchenpos.common.tobe.domain.DisplayedName;
import kitchenpos.common.tobe.domain.Price;

@Table(name = "menu")
@Entity(name = "tobeMenu")
public class Menu {

    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private DisplayedName name;

    @Embedded
    private MenuPrice menuPrice;

    @Column(
        name = "menu_group_id",
        nullable = false,
        columnDefinition = "varbinary(16)"
    )
    private UUID menuGroupId;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private MenuProducts menuProducts;

    protected Menu() {
    }

    public Menu(final DisplayedName name, final MenuPrice menuPrice, final UUID menuGroupId, final boolean displayed, final MenuProducts menuProducts) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.menuPrice = menuPrice;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
        isValidPrice(menuPrice);
    }

    public Menu changePrice(final MenuPrice menuPrice) {
        isValidPrice(menuPrice);
        this.menuPrice = menuPrice;
        return this;
    }

    public Menu display() {
        isValidPrice(menuPrice);
        this.displayed = true;
        return this;
    }

    public Menu hide() {
        this.displayed = false;
        return this;
    }

    public void isValidPrice(final Price price) {
        if (price.compareTo(menuProducts.calculateTotalPrice()) > 0) {
            throw new IllegalArgumentException();
        }
    }

    public UUID getId() {
        return id;
    }

    public DisplayedName getName() {
        return name;
    }

    public MenuPrice getMenuPrice() {
        return menuPrice;
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
