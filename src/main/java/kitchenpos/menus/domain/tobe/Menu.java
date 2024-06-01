package kitchenpos.menus.domain.tobe;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Entity
public class Menu {
    @Id
    @Column(name = "id", columnDefinition = "binary(16)")
    private UUID id;
    @Embedded
    private DisplayedName name;
    @Embedded
    private MenuGroup menuGroup;
    @Embedded
    private Price price;
    @ElementCollection
    private MenuProducts menuProducts;
    private boolean displayed;

    protected Menu() {
    }

    private Menu(final DisplayedName name, final MenuGroup menuGroup, final Price price, final MenuProducts menuProducts) {
        if (this.isExpensiveToTotalProductPrice(price, menuProducts)) {
            throw new IllegalArgumentException("메뉴의 가격은 상품의 총 금액보다 적거나 같아야 된다.");
        }

        this.id = UUID.randomUUID();
        this.name = name;
        this.menuGroup = menuGroup;
        this.price = price;
        this.menuProducts = menuProducts;
        this.displayed = true;
    }

    public static final Menu createMenu(final String name, final MenuGroup menuGroup, final BigDecimal price, final MenuProducts menuProducts, final Profanities profanities){
        DisplayedName displayedName = DisplayedName.createDisplayedName(name, profanities);
        Price displayedPrice = Price.createPrice(price);

        return new Menu(displayedName, menuGroup, displayedPrice, menuProducts);
    }

    private boolean isExpensiveToTotalProductPrice(final Price price, final MenuProducts menuProducts) {
        if (price.comparePrice(menuProducts.totalAmount()) >= 1) {
            return true;
        }

        return false;
    }

    public void changePrice(final Price price){
        if (this.isExpensiveToTotalProductPrice(price, menuProducts)) {
            this.hideMenu();
        }

        this.price = price;
    }

    private void hideMenu() {
        this.displayed = false;
    }

    public UUID getId() {
        return id;
    }

    public DisplayedName getDisplayedName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public MenuProducts getMenuProducts() {
        return menuProducts;
    }

    public boolean isDisplayed() {
        return displayed;
    }
}
