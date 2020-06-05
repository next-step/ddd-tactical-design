package kitchenpos.menus.tobe.domain.menu;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import kitchenpos.common.model.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MenuProductTest {

    @DisplayName("상품메뉴의 금액 합을 계산할수있다.")
    @ParameterizedTest
    @CsvSource({
        "1,10000,10000",
        "2,10000,20000"
    })
    void computePriceSum(long quantity, BigDecimal price, BigDecimal menuProductsPriceSum) {
        MenuProduct menuProduct = new MenuProduct(1L, quantity, price);

        assertThat(menuProduct.computePriceSum()).isEqualTo(Price.of(menuProductsPriceSum));
    }
}
