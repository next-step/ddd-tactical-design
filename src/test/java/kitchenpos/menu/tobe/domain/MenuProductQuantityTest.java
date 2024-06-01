package kitchenpos.menu.tobe.domain;

import kitchenpos.menu.tobe.domain.menu.MenuProductQuantity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuProductQuantityTest {
    @Test
    @DisplayName("수량이 음수라면 예외가 발생한다.")
    void test1() {
        Assertions.assertThatThrownBy(() -> new MenuProductQuantity(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("수량이 0 이상이라면 정상적으로 생성할 수 있다.")
    void test2() {
        long validQuantity = 10;
        MenuProductQuantity menuProductQuantity = new MenuProductQuantity(validQuantity);

        Assertions.assertThat(menuProductQuantity).isNotNull();
        Assertions.assertThat(menuProductQuantity.getQuantity()).isEqualTo(validQuantity);
    }
}