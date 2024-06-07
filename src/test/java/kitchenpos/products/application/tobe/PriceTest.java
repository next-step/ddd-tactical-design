package kitchenpos.products.application.tobe;

import kitchenpos.products.tobe.domain.vo.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PriceTest {
    @DisplayName("가격은 0원 이하가 될 수 없다")
    @Test
    void setWrongValueTest() throws Exception {
        assertThatThrownBy(()->new Price(0))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
