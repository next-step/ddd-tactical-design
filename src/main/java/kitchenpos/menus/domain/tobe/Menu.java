package kitchenpos.menus.domain.tobe;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kitchenpos.products.infra.tobe.Profanities;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * - `MenuGroup`은 식별자와 이름을 가진다.
 * - `Menu`는 식별자와 `Displayed Name`, 가격, `MenuProducts`를 가진다.
 * - `Menu`는 특정 `MenuGroup`에 속한다.
 * - `Menu`의 가격은 `MenuProducts`의 금액의 합보다 적거나 같아야 한다.
 * - `Menu`의 가격이 `MenuProducts`의 금액의 합보다 크면 `NotDisplayedMenu`가 된다.
 * - `MenuProduct`는 가격과 수량을 가진다.
 */
@Table(name = "menu")
@Entity(name = "menu")
public class Menu {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    @Column(name = "name", nullable = false)
    private DisplayedName displayedName;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "menu_group_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private MenuGroup menuGroup;

    @Embedded
    @Column(name = "price", nullable = false)
    private Price price;

    @Embedded
    private MenuProducts menuProducts;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    protected Menu() {
    }

    private Menu(final DisplayedName displayedName, final MenuGroup menuGroup, final Price price, final MenuProducts menuProducts) {
        this.validateMenuPrice(price, menuProducts);

        this.id = UUID.randomUUID();
        this.displayedName = displayedName;
        this.menuGroup = menuGroup;
        this.price = price;
        this.menuProducts = menuProducts;
        this.displayed = true;
    }

    private void validateMenuPrice(final Price price, final MenuProducts menuProducts) {
        if (menuProducts.isExpensiveTotalPrice(price)) {
            throw new IllegalArgumentException("메뉴의 가격은 상품의 총 금액보다 적거나 같아야 된다.");
        }
    }

    public static final Menu createMenu(final String name, final MenuGroup menuGroup, final BigDecimal price, final MenuProducts menuProducts, final Profanities profanities) {
        DisplayedName displayedName = DisplayedName.createDisplayedName(name, profanities);
        Price displayedPrice = Price.createPrice(price);

        return new Menu(displayedName, menuGroup, displayedPrice, menuProducts);
    }

    public void changePrice(final Price price) {
        this.price = price;

        if (this.menuProducts.isExpensiveTotalPrice(this.getPrice())) {
            this.hideMenu();
        }
    }

    public void changeProductPrice(final UUID productId, final BigDecimal price) {
        this.menuProducts.changeMenuProductsPrice(productId, price);

        if (this.menuProducts.isExpensiveTotalPrice(this.price)) {
            this.hideMenu();
        }
    }

    public UUID getId() {
        return this.id;
    }

    public DisplayedName getDisplayedName() {
        return this.displayedName;
    }

    public Price getPrice() {
        return this.price;
    }

    public MenuGroup getMenuGroup() {
        return this.menuGroup;
    }

    public MenuProducts getMenuProducts() {
        return this.menuProducts;
    }

    public boolean isDisplayed() {
        return this.displayed;
    }

    public void hideMenu() {
        this.displayed = false;
    }

    public void displayMenu() {
        this.displayed = true;
    }
}
