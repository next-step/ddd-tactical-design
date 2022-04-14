package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class QuantityTest {

    @ParameterizedTest
    @ValueSource(longs = { 0L, -1L, -1000L })
    @DisplayName("재고가 0개 이하면 재고 생성 실패")
    void createQuantityFail(long quantity) {
        assertThatIllegalArgumentException().isThrownBy(() -> new Quantity(quantity));
    }

    @Test
    @DisplayName("재고 생성 성공")
    void createQuantitySuccess() {
        // given
        long expected = 5L;

        // when
        Quantity actual = new Quantity(5L);

        // then
        assertThat(actual.value()).isEqualTo(expected);
    }
}
