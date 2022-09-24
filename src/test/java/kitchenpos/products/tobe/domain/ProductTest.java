package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.vo.ProductName;
import kitchenpos.products.tobe.domain.vo.ProductPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ProductTest {

    private PurgomalumClient profanity;

    @BeforeEach
    void setUp() {
        profanity = new FakePurgomalumClient();
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        final Product product = createProduct("후라이드");

        assertThat(product).isNotNull();
        assertAll(
                () -> assertThat(product.getId()).isNotNull(),
                () -> assertThat(product.getName()).isEqualTo(new ProductName("후라이드", profanity)),
                () -> assertThat(product.getPrice()).isEqualTo(new ProductPrice(BigDecimal.ZERO))
        );
    }

    private Product createProduct(String name) {
        return new Product(name, profanity, BigDecimal.ZERO);
    }

    @DisplayName("상품 에러 케이스")
    @Nested
    class ErrorCaseTest {

        @DisplayName("이름이 올바르지 않으면 등록할 수 없다.")
        @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
        @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
        @NullSource
        void create1(final String name) {
            assertThatThrownBy(() -> createProduct(name))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("가격이 올바르지 않으면 등록할 수 없다.")
        @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
        @ValueSource(strings = "-1000")
        @NullSource
        void create2(final BigDecimal price) {
            assertThatThrownBy(() -> new Product("후라이드", profanity, price))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("가격이 올바르지 않으면 변경할 수 없다.")
        @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
        @ValueSource(strings = "-1000")
        @NullSource
        void changePrice(final BigDecimal price) {
            final Product product = createProduct("후라이드");

            assertThatThrownBy(() -> product.changePrice(price))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
