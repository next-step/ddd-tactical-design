package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PriceTest {

    @DisplayName("가격은 0원 미만이면 안된다.")
    @Test
    void price() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Price(-3)
        );
    }
}
