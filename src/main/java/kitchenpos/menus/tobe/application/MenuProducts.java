package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.products.tobe.Money;
import kitchenpos.products.tobe.ProductPrices;

import java.util.Collections;
import java.util.List;

public class MenuProducts {

    private List<MenuProduct> menuProducts;

    public MenuProducts(List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public static MenuProducts of(List<MenuProduct> menuProductRequests, Money menuPrice, ProductPrices productPrices) {

        Money sumProductPrice = getSumProductPrice(menuProductRequests, productPrices);

        if (menuPrice.compareTo(sumProductPrice) > 0) {
            throw new IllegalArgumentException("메뉴에 포함된 상품의 가격이 상품의 가격보다 높습니다.");
        }

        return new MenuProducts(menuProductRequests);
    }

    public List<MenuProduct> value() {
        return Collections.unmodifiableList(menuProducts);
    }

    private static Money getSumProductPrice(List<MenuProduct> menuProductRequests, ProductPrices productPrices) {
        return menuProductRequests.stream()
                .map(it -> productPrices.getPrice(it.getProductId(), it.getQuantity()))
                .reduce((a, b) -> new Money(a.value().add(b.value())))
                .orElseThrow(IllegalArgumentException::new);
    }
}
