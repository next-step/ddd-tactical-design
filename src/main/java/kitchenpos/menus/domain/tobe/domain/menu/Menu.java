package kitchenpos.menus.domain.tobe.domain.menu;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.MenuGroupId;
import kitchenpos.common.domain.MenuId;
import kitchenpos.common.domain.Price;

@Table(name = "menu")
@Entity
public class Menu {

    @EmbeddedId
    @Column(name = "id", columnDefinition = "varbinary(16)")
    private MenuId id;

    @Embedded
    @Column(name = "displayed_name", nullable = false)
    private DisplayedName displayedName;

    @Embedded
    @Column(name = "price", nullable = false)
    private Price price;

    @Embedded
    @Column(name = "menu_group_id", columnDefinition = "varbinary(16)")
    private MenuGroupId menuGroupId;

    @Embedded
    @Column(name = "displayed", nullable = false)
    private Displayed displayed;

    @Embedded
    private MenuProducts menuProducts;

    protected Menu() {
    }

    public Menu(
        final MenuId id,
        final DisplayedName name,
        final Price price,
        final MenuGroupId menuGroupId,
        final Displayed displayed,
        final MenuProducts menuProducts
    ) {
        validatePrice(price, menuProducts);

        this.id = id;
        this.displayedName = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    private void validatePrice(final Price price, final MenuProducts menuProducts) {
        if (price.compareTo(menuProducts.calculateAmount()) > 0) {
            throw new IllegalArgumentException("메뉴의 가격은 메뉴 상품 금액의 합보다 적거나 같아야 합니다.");
        }
    }

    public Price getPrice() {
        return this.price;
    }

    public void changePrice(final Price price) {
        if (priceExceedsAmount(price)) {
            hide();
        }
        this.price = price;
    }

    private boolean priceExceedsAmount(final Price price, MenuProducts menuProducts) {
        return price.compareTo(menuProducts.calculateAmount()) > 0;
    }

    private boolean priceExceedsAmount(final Price price) {
        return priceExceedsAmount(price, menuProducts);
    }

    private boolean priceExceedsAmount() {
        return priceExceedsAmount(price);
    }

    public void display() {
        if (priceExceedsAmount()) {
            throw new IllegalArgumentException("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없습니다.");
        }
        this.displayed = new Displayed(true);
    }

    public void hide() {
        this.displayed = new Displayed(false);
    }

    public boolean isDisplayed() {
        return displayed.isDisplayed();
    }

    public boolean isHidden() {
        return displayed.isHidden();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Menu menu = (Menu) o;
        return Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
