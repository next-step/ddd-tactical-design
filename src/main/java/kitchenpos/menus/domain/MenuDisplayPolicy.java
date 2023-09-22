package kitchenpos.menus.domain;

import org.springframework.stereotype.Component;

@Component
public class MenuDisplayPolicy {
    private final MenuPricePolicy menuPricePolicy;

    public MenuDisplayPolicy(MenuPricePolicy menuPricePolicy) {
        this.menuPricePolicy = menuPricePolicy;
    }

    public void follow(Menu menu) {
        try {
            menuPricePolicy.follow(menu.getPrice(), menu.totalPrice());
        } catch (IllegalArgumentException e) {
            menu.hide();
        }
    }
}
