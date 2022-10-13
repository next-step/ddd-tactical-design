package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatCode;

/*
- [x] 메뉴에 속한 상품의 수량은 0보다 크거나 같아야한다.
- [x] 1 개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.
- [x] 상품이 없으면 등록할 수 없다.
 */
class MenuProductTest {
    @DisplayName("메뉴에 속한 상품의 수량은 0보다 크거나 같다.")
    @ParameterizedTest
    @ValueSource(longs = {0L, 1L})
    void moreThanZeroAndEqualsQuantity(final long quantity) {
        final Product product = new Product(BigDecimal.ONE);
        assertThatCode(() -> new MenuProduct(quantity, new DisplayedName("양념치킨", new FakeProfanity()), product))
                .doesNotThrowAnyException();
    }
}
