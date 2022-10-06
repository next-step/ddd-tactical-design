package kitchenpos.menus.menu.tobe.domain;

import kitchenpos.common.domain.vo.Price;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Embeddable
public class MenuProducts {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "menu")
    private List<MenuProduct> values = new ArrayList<>();

    protected MenuProducts() {
    }

    private MenuProducts(final List<MenuProduct> values) {
        this.values.addAll(values);
    }

    protected static MenuProducts of(final MenuProduct... values) {
        return of(List.of(values));
    }

    protected static MenuProducts of(final List<MenuProduct> values) {
        if (values.isEmpty()) {
            throw new IllegalArgumentException("메뉴 상품은 비어있을 수 없습니다.");
        }
        return new MenuProducts(values);
    }

    protected void makeRelation(final Menu menu) {
        for (MenuProduct value : values) {
            value.makeRelation(menu);
        }
    }

    protected void changePrice(final UUID productId, final Price price) {
        values.stream().parallel()
                .filter(menuProduct -> menuProduct.productId().equals(productId))
                .forEach(menuProduct -> menuProduct.changePrice(price));
    }

    public Price totalAmount() {
        return Price.valueOf(values.stream()
                .map(MenuProduct::amount)
                .mapToLong(Price::toLong)
                .sum());
    }

    public List<MenuProduct> values() {
        return Collections.unmodifiableList(values);
    }
}
