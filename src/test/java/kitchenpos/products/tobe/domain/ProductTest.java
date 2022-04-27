package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.domain.exception.NullAndNegativePriceException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static kitchenpos.products.fixture.NameFixture.간장반양념반;
import static kitchenpos.products.fixture.PriceFixture.만원;
import static kitchenpos.products.fixture.PriceFixture.이만원;
import static kitchenpos.products.fixture.ProductFixture.교촌치킨;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@DisplayName("상품 클래스")
class ProductTest {

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class registerProduct_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 상품_등록_요청이_올때 {
            @Test
            @DisplayName("상품을 생성할 수 있다.")
            void test1() {
                assertThatCode(
                    () -> new Product(null, 간장반양념반, 만원)
                ).doesNotThrowAnyException();
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class changeProduct_메서드는 {
        @Nested
        @TestInstance(PER_CLASS)
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 상품_가격_변경_요청이_올때 {

            @Test
            @DisplayName("가격을 변경할 수 있다.")
            void test5() {
                assertAll(
                    () -> assertDoesNotThrow(() -> 교촌치킨.changePrice(이만원)),
                    () -> assertThat(교촌치킨.getPrice())
                        .isEqualTo(new ProductPrice(이만원))
                );
            }

            @ParameterizedTest
            @ValueSource(strings = "-10000")
            @DisplayName("변경하려는 상품의 가격이 음수거나 존재하지 않으면 NullAndNegativePriceException 예외 발생")
            void test6(int price) {
                assertThatThrownBy(
                    () -> 교촌치킨.changePrice(price)
                ).isInstanceOf(NullAndNegativePriceException.class);
            }
        }
    }
}
