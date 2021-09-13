package kitchenpos.menus.domain.tobe.domain.menu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

public class PriceTest {

    @DisplayName("메뉴의 가격을 생성할 수 있다.")
    @ValueSource(strings = {"0", "8000"})
    @ParameterizedTest
    void 생성(final BigDecimal price) {
        final Price actual = new Price(price);

        assertThat(actual).isEqualTo(new Price(price));
    }

    @DisplayName("메뉴의 가격은 null이 될 수 없다.")
    @NullSource
    @ParameterizedTest
    void null가격(final BigDecimal price) {
        assertThatThrownBy(() -> new Price(price))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격은 0원 이상이어야 한다.")
    @ValueSource(strings = "-8000")
    @ParameterizedTest
    void 마이너스가격(final BigDecimal price) {
        assertThatThrownBy(() -> new Price(price))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 가격 간 동등성을 확인할 수 있다.")
    @Test
    void 동등성() {
        final Price price1 = new Price(BigDecimal.valueOf(1000L));
        final Price price2 = new Price(BigDecimal.valueOf(1000L));

        assertThat(price1).isEqualTo(price2);
    }
}
