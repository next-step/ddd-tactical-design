package kitchenpos.menus.tobe.domain.menu.domain;

import kitchenpos.common.tobe.domain.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static kitchenpos.menus.TobeFixtures.menuProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuProductTest {

    @DisplayName("정상적으로 생성되는 것 테스트.")
    @Test
    void create() {
        MenuProduct result = menuProduct();
        assertThat(result).isNotNull();
        assertAll(
                () -> assertThat(result.getProductId()).isEqualTo(1L),
                () -> assertThat(result.getQuantity()).isEqualTo(2)
        );
    }

    @DisplayName("정상적으로 가격 계산하는 것 테스트.")
    @Test
    void calculateTest() {
        // given
        MenuProduct sample = menuProduct();

        // when
        BigDecimal result = sample.calculateTest(new Price(BigDecimal.valueOf(16_000)));

        // then
        assertThat(result).isEqualTo(BigDecimal.valueOf(32_000));
    }
}