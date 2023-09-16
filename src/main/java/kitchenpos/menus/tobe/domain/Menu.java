package kitchenpos.menus.tobe.domain;

import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.Price;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private DisplayedName name;

    @Embedded
    private Price price;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "menu_group_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private MenuGroup menuGroup;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private MenuProducts menuProducts;


    public Menu() {}

    public Menu(UUID id, DisplayedName name, Price price, MenuGroup menuGroup, boolean displayed, MenuProducts menuProducts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public static Menu create(UUID id, MenuGroup menuGroup, MenuProducts menuProducts, Price price, DisplayedName name, boolean displayed) {
        return new Menu(id, name, price, menuGroup, displayed, menuProducts);
    }

    public List<UUID> getMenuProductIds() {
        return menuProducts.getMenuProductIds();
    }

    public void displayed() {
        this.displayed = true;
    }

    public void notDisplayed() {
        this.displayed = false;
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts.getMenuProducts();
    }

    public UUID getId() {
        return id;
    }
}
