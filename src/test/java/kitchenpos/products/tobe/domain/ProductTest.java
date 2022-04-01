package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("상품은")
class ProductTest {

    @Test
    @DisplayName("가격은 0원 이상 이어야 한다")
    void 상품의_가격은_0원_이상_이어야_한다() {

        assertThatIllegalArgumentException()
            .isThrownBy(() -> new Product(UUID.randomUUID(), "test", -1));
    }

    @Nested
    @DisplayName("변경할 가격이")
    class 변경할_가격이 {

        @DisplayName("0원 보다 작다면 변경 불가능하다.")
        @ParameterizedTest(name = "{0} 인 경우")
        @ValueSource(longs = {-1, -10})
        void 빵원_보다_작다면_변경_불가능하다(long value) {
            final Product product = new Product(UUID.randomUUID(), "test", 1000);

            assertThatIllegalArgumentException()
                .isThrownBy(() -> product.changePrice(new Money(value)));
        }

        @DisplayName("0원 이상이라면 변경 가능하다.")
        @ParameterizedTest(name = "{0} 인 경우")
        @ValueSource(longs = {0, 1})
        void 빵원_이상이라면_변경_가능하다(long value) {
            final Product product = new Product(UUID.randomUUID(), "test", 1000);

            assertDoesNotThrow(() -> product.changePrice(new Money(value)));
        }
    }
}
