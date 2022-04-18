package kitchenpos.products.tobe.domain.test;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductPrice;
import kitchenpos.products.tobe.domain.Profanities;
import kitchenpos.products.tobe.domain.exception.NullAndNegativePriceException;
import kitchenpos.products.tobe.domain.fixture.FakeProfanities;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static kitchenpos.products.tobe.domain.fixture.NameFixture.PRODUCT_NAME;
import static kitchenpos.products.tobe.domain.fixture.PriceFixture.*;
import static kitchenpos.products.tobe.domain.fixture.ProductFixture.상품;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@DisplayName("상품 클래스")
class ProductTest {
    private static final Profanities profanities = new FakeProfanities();

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class registerProduct_메서드는{
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 상품_등록_요청이_올때{
            @Test
            @DisplayName("상품을 생성할 수 있다.")
            void test1() {
                assertThatCode(
                        () -> Product.registerProduct(null, PRODUCT_NAME, PRICE)
                ).doesNotThrowAnyException();
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class changeProduct_메서드는{
        @Nested
        @TestInstance(PER_CLASS)
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 상품_가격_변경_요청이_올때{

            private Stream<Arguments> provideBigDecimalForIsNotNullAndMinusValue() {
                return Stream.of(
                        Arguments.of((BigDecimal) null),
                        Arguments.of(MINUS_PRICE)
                );
            }

            @Test
            @DisplayName("가격을 변경할 수 있다.")
            void test5() {
                assertAll(
                        () -> assertDoesNotThrow(() -> 상품.changeProductPrice(CHANGE_PRICE)),
                        () -> assertThat(상품.getPrice())
                                .isEqualTo(new ProductPrice(CHANGE_PRICE))
                );
            }

            @ParameterizedTest
            @MethodSource("provideBigDecimalForIsNotNullAndMinusValue")
            @DisplayName("변경하려는 상품의 가격이 음수거나 존재하지 않으면 NullAndNegativePriceException 예외 발생")
            void test6(BigDecimal price) {
                assertThatThrownBy(
                        () -> 상품.changeProductPrice(price)
                ).isInstanceOf(NullAndNegativePriceException.class);
            }
        }
    }
}
