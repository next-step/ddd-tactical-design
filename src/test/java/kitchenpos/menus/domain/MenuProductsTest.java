package kitchenpos.menus.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static kitchenpos.Fixtures.menuProduct;
import static org.assertj.core.api.Assertions.assertThat;

class MenuProductsTest {

    @Test
    @DisplayName("메뉴상품의 가격의 합 정상 동작")
    void getTotalPriceTest() {
        List<MenuProduct> dummyMenuProducts = new ArrayList<>();
        dummyMenuProducts.add(menuProduct());
        dummyMenuProducts.add(menuProduct());

        MenuProducts menuProducts = new MenuProducts(dummyMenuProducts);
        assertThat(menuProducts.getTotalPrice()).isEqualTo(BigDecimal.valueOf(64_000));
    }
}