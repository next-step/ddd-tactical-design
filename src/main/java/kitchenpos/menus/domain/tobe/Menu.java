package kitchenpos.menus.domain.tobe;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.menugroups.domain.tobe.MenuGroup;

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

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "menu_group_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private MenuGroup menuGroup;

    @Embedded
    private DisplayedMenu displayed;

    @Embedded
    private MenuProducts menuProducts;

    @Transient
    private UUID menuGroupId;

    protected Menu() {
    }

    public Menu(MenuName name, MenuPrice price, MenuGroup menuGroup, DisplayedMenu displayed,
            MenuProducts menuProducts) {
        this(UUID.randomUUID(), name, price, menuGroup, displayed, menuProducts, menuGroup.getId());
    }

    public Menu(UUID id, MenuName name, MenuPrice price, MenuGroup menuGroup,
            DisplayedMenu displayed, MenuProducts menuProducts, UUID menuGroupId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
        this.menuGroupId = menuGroupId;
    }

    public void changePrice(MenuPrice price) {
        this.price = price;
    }

    public void display() {
        this.displayed = new DisplayedMenu(true);
    }

    public void hide() {
        this.displayed = new DisplayedMenu(false);
    }

    public boolean isDisplayed() {
        return this.displayed.isDisplayed();
    }

    public UUID getId() {
        return id;
    }

    public MenuPrice getMenuPrice() {
        return price;
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts.getMenuProducts();
    }
}
