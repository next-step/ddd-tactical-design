package kitchenpos.menus.tobe.domain.model;

import kitchenpos.menus.tobe.domain.exception.IllegalMenuPriceException;
import kitchenpos.menus.tobe.doubles.FakeProfanity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuTest {

    private final Profanity profanity = new FakeProfanity();
    private final MenuName menuName = new MenuName(profanity, "치킨");

    @DisplayName("가격 정책에 안 맞는 메뉴 생성하면 예외")
    @Test
    void constructor() {
        // given
        MenuProduct menuProduct = new MenuProduct(UUID.randomUUID(), 3, 5000L);
        long tooMuchPrice = 900_000L;

        // expected
        assertThatThrownBy(() -> new Menu(UUID.randomUUID(), menuName, tooMuchPrice, 1L, true, menuProduct))
                .isInstanceOf(IllegalMenuPriceException.class);
    }

    @DisplayName("가격 정책에 위반되는 가격 변경은 예외")
    @Test
    void changePrice() {
        // given
        MenuProduct menuProduct = new MenuProduct(UUID.randomUUID(), 3, 5000L);
        Menu menu = new Menu(UUID.randomUUID(), menuName, 14_000L, 1L, true, menuProduct);

        // expected
        assertThatThrownBy(() -> menu.changePrice(new MenuPrice(900_000L)))
                .isInstanceOf(IllegalMenuPriceException.class);
    }

    @DisplayName("메뉴 상품 가격 변경에 따라 메뉴가 숨겨질 수도 있다.")
    @Test
    void updateProduct() {
        // given
        UUID productId = UUID.randomUUID();
        MenuProduct menuProduct = new MenuProduct(productId, 3, 5000L);
        Menu menu = new Menu(UUID.randomUUID(), menuName, 14_000L, 1L, true, menuProduct);

        // when
        assertThat(menu.isDisplayed()).isTrue();
        menu.updateProduct(productId, 0L);

        // then
        assertThat(menu.isDisplayed()).isFalse();
    }
}
