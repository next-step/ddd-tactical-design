package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
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

    private PurgomalumClient purgomalumClient;
    private DisplayedName displayedName;

    @BeforeEach
    void setUp() {
        purgomalumClient = new FakePurgomalumClient();
        displayedName = DisplayedName.valueOf("치킨", purgomalumClient);
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

        @DisplayName("성공")
        @Test
        void success() {
            final Product product = Product.create(displayedName, 10_000L);

            product.changePrice(20_000L);

            assertThat(product.price()).isEqualTo(Price.valueOf(20_000L));
        }

        @DisplayName("가격 정보가 있어야 한다.")
        @Test
        void error_1() {
            final Product product = Product.create(displayedName, 10_000L);

            assertThatThrownBy(() -> product.changePrice(null))
                    .isInstanceOf(InvalidProductPriceException.class);
        }
    }
}
