package kitchenpos.menus.tobe.domain;

public class AmountPolicy {
    public static void validRegistrableMenuPrice(MenuPrice menuPrice, MenuProducts menuProducts) {
        if (isAmountOver(menuPrice, menuProducts)) {
            throw new NotRegistrableMenuPriceException();
        }
    }

    public static void validUpdatableMenuPrice(MenuPrice menuPrice, MenuProducts menuProducts) {
        if (isAmountOver(menuPrice, menuProducts)) {
            throw new NotUpdatableMenuPriceException();
        }
    }

    public static void validDisplayableMenuPrice(MenuPrice menuPrice, MenuProducts menuProducts) {
        if (isAmountOver(menuPrice, menuProducts)) {
            throw new NotDisplayableMenuPriceException();
        }
    }

    public static boolean isAmountOver(MenuPrice menuPrice, MenuProducts menuProducts) {
        return menuPrice.getPrice().compareTo(menuProducts.getAmount()) > 0;
    }
}
