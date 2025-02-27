package kitchenpos.menu.tobe.domain.menu;

public interface ProductClient {
    void validateMenuPrice(MenuProducts menuProducts, MenuPrice menuPrice);
    void validateMenuProductSize(MenuProducts menuProducts);
}
