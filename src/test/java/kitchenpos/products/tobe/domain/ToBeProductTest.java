package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static kitchenpos.fixture.ProductFixture.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ToBeProductTest {

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void createWithInvalidPrice(final BigDecimal price) {
        assertThatThrownBy(() -> new ToBeProduct(UUID.randomUUID(), FRIED_CHICKEN, price))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @DisplayName("상품의 이름에는 비속어가 포함될 수 없다.")
    @ValueSource(strings = {"비속어", "욕설"})
    @ParameterizedTest
    void createWithProfanityName(final String profanityName) {
        final ProfanityName toBeProfanityName = new ProfanityName(List.of("비속어", "욕설"));
        assertThatThrownBy(() ->
                new ToBeProduct(UUID.randomUUID(), profanityName, toBeProfanityName, FRIED_CHICKEN_PRICE)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 이름은 1글자 이상이어야 한다.")
    @ValueSource(strings = {"", " "})
    @ParameterizedTest
    void createWithEmptyName(final String name) {
        assertThatThrownBy(() -> new ToBeProduct(UUID.randomUUID(), name, FRIED_CHICKEN_PRICE))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePriceWithInvalidPrice(final BigDecimal price) {
        final ToBeProduct product = new ToBeProduct(UUID.randomUUID(), FRIED_CHICKEN, FRIED_CHICKEN_PRICE);
        assertThatThrownBy(() -> product.changePrice(price))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
