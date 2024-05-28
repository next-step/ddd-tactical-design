package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Nested
    @DisplayName("상품을 등록할 수 있다.")
    class Creation {

        @Test
        @DisplayName("상품이 올바르면 등록할 수 있다.")
        void createTest() {
            final var profanityCheckProvider = new FakeProfanityCheckProvider("욕설", "비속어");
            ProductFixtures.create("상품명", 10_000, profanityCheckProvider);
        }

        @Test
        @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
        void cannotCreateInvalidPrice() {
            assertThatThrownBy(() -> ProductFixtures.withPrice(-1_000))
                .isInstanceOf(PriceLessThanZeroException.class);
        }

        @Test
        @DisplayName("상품의 이름이 올바르지 않으면 등록할 수 없다.")
        void cannotCreateInvalidName() {
            final var profanityCheckProvider = new FakeProfanityCheckProvider("욕설", "비속어");
            assertThatThrownBy(() -> ProductFixtures.withName("욕설", profanityCheckProvider))
                .isInstanceOf(DisplayedNameProfanityIncludedException.class);
        }
    }

    @Nested
    @DisplayName("상품의 가격을 변경할 수 있다.")
    class PriceChance {

        @DisplayName("상품의 가격이 올바르면 변경할 수 있다.")
        @Test
        void changePrice() {
            final Product product = ProductFixtures.create();
            product.changePrice(BigDecimal.valueOf(10_000));

            assertThat(product.getPrice()).isEqualTo(Price.create(BigDecimal.valueOf(10_000)));
        }

        @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
        @Test
        void cannotChangeInvalidPrice() {
            final Product product = ProductFixtures.create();
            assertThatThrownBy(() -> product.changePrice(BigDecimal.valueOf(-1_000)))
                .isInstanceOf(PriceLessThanZeroException.class);
        }
    }
}