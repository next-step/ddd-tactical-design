package kitchenpos.menus.domain.tobe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class MenuQuantityTest {

    @DisplayName("수량을 생성할 수 있다.")
    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5})
    void create(long quantity) {
        MenuQuantity menuQuantity = new MenuQuantity(quantity);

        assertThat(menuQuantity).extracting("quantity").isEqualTo(quantity);
    }

    @DisplayName("수량을 생성할 수 없다.")
    @ParameterizedTest
    @ValueSource(longs = {-1, -2, -3, -4, -5})
    void create_fail(long quantity) {
        assertThatCode(() -> new MenuQuantity(quantity)).isInstanceOf(IllegalArgumentException.class);
    }
}
