package kitchenpos.menus.domain.tobe;


import kitchenpos.products.domain.tobe.BanWordFilter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Menu {
    private static final String MENU_GROUP_NULL_NOT_ALLOWED = "menuGroup이 있어야합니다.";

    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuName name;

    @Embedded
    private MenuPrice price;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "menu_group_id",
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private MenuGroup menuGroup;

    @Embedded
    private MenuDisplayed displayed;

    @Embedded
    private List<MenuProduct> menuProducts;

    public Menu(String name, BanWordFilter banWordFilter, BigDecimal price, MenuGroup menuGroup, boolean displayed, List<MenuProduct> menuProducts) {
        this(UUID.randomUUID(), name, banWordFilter, price, menuGroup, displayed, menuProducts);
    }

    public Menu(UUID id, String name, BanWordFilter banWordFilter, BigDecimal price, MenuGroup menuGroup, boolean displayed, List<MenuProduct> menuProducts) {
        validate(menuGroup);
        this.id = id;
        this.name = new MenuName(name, banWordFilter);
        this.price = new MenuPrice(price);
        this.menuGroup = menuGroup;
        this.displayed = new MenuDisplayed(displayed);
        this.menuProducts = menuProducts;
    }

    private void validate(MenuGroup menuGroup) {
        if (menuGroup == null) {
            throw new IllegalArgumentException(MENU_GROUP_NULL_NOT_ALLOWED);
        }
    }

    public void show() {
        this.displayed.show();
    }

    public void hide() {
        this.displayed.hide();
    }

    public void changePrice(BigDecimal price) {
        this.price = new MenuPrice(price);
    }

    public boolean getDisplayed() {
        return displayed.isDisplayed();
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Menu)) {
            return false;
        }
        Menu menu = (Menu) o;
        return Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
