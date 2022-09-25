package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.domain.exception.InvalidProductPriceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class PriceTest {

    @DisplayName("가격을 생성한다.")
    @Test
    void valueOf() {
        final Price price = Price.valueOf(10_000L);

        assertAll(
                () -> assertThat(price).isEqualTo(Price.valueOf(10_000L)),
                () -> assertThat(price.value()).isEqualTo(BigDecimal.valueOf(10_000L))
        );
    }

    @ParameterizedTest(name = "가격은 0원 이상이여야 한다. price={0}")
    @NullSource
    @ValueSource(longs = {-10_000L, -20_000L})
    void invalid_price(final Long price) {
        assertThatThrownBy(() -> Price.valueOf(price))
                .isInstanceOf(InvalidProductPriceException.class);
    }
}
