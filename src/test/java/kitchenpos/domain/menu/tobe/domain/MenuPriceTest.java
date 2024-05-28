package kitchenpos.domain.menu.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class MenuPriceTest {

    @DisplayName("메뉴 가격을 생성한다")
    @Test
    void constructor() {
        BigDecimal price = BigDecimal.valueOf(1_000);
        assertThat(new MenuPrice(price)).isEqualTo(new MenuPrice(price));
    }

    @DisplayName("메뉴 가격이 Null이면 생성을 실패한다")
    @Test
    void constructor_null_fail() {
        assertThatIllegalArgumentException().isThrownBy(() -> new MenuPrice(null));
    }

    @DisplayName("메뉴 가격이 음수면 생성을 실패한다")
    @Test
    void constructor_minus_fail() {
        assertThatIllegalArgumentException().isThrownBy(() -> new MenuPrice(BigDecimal.valueOf(-1)));
    }
}
