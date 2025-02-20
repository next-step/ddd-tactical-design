package kitchenpos.menu.tobe.domain.menu;

public interface MenuValidator {
    void validateMenuPrice(MenuProducts menuProducts, MenuPrice menuPrice);
    void validateMenuProductSize(MenuProducts menuProducts);
}
