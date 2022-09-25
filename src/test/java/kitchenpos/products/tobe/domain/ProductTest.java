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
                    () -> assertThat(product.price()).isEqualTo(Price.valueOf(10_000L))
            );
        }

        @ParameterizedTest(name = "상품명에는 비어있거나, 비속어가 포함되면 안된다. name={0}")
        @NullSource
        @ValueSource(strings = {"비속어", "욕설"})
        void error_1(final String name) {
            assertThatThrownBy(() -> Product.create(name, 10_000L, purgomalumClient))
                    .isInstanceOf(InvalidProductNameException.class);
        }

        @DisplayName("가격 정보가 있어야 한다.")
        @Test
        void error_2() {
            assertThatThrownBy(() -> Product.create("상품", null, purgomalumClient))
                    .isInstanceOf(InvalidProductPriceException.class);
        }
    }

    @DisplayName("상품의 가격을 변경한다.")
    @Nested
    class ChangePriceTest {

        @DisplayName("성공")
        @Test
        void success() {
            final Product product = Product.create("치킨", 10_000L, purgomalumClient);

            product.changePrice(20_000L);

            assertThat(product.price()).isEqualTo(Price.valueOf(20_000L));
        }

        @DisplayName("가격 정보가 있어야 한다.")
        @Test
        void error_1() {
            final Product product = Product.create("치킨", 10_000L, purgomalumClient);

            assertThatThrownBy(() -> product.changePrice(null))
                    .isInstanceOf(InvalidProductPriceException.class);
        }
    }
}
