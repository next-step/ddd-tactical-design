package kitchenpos.menus.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
import kitchenpos.common.model.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MenuTest {

    @DisplayName("메뉴에 속한 상품금액의 합을 계산할수있다.")
    @ParameterizedTest
    @CsvSource({
            "1,10000,10000",
            "2,10000,20000"
    })
    void computeMenuProductsPriceSum(long quantity, BigDecimal price, BigDecimal menuProductsPriceSum) {
        MenuProduct menuProduct = new MenuProduct(1L, quantity, price);

        Menu menu = new Menu("후라이드+후라이드", BigDecimal.valueOf(100),  1L,  new MenuProducts(Arrays.asList(menuProduct)));

        assertThat(menu.computeMenuProductsPriceSum()).isEqualTo(Price.of(menuProductsPriceSum));
    }
}
