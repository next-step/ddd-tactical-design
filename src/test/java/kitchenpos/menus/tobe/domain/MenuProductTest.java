package kitchenpos.menus.tobe.domain;

import kitchenpos.products.tobe.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static kitchenpos.Fixtures.newProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class MenuProductTest {

    private final Product product = newProduct("상품", 1000L);

    @Test
    @DisplayName("상품이 비어있으면 메뉴 상품 생성 실패")
    void createMenuProductFail01() {
        assertThatIllegalArgumentException().isThrownBy(() -> MenuProduct.create(null, 0L));
    }

    @ParameterizedTest
    @ValueSource(longs = { 0L, -1L, -100L })
    @DisplayName("재고가 0개 이하면 메뉴상품 생성 실패")
    void createMenuProductFail02(long quantity) {
        assertThatIllegalArgumentException().isThrownBy(() -> MenuProduct.create(product, quantity));
    }

    @Test
    @DisplayName("메뉴 상품 생성 성공")
    void createMenuProductSuccess() {
        // given
        long price = product.getPrice();
        long quantity = 5L;

        // when
        MenuProduct actual = MenuProduct.create(product, quantity);

        // then
        assertThat(actual.getPrice()).isEqualTo(price * quantity);
    }
}
