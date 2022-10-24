package kitchenpos.menu.tobe.domain.service;


import kitchenpos.common.price.Price;
import kitchenpos.menu.tobe.domain.entity.Menu;
import kitchenpos.menu.tobe.domain.vo.MenuProduct;
import org.springframework.stereotype.Service;

@Service
public class MenuDisplayPolicy {

    private MenuDisplayPolicy() {
    }

    public static boolean isDisplayable(final Menu menu) {
        final Price total = getTotalPrice(menu);
        return menu.price().compareTo(total) <= 0;
    }

    public static boolean isDisplayable(final Menu menu, final Price targetPrice) {
        final Price total = getTotalPrice(menu);
        return targetPrice.compareTo(total) <= 0;
    }

    private static Price getTotalPrice(final Menu menu) {
        return menu.menuProducts()
            .stream()
            .map(MenuProduct::subtotal)
            .reduce(new Price(0), Price::add);
    }
}
