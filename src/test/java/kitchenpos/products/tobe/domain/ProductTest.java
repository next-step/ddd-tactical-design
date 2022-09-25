package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.exception.InvalidProductNameException;
import kitchenpos.products.tobe.domain.exception.InvalidProductPriceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        purgomalumClient = new FakePurgomalumClient();
    }

    @DisplayName("상품을 생성한다.")
    @Nested
    class CreateTest {

        @DisplayName("성공")
        @Test
        void success() {
            final Product product = Product.create("치킨", 10_000L, purgomalumClient);

            assertAll(
                    () -> assertThat(product.id()).isNotNull(),
                    () -> assertThat(product.name()).isEqualTo("치킨"),
                    () -> assertThat(product.price()).isEqualTo(BigDecimal.valueOf(10_000L))
            );
        }

        @ParameterizedTest(name = "상품명에는 비어있거나, 비속어가 포함되면 안된다. name={0}")
        @NullSource
        @ValueSource(strings = {"비속어", "욕설"})
        void error_1(final String name) {
            assertThatThrownBy(() -> Product.create(name, 10_000L, purgomalumClient))
                    .isInstanceOf(InvalidProductNameException.class);
        }

        @ParameterizedTest(name = "상품 가격은 0원 이상이여야 한다. price={0}")
        @NullSource
        @ValueSource(longs = {-10_000L, -20_000L})
        void error_2(final Long price) {
            assertThatThrownBy(() -> Product.create("상품", price, purgomalumClient))
                    .isInstanceOf(InvalidProductPriceException.class);
        }
    }
}
