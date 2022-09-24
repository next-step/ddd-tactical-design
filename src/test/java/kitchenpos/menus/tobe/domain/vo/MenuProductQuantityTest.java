package kitchenpos.menus.tobe.domain.vo;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MenuProductQuantityTest {

    @DisplayName("메뉴 상품 수량을 생성할 수 있다.")
    @Test
    void create() {
        MenuProductQuantity actual = new MenuProductQuantity(0);

        assertThat(actual).isEqualTo(new MenuProductQuantity(0));
        assertThat(actual.hashCode() == new MenuProductQuantity(0).hashCode())
                .isTrue();
    }

    @DisplayName("메뉴 상품 수량은 0개 이상이어야 한다.")
    @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
    @ValueSource(longs = -1)
    void error1(long quantity) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new MenuProductQuantity(quantity));
    }
}
