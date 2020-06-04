package kitchenpos.menus.tobe.domain.menu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.Arrays;
import kitchenpos.common.model.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MenuTest {

    @DisplayName("메뉴에 속한 상품금액의 합을 계산할수있다.")
    @ParameterizedTest
    @CsvSource({
        "1,10000,10000",
        "2,10000,20000"
    })
    void computeMenuProductsPriceSum(long quantity, BigDecimal price,
        BigDecimal menuProductsPriceSum) {
        MenuProduct menuProduct = new MenuProduct(1L, quantity, price);

        Menu menu = Menu.of("후라이드+후라이드", BigDecimal.valueOf(100), 1L,
            new MenuProducts(Arrays.asList(menuProduct)));

        assertThat(menu.computeMenuProductsPriceSum()).isEqualTo(Price.of(menuProductsPriceSum));
    }

    @DisplayName("메뉴의 가격이 메뉴상품의 가격의 합보다 크면 예외가 발생한다.")
    @Test
    void menuPrice() {
        MenuProduct menuProduct = new MenuProduct(1L, 1, BigDecimal.valueOf(10000));

        assertThatThrownBy(() ->
            Menu.of("후라이드+후라이드", BigDecimal.valueOf(10001), 1L,
                new MenuProducts(Arrays.asList(menuProduct)))
        )
            .isInstanceOf(IllegalArgumentException.class);
    }
}
