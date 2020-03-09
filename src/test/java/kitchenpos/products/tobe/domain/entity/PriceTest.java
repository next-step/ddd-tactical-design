package kitchenpos.products.tobe.domain.entity;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class PriceTest {

    @DisplayName("가격 객체를 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"0", "1000"})
    void create(final BigDecimal price) {
        assertThatCode(() -> Price.valueOf(price)).doesNotThrowAnyException();
    }

    @DisplayName("가격이 유효하지 않으면 생성할 수 없다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "-1000")
    void canNotCreate(final BigDecimal price) {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> Price.valueOf(price));
    }
}
