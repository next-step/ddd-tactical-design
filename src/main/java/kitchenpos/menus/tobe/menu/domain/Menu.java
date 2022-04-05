package kitchenpos.menus.tobe.menu.domain;

import kitchenpos.menus.tobe.menu.domain.product.Price;
import kitchenpos.menus.tobe.menugroup.domain.MenuGroup;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Table(name = "menu")
@Entity
public class Menu {
    @EmbeddedId
    private MenuId id;

    @Embedded
    private Name name;

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

    public Menu(final Name name, final Price amount, final MenuGroup menuGroup, final List<MenuProduct> menuProducts) {
        this(randomUUID(), name, amount, menuGroup, false, new MenuProducts(menuProducts));
    }

    public Menu(final Name name, final Price amount, final MenuGroup menuGroup, boolean displayed, final MenuProducts menuProducts) {
        this(randomUUID(), name, amount, menuGroup, displayed, menuProducts);
    }

    private Menu(final UUID id, final Name name, final Price price, final MenuGroup menuGroup, final boolean displayed, final MenuProducts menuProducts) {
        this(new MenuId(id), name, price, menuGroup, displayed, menuProducts);
    }

    private Menu(final MenuId id, final Name name, final Price amount, final MenuGroup menuGroup, final boolean displayed, final MenuProducts menuProducts) {
        verify(amount, menuProducts);
        this.id = id;
        this.name = name;
        this.price = amount;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    private boolean canDisplay(final MenuProducts menuProducts, final Price price) {
        return price.isBelowAmount(menuProducts.calculateAmount());
    }

    private void verify(final Price price, final MenuProducts menuProducts) {
        if (!canDisplay(menuProducts, price)) {
            throw new IllegalArgumentException("메뉴를 등록할 땐 가격(price)이 상품 가격 합(amount)보다 적거나 같아야합니다.");
        }
    }

    public void changePrice(final Price price) {
        this.price = price;
        checkHide();
    }

    public void checkHide() {
        if (!canDisplay(menuProducts, price)) {
            hide();
        }
    }

    public BigDecimal getPrice() {
        return price.value();
    }

    public void hide() {
        this.displayed = false;
    }

    public void display() {
        if (!canDisplay(menuProducts, price)) {
            throw new IllegalStateException("메뉴를 노출할땐 가격(price)이 상품 가격 합(amount)보다 적거나 같아야합니다.");
        }
        this.displayed = true;
    }

    public UUID getId() {
        return id.getId();
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts.getMenuProducts();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Menu menu = (Menu) o;

        return id != null ? id.equals(menu.id) : menu.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
