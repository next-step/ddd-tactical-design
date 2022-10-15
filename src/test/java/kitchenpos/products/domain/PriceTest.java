package kitchenpos.products.domain;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PriceTest {

    @DisplayName("가격은 0원 미만이면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -3})
    void price(int price) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Price.from(price)
        );
    }

    @DisplayName("가격은 비어있으면 예외가 발생한다.")
    @Test
    void price2() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Price(null)
                );
    }
}
