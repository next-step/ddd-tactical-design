package kitchenpos.products.tobe.domain.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import kitchenpos.common.tobe.domain.DisplayedName;
import kitchenpos.common.tobe.domain.Price;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProductTest {

    private static final String DEFAULT_NAME = "상품1";
    private static final long DEFAULT_PRICE = 10000L;
    private final PurgomalumClient purgomalumClient = new FakePurgomalumClient();

    @DisplayName("상품의 가격이 음수이면 Product를 생성할 수 없다")
    @ParameterizedTest
    @ValueSource(longs = {-1, -1000L, Long.MIN_VALUE})
    void wrongPrice(final long price) {
        assertThatThrownBy(
            () -> new Product(new DisplayedName(DEFAULT_NAME, purgomalumClient), new Price(price))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이름과 가격이 올바르면 Product를 생성할 수 있다")
    @ParameterizedTest
    @ValueSource(strings = {"상품1", "상품2"})
    void ok(final String name) {
        final Product product = new Product(new DisplayedName(name, purgomalumClient), new Price(DEFAULT_PRICE));

        assertThat(product).isNotNull();
    }

    @DisplayName("가격을 변경할 수 있다")
    @ParameterizedTest
    @ValueSource(longs = {0, 1, 1000, Long.MAX_VALUE})
    void changePrice(final long price) {
        final Product product = new Product(new DisplayedName(DEFAULT_NAME, purgomalumClient), new Price(DEFAULT_PRICE));
        final Price anotherPrice = new Price(price);

        final Product changedProduct = product.changePrice(anotherPrice);

        assertThat(changedProduct.getPrice()).isEqualTo(anotherPrice);
    }

}
