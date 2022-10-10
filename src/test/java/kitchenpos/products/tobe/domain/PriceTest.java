package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PriceTest {

    @DisplayName("가격은 0원 이상이어야 한다")
    @Test
    void price() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Price.from(-3)
        );
    }
}
