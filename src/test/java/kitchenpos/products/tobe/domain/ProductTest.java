package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.domain.vo.ProfanityName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.List;

import static kitchenpos.fixture.ProductFixture.FRIED_CHICKEN;
import static kitchenpos.fixture.ProductFixture.FRIED_CHICKEN_PRICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

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

    @DisplayName("상품을 생성할 수 있다.")
    @CsvSource(value = {"1:피자:15_000", "2:후라이드치킨:16_000", "3:양념치킨:16_000"}, delimiter = ':')
    @ParameterizedTest(name = "{index}. 상품 식별자: {0}, 이름: {1}, 상품 가격: {2}")
    void create(final long productId, final String displayedName, final long price) {
        final Product product = new Product(productId, displayedName, price);

        assertAll(
                () -> assertThat(product).isEqualTo(new Product(productId, displayedName, price)),
                () -> assertThat(product.getId()).isEqualTo(productId),
                () -> assertThat(product.getName()).isEqualTo(displayedName),
                () -> assertThat(product.getPrice()).isEqualTo(BigDecimal.valueOf(price))
        );
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

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @CsvSource(value = {"1:피자:15_000:14_000", "2:후라이드치킨:16_000:17_000", "3:양념치킨:16_000:16_000"}, delimiter = ':')
    @ParameterizedTest
    void changePrice(final long productId, final String displayedName, final long price, final long changedPrice) {
        final Product product = new Product(productId, displayedName, price);

        final Product actual = product.changePrice(changedPrice);

        assertThat(actual.getPrice()).isEqualTo(BigDecimal.valueOf(changedPrice));
    }
}
