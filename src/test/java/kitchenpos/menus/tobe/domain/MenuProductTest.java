package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static kitchenpos.menus.exception.MenuProductExceptionMessage.ILLEGAL_QUANTITY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("메뉴상품 테스트")
class MenuProductTest {

    @DisplayName("메뉴상품 생성 테스트")
    @Test
    void create() {
        UUID id = UUID.randomUUID();
        MenuProduct menuProduct = MenuProduct.of(id, 3L);
        assertThat(menuProduct).isEqualTo(MenuProduct.of(id, 3L));
    }

    @DisplayName("수량이 0보다 작으면 예외를 반환한다.")
    @ValueSource(longs = {-2L,-1L,0L})
    @ParameterizedTest
    void quantity(Long input) {
        UUID id = UUID.randomUUID();
        assertThatThrownBy(() -> MenuProduct.of(id, input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ILLEGAL_QUANTITY);
    }


}
