package kitchenpos.menus.tobe.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MenuProductQuantityTest {

    @DisplayName("구성품 수량은 음수가 될수 없다")
    @ParameterizedTest
    @ValueSource(longs = {-1, -100, -1_000, -10_000, -100_000, -1_000_000})
    void test1(long quantity) {
        assertThatThrownBy(
            () -> new MenuProductQuantity(quantity)
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("메뉴에 등록될 상품의 개수는 음수일수 없습니다");
    }
}