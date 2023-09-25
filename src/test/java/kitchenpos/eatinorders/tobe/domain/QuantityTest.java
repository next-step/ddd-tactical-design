package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("매장주문 수량 테스트")
class QuantityTest {

    @DisplayName("매장주문 수량은 양수, 음수 모두 생성할 수 있다.")
    @ValueSource(ints = {-2, -1, 0, 1, 2})
    @ParameterizedTest
    void create(Integer input) {
        assertThat(Quantity.create(input)).isEqualTo(Quantity.create(input));
    }

}