package kitchenpos.menu.tobe.domain.vo;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MenuProductQuantityTest {

    @DisplayName("MenuProductQuantity는 1 이상의 정수일 수 있다.")
    @ValueSource(longs = {
        1, 395848125, 1252547819, 491598008, 1632439888,
        1351529542, 192099596, 1574050269, 208629295, 330560803,
    })
    @ParameterizedTest
    void yvvbgqpw(final long menuProductQuantity) {
        assertThatNoException().isThrownBy(() -> new MenuProductQuantity(menuProductQuantity));
    }

    @DisplayName("MenuProductQuantity는 1 미만의 정수일 수 없다.")
    @ValueSource(longs = {
        0, -384956592, -265755600, -583220825, -1589482440,
        -718419631, -163086986, -1222040361, -498175820, -816502739,
    })
    @ParameterizedTest
    void dhdrhvkk(final long menuProductQuantity) {
        assertThatIllegalArgumentException().isThrownBy(() ->
            new MenuProductQuantity(menuProductQuantity));
    }
}
