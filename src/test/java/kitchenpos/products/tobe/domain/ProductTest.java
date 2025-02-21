package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.domain.vo.ProfanityName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.List;

import static kitchenpos.fixture.ProductFixture.FRIED_CHICKEN;
import static kitchenpos.fixture.ProductFixture.FRIED_CHICKEN_PRICE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductTest {

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void createWithInvalidPrice(final BigDecimal price) {
        assertThatThrownBy(() -> new Product(null, FRIED_CHICKEN, price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 이름에는 비속어가 포함될 수 없다.")
    @ValueSource(strings = {"비속어", "욕설"})
    @ParameterizedTest
    void createWithProfanityName(final String profanityName) {
        final ProfanityName toBeProfanityName = new ProfanityName(List.of("비속어", "욕설"));
        assertThatThrownBy(() ->
                new Product(null, profanityName, toBeProfanityName, FRIED_CHICKEN_PRICE)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 이름은 1글자 이상이어야 한다.")
    @ValueSource(strings = {"", " "})
    @ParameterizedTest
    void createWithEmptyName(final String name) {
        assertThatThrownBy(() -> new Product(null, name, FRIED_CHICKEN_PRICE))
                .isInstanceOf(IllegalArgumentException.class);
    }




    @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePriceWithInvalidPrice(final BigDecimal price) {
        final Product product = new Product(null, FRIED_CHICKEN, FRIED_CHICKEN_PRICE);
        assertThatThrownBy(() -> product.changePrice(price))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
