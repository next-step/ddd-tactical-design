package kitchenpos.menus.tobe.domain.model;

import java.util.Arrays;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    private Price price;

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "menu_group_id",
        columnDefinition = "varbinary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private MenuGroup menuGroup;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private MenuProducts menuProducts;

    protected Menu() {
    }

    public Menu(final DisplayedName name, final Price price, final MenuGroup menuGroup, final boolean displayed, final MenuProduct ... menuProductArr) {
        this(name, price, menuGroup, displayed, new MenuProducts(Arrays.asList(menuProductArr)));
    }

    public Menu(final DisplayedName name, final Price price, final MenuGroup menuGroup, final boolean displayed, final MenuProducts menuProducts) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
        isValidPrice(price);
    }

    public Menu changePrice(final Price price) {
        isValidPrice(price);
        this.price = price;
        return this;
    }

    public Menu display() {
        isValidPrice(price);
        this.displayed = true;
        return this;
    }

    public Menu hide() {
        this.displayed = false;
        return this;
    }

    public void isValidPrice(final Price price) {
        if (price.compareTo(menuProducts.getTotalPrice()) > 0) {
            throw new IllegalArgumentException();
        }
    }

    public UUID getId() {
        return id;
    }

    public DisplayedName getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public MenuProducts getMenuProducts() {
        return menuProducts;
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }
}
