package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    private static PurgomalumClient purgomalumClient;

    @BeforeAll
    static void beforeAll() {
        purgomalumClient = new FakePurgomalumClient();
    }

    @DisplayName("상품명 테스트")
    @Nested
    class TestProductName {
        @DisplayName("상품명은 비어있을 수 없다")
        @ParameterizedTest(name = "[{index}] productName={0}")
        @NullSource
        @ValueSource(strings = {""})
        void givenProductName_whenProductNameIsBlank_thenThrowException(final String productName) {
            assertThatThrownBy(() -> ProductName.create(productName, purgomalumClient))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("상품명은 비어있을 수 없습니다.");
        }

        @DisplayName("상품명에 비속어가 포함되어 있으면 등록할 수 없다")
        @ParameterizedTest(name = "[{index}] productName={0}")
        @ValueSource(strings = {"비속어", "욕설"})
        void givenProductName_whenProductNameContainsProfanity_thenThrowException(final String productName) {
            assertThatThrownBy(() -> ProductName.create(productName, purgomalumClient))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("상품명에 비속어가 포함되어 있습니다.");
        }
    }

    @DisplayName("상품 가격 테스트")
    @Nested
    class TestProductPrice {
        @DisplayName("상품 가격은 비어있을 수 없다")
        @ParameterizedTest(name = "[{index}] productPrice={0}")
        @NullSource
        void givenProductPrice_whenProductPriceIsNull_thenThrowException(final BigDecimal productPrice) {
            assertThatThrownBy(() -> ProductPrice.create(productPrice))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("상품 가격은 비어있을 수 없습니다.");
        }

        @DisplayName("상품 가격은 음수일 수 없다")
        @ParameterizedTest(name = "[{index}] productPrice={0}")
        @ValueSource(longs = {-1L, -10L, -1_000L})
        void givenProductPrice_whenProductPriceIsNegative_thenThrowException(final long productPrice) {
            assertThatThrownBy(() -> ProductPrice.create(BigDecimal.valueOf(productPrice)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("상품 가격은 음수일 수 없습니다.");
        }
    }

    @DisplayName("상품 테스트")
    @Nested
    class TestProduct {
        @DisplayName("상품명과 상품 가격이 올바르면 상품을 등록할 수 있다")
        @ParameterizedTest(name = "[{index}] productName={0}")
        @ValueSource(strings = {"후라이드", "양념치킨", "후라이드", "양념치킨"})
        void givenProductNameAndProductPrice_whenProductNameAndProductPriceIsValid_thenCreateProduct(final String productName) {
            // Given
            final BigDecimal productPrice = BigDecimal.valueOf(16_000L);

            // When
            final Product product = Product.create(ProductName.create(productName, purgomalumClient), ProductPrice.create(productPrice));

            // Then
            assertThat(product).isNotNull();
            assertThat(product.getId()).isNotNull();
            assertThat(product.getName()).isEqualTo(productName);
            assertThat(product.getPrice()).isEqualTo(productPrice);
        }
    }

}
