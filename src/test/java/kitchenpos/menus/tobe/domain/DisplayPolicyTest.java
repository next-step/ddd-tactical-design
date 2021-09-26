package kitchenpos.menus.tobe.domain;

import kitchenpos.fixture.MenuFixture;
import kitchenpos.fixture.ProductFixture;
import kitchenpos.products.tobe.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

class DisplayPolicyTest {
    private final UUID productId1 = randomUUID();
    private final UUID productId2 = randomUUID();
    private final long price1 = 1000L;
    private final long price2 = 2000L;
    private final Product product1 = ProductFixture.상품(productId1, price1);
    private final Product product2 = ProductFixture.상품(productId2, price2);


    @ParameterizedTest
    @ValueSource(strings = {"0", "-1000"})
    @DisplayName("Menu의 가격 (price)이 MenuProducts의 금액 합(amount) 정책에 부합한지(적거나 같은지) 확인할 수 있다.")
    void isValidAmount_bigger(final long value) {
        final MenuProducts menuProducts = MenuFixture.금액이불러와진_메뉴상품목록(product1, product2);
        final Price price = new Price(price1 + price2 + value);

        boolean expected = DisplayPolicy.canDisplay(menuProducts, price);

        assertThat(expected).isTrue();
    }


    @ParameterizedTest
    @ValueSource(strings = {"1000000"})
    @DisplayName("Menu의 가격 (price)이 MenuProducts의 금액 합(amount) 정책에 부합하지 않는지(큰지) 확인할 수 있다.")
    void isValidAmount_sameAndLower(final long value) {
        final MenuProducts menuProducts = MenuFixture.금액이불러와진_메뉴상품목록(product1, product2);
        final Price price = new Price(price1 + price2 + value);

        boolean expected = DisplayPolicy.canDisplay(menuProducts, price);

        assertThat(expected).isFalse();
    }
}
