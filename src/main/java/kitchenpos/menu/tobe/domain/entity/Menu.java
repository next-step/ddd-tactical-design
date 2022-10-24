package kitchenpos.menu.tobe.domain.entity;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import kitchenpos.common.name.Name;
import kitchenpos.common.price.Price;
import kitchenpos.menu.tobe.domain.service.MenuDisplayPolicy;
import kitchenpos.menu.tobe.domain.vo.MenuProduct;

public class Menu {

    public final UUID id;

    public final Name name;

    public final MenuGroup menuGroup;

    private final List<MenuProduct> menuProducts;

    private Boolean displayed;

    private Price price;

    public Menu(
        final UUID id,
        final Name name,
        final Boolean displayed,
        final Price price,
        final MenuGroup menuGroup,
        final List<MenuProduct> menuProducts
    ) {
        this.id = id;
        this.name = name;
        this.displayed = displayed;
        this.price = price;
        this.menuGroup = menuGroup;
        this.menuProducts = menuProducts;
    }

    public boolean displayed() {
        return this.displayed;
    }

    public Price price() {
        return this.price;
    }

    public List<MenuProduct> menuProducts() {
        return Collections.unmodifiableList(this.menuProducts);
    }

    public void display() {
        if (!MenuDisplayPolicy.isDisplayable(this)) {
            throw new IllegalStateException("메뉴 노출 정책에 따라 이 메뉴를 노출할 수 없습니다");
        }
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }

    public void setPrice(final Price price) {
        if (!MenuDisplayPolicy.isDisplayable(this, price)) {
            throw new IllegalArgumentException("메뉴 노출 정책에 따라 가격을 변경할 수 없습니다");
        }
        this.price = price;
    }
}
