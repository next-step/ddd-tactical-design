package kitchenpos.menus.tobe.domain;

public class DisplayPolicy {
    public static boolean canDisplay(MenuProducts menuProducts, Price price) {
        return price.isBelowAmount(menuProducts.calculateAmount());
    }
}
