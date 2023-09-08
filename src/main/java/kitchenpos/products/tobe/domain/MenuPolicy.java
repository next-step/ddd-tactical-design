package kitchenpos.products.tobe.domain;

import kitchenpos.menus.domain.Menu;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class MenuPolicy {

    public static void applyDisplayedPolicy(List<Menu> menus) {
        for (Menu menu : menus) {
            BigDecimal amount = getMenuAmount(menu);
            if (isNotValidMenuAmount(menu, amount)) {
                menu.setDisplayed(false);
            }
        }
    }

    private static boolean isNotValidMenuAmount(Menu menu, BigDecimal menuAmount) {
        return menu.getPrice().compareTo(menuAmount) > 0;
    }

    private static BigDecimal getMenuAmount(Menu menu) {
        return menu.getMenuProducts()
                .stream()
                .map(menuProduct -> menuProduct.getProduct().getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
