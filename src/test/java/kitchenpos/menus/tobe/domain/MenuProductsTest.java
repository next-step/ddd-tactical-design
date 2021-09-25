package kitchenpos.menus.tobe.domain;

import kitchenpos.fixture.MenuFixture;
import kitchenpos.fixture.ProductFixture;
import kitchenpos.products.tobe.domain.Product;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static kitchenpos.Fixtures.INVALID_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("메뉴 상품 목록(MenuProducts)은")
class MenuProductsTest {
    private final UUID productId1 = randomUUID();
    private final UUID productId2 = randomUUID();
    private final long price1 = 1000L;
    private final long price2 = 2000L;
    private final Product product1 = ProductFixture.상품(productId1, price1);
    private final Product product2 = ProductFixture.상품(productId2, price2);
    private final List<MenuProduct> menuProductList = Arrays.asList(MenuFixture.메뉴상품(productId1), MenuFixture.메뉴상품(productId2));

    @Test
    @DisplayName("생성할 수 있다.")
    void create() {
        final MenuProducts menuProducts = new MenuProducts(menuProductList);
        assertThat(menuProducts).isEqualTo(new MenuProducts(menuProductList));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("0개 이하의 MenuProduct는 IllegalArgumentException이 발생한다.")
    void create(List<MenuProduct> menuProducts) {
        ThrowableAssert.ThrowingCallable throwingCallable = () -> new MenuProducts(menuProducts);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(throwingCallable);
    }

    @Test
    @DisplayName("productId 목록을 반환할 수 있다.")
    void getProductIds() {
        final MenuProducts menuProducts = new MenuProducts(menuProductList);

        final List<UUID> productIds = menuProducts.getProductIds();

        assertThat(productIds).contains(productId1, productId2);
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "-1000"})
    @DisplayName("Menu의 가격 (Amount)이 MenuProducts의 금액 정책에 부합한지(적거나 같은지) 확인할 수 있다.")
    void isValidAmount_bigger(final long value) {
        final MenuProducts menuProducts = MenuFixture.금액이불러와진_메뉴상품목록(product1, product2);
        final Amount amount = new Amount(price1 + price2 + value);

        boolean expected = menuProducts.isValidAmount(amount);

        assertThat(expected).isTrue();
    }


    @ParameterizedTest
    @ValueSource(strings = {"1000"})
    @DisplayName("Menu의 가격 (Amount)이 MenuProducts의 금액 정책에 부합하지 않는지(큰지) 확인할 수 있다.")
    void isValidAmount_sameAndLower(final long value) {
        final MenuProducts menuProducts = MenuFixture.금액이불러와진_메뉴상품목록(product1, product2);
        final Amount amount = new Amount(price1 + price2 + value);

        boolean expected = menuProducts.isValidAmount(amount);

        assertThat(expected).isFalse();
    }
}
