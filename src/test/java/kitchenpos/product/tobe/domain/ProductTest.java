package kitchenpos.product.tobe.domain;

import kitchenpos.exception.IllegalPriceException;
import kitchenpos.fixture.tobe.ProductFixture;
import kitchenpos.infra.FakePurgomalumClient;
import kitchenpos.infra.PurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static kitchenpos.MoneyConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductTest {
    private final PurgomalumClient purgomalumClient = new FakePurgomalumClient();

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void success() {
        final var product = ProductFixture.createProduct("상품이름", 만원);

        assertThat(product).isNotNull();
    }

    @DisplayName("싱픔의 가격을 변경할 수 있다.")
    @Test
    void change() {
        final var product = ProductFixture.createProduct("상품이름", 만원);
        product.updateProductPrice(BigDecimal.valueOf(오천원));

        assertThat(product.getPrice()).isEqualTo(BigDecimal.valueOf(오천원));
    }

    @ParameterizedTest
    @ValueSource(longs = {-1_000L, -10_000L, -1L})
    @DisplayName("[실패] 0원보다 적게 입력하는 경우 변경할 수 없다.")
    void fail1(final long input) {
        final var product = ProductFixture.createProduct("상품이름", 만원);

        assertThrows(IllegalPriceException.class, () -> product.updateProductPrice(BigDecimal.valueOf(input)));
    }
}
