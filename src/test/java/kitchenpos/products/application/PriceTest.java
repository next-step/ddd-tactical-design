package kitchenpos.products.application;

import kitchenpos.products.tobe.domain.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PriceTest {

    @DisplayName("가격생성")
    @Test
    void 가격생성() {
        // when
        Price price = new Price(new BigDecimal(12_000L));

        // then
        assertThat(price.getPrice()).isEqualTo(new BigDecimal(12_000L));
    }

    @DisplayName("가격은 0원 이상이어야 한다.")
    @Test
    void 가격생성_음수() {
        // when then
        assertThatThrownBy(() -> new Price(new BigDecimal(-12_000L)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
