package kitchenpos.products.tobe.domain;

import kitchenpos.Fixtures;
import kitchenpos.common.domain.Price;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

public class ProductTest {

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @ValueSource(strings = "-1000")
    @Test
    void changePrice(final BigDecimal price) {
        //given
        Product before = Fixtures.tobe_product("아무상품", 150_000L);

        //when
        Product after = before.changePrice(Price.of(price));

        //then
        Assertions.assertThat(after.getPrice()).isEqualTo(price);
    }

    @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice_invalid_price(final BigDecimal price) {
        //given
        Product before = Fixtures.tobe_product("아무상품", 150_000L);

        //when
        //then
        Assertions.assertThatThrownBy(
                () -> before.changePrice(Price.of(price))
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
