package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.ui.MenuRequest;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {
    @Column(name = "menu_id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private MenuName name;

    @Column(name = "price", nullable = false)
    private MenuPrice price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
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

    protected Menu() {
    }

    public static Menu of(MenuRequest request, MenuGroup menuGroup, MenuProducts menuProducts) {
        return new Menu(UUID.randomUUID(), request.getName(), request.getPrice(), menuGroup, request.isDisplayed(), menuProducts);
    }

    public Menu(
            UUID id,
            String name,
            BigDecimal price,
            MenuGroup menuGroup,
            boolean displayed,
            MenuProducts menuProducts
    ) {
        this.id = id;
        this.name = new MenuName(name);
        this.price = new MenuPrice(price);
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;

        if (isPriceLessThanTotal()) {
            throw new IllegalArgumentException();
        }
    }

    public void hideIfMenuPriceTooHigher() {
        if (isPriceLessThanTotal()) {
            this.displayed = false;
        }
    }

    public void changePrice(BigDecimal ten) {
        if (isPriceLessThanTotal()) {
            throw new IllegalArgumentException();
        }
        this.price = new MenuPrice(ten);
    }

    private boolean isPriceLessThanTotal() {
        return price.value().compareTo(menuProducts.getTotalPrice()) < 0;
    }

    public void setDisplayable() {
        if (isPriceLessThanTotal()) {
            throw new IllegalArgumentException();
        }
        this.displayed = true;
    }

    public void setHide() {
        this.displayed = false;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public String getId() {
        return id.toString();
    }

    public BigDecimal getPrice() {
        return price.value();
    }

    public String getMenuGroupId() {
        return menuGroup.getId();
    }

    public MenuProducts getMenuProducts() {
        return menuProducts;
    }

    public List<Long> getMenuProductsSeq() {
        return menuProducts.getSequences();
    }

    public String getName() {
        return name.value();
    }
}

