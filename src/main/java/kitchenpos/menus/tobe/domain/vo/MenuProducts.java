package kitchenpos.menus.tobe.domain.vo;

import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.exception.InvalidMenuProductEmptyException;
import kitchenpos.menus.tobe.domain.exception.MenuProductCountMismatchException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuProducts {
    private final List<MenuProduct> products;

    public MenuProducts(MenuProduct... products) {
        this(Arrays.stream(products).toList());
    }

    public MenuProducts(List<MenuProduct> products) {
        if (products == null | products.isEmpty()) {
            throw new InvalidMenuProductEmptyException("메뉴에 포함된 상품은 한개 이상 존재해야 합니다.");
        }

        long distinctCount = products.stream()
                .map(MenuProduct::getProductId)
                .distinct()
                .count();
        if (products.size() != distinctCount) {
            throw new MenuProductCountMismatchException("메뉴에 등록된 상품 개수와 실제 상품 개수가 일치해야 합니다.");
        }

        this.products = new ArrayList<>(products);
    }

    public int total() {
        return products.stream()
                .mapToInt(MenuProduct::amount)
                .sum()
                ;
    }

    public List<MenuProduct> getProducts() {
        return products;
    }
}
