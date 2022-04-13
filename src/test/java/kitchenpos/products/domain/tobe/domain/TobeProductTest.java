package kitchenpos.products.domain.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static kitchenpos.Fixtures.tobeProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TobeProductTest {

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice_success() {
        //given
        final TobeProduct 상품 = tobeProduct("후라이드", BigDecimal.valueOf(16_000L));
        final BigDecimal 가격 = BigDecimal.valueOf(15_000L);
        //when
        상품.changePrice(가격);

        //then
        assertThat(상품.getPrice().getValue()).isEqualTo(가격);
    }

    @DisplayName("상품가격정책에 부합하지 않은 가격으로는 변경할 수 없다.")
    @ValueSource(strings = "-1")
    @ParameterizedTest
    void changePrice_fail_policy_violation(final BigDecimal price) {
        //given
        final TobeProduct 상품 = tobeProduct("후라이드", BigDecimal.valueOf(16_000L));

        //when&then
        assertThatThrownBy(() -> 상품.changePrice(price))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
