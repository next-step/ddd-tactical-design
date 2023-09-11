package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ProductPriceTest {

    @DisplayName("가격은 음수가 될수 없다")
    @ParameterizedTest
    @ValueSource(longs = {-1, -100, -1_000, -10_000, -100_000, -1_000_000})
    void test1(long price) {
        assertThatThrownBy(
            () -> new ProductPrice(BigDecimal.valueOf(price))
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("가격은 음수가 될수 없습니다");
    }

}
