package kitchenpos.menus.menu.tobe.domain;

import kitchenpos.menus.menu.tobe.domain.exception.InvalidMenuException;
import kitchenpos.menus.menu.tobe.domain.vo.MenuName;
import kitchenpos.menus.menu.tobe.domain.vo.Price;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {

    private static final int ZERO = 0;

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuName menuName;

    @Embedded
    private Price price;

    @Column(name = "menu_group_id", nullable = false)
    private UUID menuGroupId;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private MenuProducts menuProducts;

    protected Menu() {
    }

    private Menu(UUID id, MenuName menuName, Price price, UUID menuGroupId, boolean displayed, MenuProducts menuProducts) {
        this.id = id;
        this.menuName = menuName;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    protected static Menu create(final MenuName menuName, final Price price, final UUID menuGroupId, final boolean displayed, final MenuProducts menuProducts) {
        if (Objects.isNull(menuName)) {
            throw new InvalidMenuException(InvalidMenuException.MENU_NAME_MESSAGE);
        }
        if (Objects.isNull(price)) {
            throw new InvalidMenuException(InvalidMenuException.PRICE_MESSAGE);
        }
        if (Objects.isNull(menuGroupId)) {
            throw new InvalidMenuException(InvalidMenuException.MENU_GROUP_ID_MESSAGE);
        }
        if (Objects.isNull(menuProducts)) {
            throw new InvalidMenuException(InvalidMenuException.MENU_PRODUCTS_MESSAGE);
        }
        validatePrice(price, menuProducts.totalAmount());
        final Menu menu = new Menu(UUID.randomUUID(), menuName, price, menuGroupId, displayed, menuProducts);
        menu.menuProducts.makeRelation(menu);
        return menu;
    }

    private static void validatePrice(final Price price, final Price totalAmount) {
        if (price.compareTo(totalAmount) <= ZERO) {
            return;
        }
        throw new InvalidMenuException(price.toLong(), totalAmount.toLong());
    }

    public UUID id() {
        return id;
    }

    public MenuName menuName() {
        return menuName;
    }

    public Price price() {
        return price;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public UUID menuGroupId() {
        return menuGroupId;
    }

    public MenuProducts menuProducts() {
        return menuProducts;
    }
}
