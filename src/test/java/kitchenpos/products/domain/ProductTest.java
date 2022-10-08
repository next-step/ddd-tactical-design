package kitchenpos.products.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import kitchenpos.Fixtures;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ProductTest {

    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setup() {
        purgomalumClient = new FakePurgomalumClient();
    }

    @Test
    @DisplayName("상품 생성")
    void constructor() {
        final Product expected = Fixtures.product();
        assertThat(expected).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("상품의 이름은 필수이다")
    @NullSource
    void constructor_with_null_name(final String name) {
        assertThatThrownBy(() -> new Product(name, BigDecimal.ONE, purgomalumClient))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("상품의 이름은 비속어를 포함할 수 없다")
    @ValueSource(strings = {"비속어", "욕설"})
    void verify_name(final String name) {
        assertThatThrownBy(() -> new Product(name, BigDecimal.ONE, purgomalumClient))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("상품의 가격은 필수이다")
    @NullSource
    void constructor_with_null_price(final BigDecimal value) {
        assertThatThrownBy(() -> new Product("상품", value, purgomalumClient))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("상품의 가격은 0보다 커야한다")
    @ValueSource(longs = { -1 })
    void constructor_with_null_price(final Long value) {
        assertThatThrownBy(() -> new Product("상품", BigDecimal.valueOf(value), purgomalumClient))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상품의 가격 변경이 가능하다")
    void change_price() {
        final Product expected = new Product("상품", BigDecimal.ONE, purgomalumClient);

        final BigDecimal changePrice = BigDecimal.TEN;
        expected.changePrice(changePrice);

        assertThat(expected.getPrice().value()).isEqualTo(changePrice);
    }

    @ParameterizedTest
    @DisplayName("가격을 변경할때 가격은 음수가 될 수 없다")
    @ValueSource(longs = { -1 })
    void change_price_with_negative_value(final Long value) {
        final Product expected = new Product("상품", BigDecimal.TEN, purgomalumClient);

        assertThatThrownBy(() -> expected.changePrice(BigDecimal.valueOf(value)))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
