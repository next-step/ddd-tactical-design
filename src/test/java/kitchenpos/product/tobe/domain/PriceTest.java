package kitchenpos.product.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

public class PriceTest {
    @DisplayName("가격은 0원 이상이어야 한다")
    @Test
    void test1() {
        final Price price = new Price(BigDecimal.ZERO);

        assertThat(price.getValue()).isEqualTo(BigDecimal.ZERO);
    }

    @DisplayName("가격은 비어 있으면 안된다")
    @Test
    void test2() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Price(null));
    }

    @DisplayName("가격은 0원 미만이면 안된다")
    @Test
    void test3() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Price(BigDecimal.valueOf(-1)));
    }
}
