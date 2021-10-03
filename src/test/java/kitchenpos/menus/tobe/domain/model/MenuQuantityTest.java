package kitchenpos.menus.tobe.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class MenuQuantityTest {

    @DisplayName("생성 검증")
    @Test
    void create() {
        Assertions.assertDoesNotThrow(() -> new MenuQuantity(BigDecimal.valueOf(2L)));
    }

    @DisplayName("올바르지 않은 생성시 에러 검증 - 음수값")
    @Test
    void minusValueCreate() {
        // minus value
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> new MenuQuantity(BigDecimal.valueOf(-1L)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("올바르지 않은 생성시 에러 검증 - null")
    @Test
    void nullValueCreate() {
        // null value
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> new MenuQuantity(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("동등성 검증")
    @Test
    void equals(){
        MenuQuantity menuQuantity1 = new MenuQuantity(BigDecimal.valueOf(2L));
        MenuQuantity menuQuantity2 = new MenuQuantity(BigDecimal.valueOf(1L));
        org.assertj.core.api.Assertions.assertThat(menuQuantity1.equals(menuQuantity2))
                .isFalse();
    }

}
