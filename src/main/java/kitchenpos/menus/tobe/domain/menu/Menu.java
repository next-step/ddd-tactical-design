package kitchenpos.menus.tobe.domain.menu;

import java.util.Objects;
import java.util.UUID;
import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.Money;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupId;

public final class Menu {

    private final MenuId id;
    private final DisplayedName name;
    private Money price;
    private final MenuGroupId menuGroupId;
    private boolean displayed;
    private final MenuProducts menuProducts;

    public Menu(
        MenuId id,
        DisplayedName name,
        Money price,
        MenuGroupId menuGroupId,
        boolean displayed,
        MenuProducts menuProducts
    ) {
        validPrice(price);
        validMenuGroup(menuGroupId);
        validMenuProductsPrice(price, menuProducts);
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public Menu(
        UUID id,
        DisplayedName name,
        long price,
        UUID menuGroupId,
        boolean displayed,
        MenuProduct... menuProducts
    ) {
        this(
            new MenuId(id),
            name,
            new Money(price),
            new MenuGroupId(menuGroupId),
            displayed,
            new MenuProducts(menuProducts)
        );
    }

    private void validPrice(Money price) {
        if (price.isLessThan(Money.ZERO)) {
            throw new IllegalArgumentException(
                String.format("가격은 0원 보다 작을 수 없습니다. price: %s", price)
            );
        }
    }

    private void validMenuGroup(MenuGroupId menuGroupId) {
        if (Objects.isNull(menuGroupId)) {
            throw new IllegalArgumentException("MenuGroupId 는 null일 수 없습니다.");
        }
    }

    private void validMenuProductsPrice(Money price, MenuProducts menuProducts) {
        if (price.isMoreThan(menuProducts.calculatePrice())) {
            throw new IllegalArgumentException(
                String.format(
                    "메뉴의 가격은 메뉴 상품의 가격의 합보다 클수 없습니다. menuPrice: %s, menuProductsPrice: %s",
                    price,
                    menuProducts.calculatePrice()
                )
            );
        }
    }

    public void changePrice(Money price) {
        validPrice(price);
        validMenuProductsPrice(price, menuProducts);
        this.price = price;
    }

    public void display() {
        validMenuProductsPrice(price, menuProducts);
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
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

    @Override
    public String toString() {
        return "Menu{" +
            "id=" + id +
            ", name=" + name +
            ", price=" + price +
            ", menuGroup=" + menuGroupId +
            ", displayed=" + displayed +
            ", menuProducts=" + menuProducts +
            '}';
    }
}
