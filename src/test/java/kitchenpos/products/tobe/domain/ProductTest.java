package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakeDisplayNameChecker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.UUID;

import static kitchenpos.products.exception.ProductExceptionMessage.PRODUCT_NAME_CONTAINS_PROFANITY;
import static kitchenpos.products.exception.ProductExceptionMessage.PRODUCT_PRICE_MORE_ZERO;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("상품테스트")
class ProductTest {

    @DisplayName("상품 생성 성공")
    @Test
    void create_success() {
        UUID id = UUID.randomUUID();
        Product product = Product.create(id, BigDecimal.valueOf(1_000L), "후라이드", new FakeDisplayNameChecker());
        assertThat(product)
                .isEqualTo(Product.create(id, BigDecimal.valueOf(1_000L), "후라이드", new FakeDisplayNameChecker()));
    }

    @DisplayName("메뉴의 식별자가 같으면 같은 상품이다.")
    @Test
    void create_success_only_equal_id() {
        UUID id = UUID.randomUUID();
        Product product = Product.create(id, BigDecimal.valueOf(1_000L), "후라이드", new FakeDisplayNameChecker());
        assertThat(product)
                .isEqualTo(Product.create(id, BigDecimal.valueOf(2_000L), "양념치킨", new FakeDisplayNameChecker()));
    }

    @DisplayName("상품이름에 비속어가 포함되면 예외를 반환한다.")
    @ValueSource(strings = {"비속어 후라이드", "욕설 후라이드"})
    @ParameterizedTest
    void create_failed_profanity(String input) {
        UUID id = UUID.randomUUID();
        assertThatThrownBy(() -> Product.create(id, BigDecimal.valueOf(1_000L), input, new FakeDisplayNameChecker()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PRODUCT_NAME_CONTAINS_PROFANITY);
    }

    @DisplayName("가격변경 성공")
    @Test
    void changePrice_success() {
        Product product = Product.create(UUID.randomUUID(), BigDecimal.valueOf(1_000L), "후라이드", new FakeDisplayNameChecker());

        product.changePrice(3000L);

        assertThat(product.getPrice()).isEqualTo(BigDecimal.valueOf(3_000L));
    }

    @DisplayName("변경하려는 금액이 0보다 작으면 예외를 반환한다.")
    @NullSource
    @ValueSource(longs = {-2L, -1L})
    @ParameterizedTest
    void changePrice_failed(Long input) {
        Product product = Product.create(UUID.randomUUID(), BigDecimal.valueOf(1_000L), "후라이드", new FakeDisplayNameChecker());
        assertThatThrownBy(() -> product.changePrice(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PRODUCT_PRICE_MORE_ZERO);
    }


}
