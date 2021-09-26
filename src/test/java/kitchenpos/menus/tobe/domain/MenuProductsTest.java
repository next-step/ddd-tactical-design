package kitchenpos.menus.tobe.domain;

import kitchenpos.fixture.MenuFixture;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("메뉴 상품 목록(MenuProducts)은")
class MenuProductsTest {
    private final UUID productId1 = randomUUID();
    private final UUID productId2 = randomUUID();
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

}
