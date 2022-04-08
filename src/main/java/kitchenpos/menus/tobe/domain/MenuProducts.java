package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MenuProducts {
    private final String INVALID_PRODUCT = "메뉴 상품 정보가 존재하지 않습니다.";

    private final List<MenuProduct> values;

    private MenuProducts(List<MenuProduct> values) {
        if (Objects.isNull(values) || values.isEmpty()) {
            throw new IllegalArgumentException(INVALID_PRODUCT);
        }
        this.values = values;
    }

    public MenuProducts(MenuProduct... menuProducts) {
        this(Arrays.asList(menuProducts));
    }

    public BigDecimal totalPrice() {
        return values.stream()
                .map(MenuProduct::totalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
