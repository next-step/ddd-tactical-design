package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakeProfanity;
import kitchenpos.products.tobe.domain.exception.InvalidProductNameException;
import kitchenpos.products.tobe.domain.exception.InvalidProductPriceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private Profanity profanity;
    private DisplayedName displayedName;

    @BeforeEach
    void setUp() {
        profanity = new FakeProfanity();
        displayedName = DisplayedName.valueOf("치킨", profanity);
    }

    @DisplayName("상품을 생성한다.")
    @Nested
    class CreateTest {

        @DisplayName("성공")
        @Test
        void success() {
            final Product product = Product.create(displayedName, 10_000L);

            assertAll(
                    () -> assertThat(product.id()).isNotNull(),
                    () -> assertThat(product.displayedName().value()).isEqualTo("치킨"),
                    () -> assertThat(product.price()).isEqualTo(Price.valueOf(10_000L))
            );
        }

        @DisplayName("이름 정보가 있어야 한다.")
        @Test
        void error_1() {
            assertThatThrownBy(() -> Product.create(null, 10_000L))
                    .isInstanceOf(InvalidProductNameException.class);
        }

        @DisplayName("가격 정보가 있어야 한다.")
        @Test
        void error_2() {
            assertThatThrownBy(() -> Product.create(displayedName, null))
                    .isInstanceOf(InvalidProductPriceException.class);
        }
    }

    @DisplayName("상품의 가격을 변경한다.")
    @Nested
    class ChangePriceTest {

        private PricePolicy pricePolicy;

        @BeforeEach
        void setUp() {
            pricePolicy = new DummyPricePolicy();
        }

        @DisplayName("성공")
        @Test
        void success() {
            final Product product = Product.create(displayedName, 10_000L);

            product.changePrice(20_000L, pricePolicy);

            assertThat(product.price()).isEqualTo(Price.valueOf(20_000L));
        }

        @DisplayName("가격 정보가 있어야 한다.")
        @Test
        void error_1() {
            final Product product = Product.create(displayedName, 10_000L);

            assertThatThrownBy(() -> product.changePrice(null, pricePolicy))
                    .isInstanceOf(InvalidProductPriceException.class);
        }
    }
}
