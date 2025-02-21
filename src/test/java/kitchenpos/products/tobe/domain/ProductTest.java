package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.domain.exception.DisplayedNameContainsProfanityException;
import kitchenpos.products.tobe.domain.exception.InvalidDisplayedNameException;
import kitchenpos.products.tobe.domain.vo.ProfanityName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ProductTest {

    private static final String PRODUCT_DEFAULT_NAME = "후라이드치킨";
    private static final long PRODUCT_DEFAULT_PRICE = 16_000L;

    @DisplayName("상품의 이름에는 비속어가 포함될 수 없다.")
    @ValueSource(strings = {"비속어", "욕설"})
    @ParameterizedTest(name = "{index}. 상품 이름: {0}")
    void createWithProfanityName(final String displayedName) {
        final ProfanityName profanityName = new ProfanityName(List.of("비속어", "욕설"));
        assertThatThrownBy(() ->
                new Product(null, displayedName, profanityName, PRODUCT_DEFAULT_PRICE)
        ).isInstanceOf(DisplayedNameContainsProfanityException.class);
    }

    @DisplayName("상품의 이름은 1글자 이상이어야 한다.")
    @ValueSource(strings = {"", " "})
    @ParameterizedTest(name = "{index}. 상품 이름: {0}")
    void createWithEmptyName(final String displayedName) {
        assertThatThrownBy(() -> new Product(null, displayedName, PRODUCT_DEFAULT_PRICE))
                .isInstanceOf(InvalidDisplayedNameException.class);
    }

    @DisplayName("상품의 가격이 올바르지 않으면 생성할 수 없다.")
    @ValueSource(longs = -1000)
    @ParameterizedTest(name = "{index}. 상품 가격: {0}")
    void createWithInvalidPrice(final Long price) {
        assertThatThrownBy(() -> new Product(null, PRODUCT_DEFAULT_NAME, price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품을 생성할 수 있다.")
    @CsvSource(value = {"1:피자:15_000", "2:후라이드치킨:16_000", "3:양념치킨:16_000"}, delimiter = ':')
    @ParameterizedTest(name = "{index}. 상품 식별자: {0}, 상품 이름: {1}, 상품 가격: {2}")
    void create(final long productId, final String displayedName, final Long price) {
        final Product product = new Product(productId, displayedName, price);

        assertAll(
                () -> assertThat(product).isEqualTo(new Product(productId, displayedName, price)),
                () -> assertThat(product.getId()).isEqualTo(productId),
                () -> assertThat(product.getName()).isEqualTo(displayedName),
                () -> assertThat(product.getPrice()).isEqualTo(price)
        );
    }

    @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(longs = -1000)
    @ParameterizedTest(name = "{index}. 상품 가격: {0}")
    void changePriceWithInvalidPrice(final Long price) {
        final Product product = new Product(null, PRODUCT_DEFAULT_NAME, PRODUCT_DEFAULT_PRICE);
        assertThatThrownBy(() -> product.changePrice(price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @CsvSource(value = {"1:피자:15_000:14_000", "2:후라이드치킨:16_000:17_000", "3:양념치킨:16_000:16_000"}, delimiter = ':')
    @ParameterizedTest(name = "{index}. 상품 식별자: {0}, 상품 이름: {1}, 상품 가격: {2}, 변경된 상품 가격: {3}")
    void changePrice(final long productId, final String displayedName, final long price, final long changedPrice) {
        final Product product = new Product(productId, displayedName, price);

        final Product actual = product.changePrice(changedPrice);

        assertThat(actual.getPrice()).isEqualTo(changedPrice);
    }
}
