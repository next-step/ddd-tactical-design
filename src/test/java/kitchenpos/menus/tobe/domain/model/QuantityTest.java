package kitchenpos.menus.tobe.domain.model;

import kitchenpos.common.domain.model.Quantity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class QuantityTest {

    @DisplayName("생성 검증")
    @Test
    void create() {
        Assertions.assertDoesNotThrow(() -> new Quantity(BigDecimal.valueOf(2L)));
    }

    @DisplayName("올바르지 않은 생성시 에러 검증 - 음수값")
    @Test
    void minusValueCreate() {
        // minus value
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> new Quantity(BigDecimal.valueOf(-1L)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("올바르지 않은 생성시 에러 검증 - null")
    @Test
    void nullValueCreate() {
        // null value
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> new Quantity(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("동등성 검증")
    @Test
    void equals(){
        Quantity quantity1 = new Quantity(BigDecimal.valueOf(2L));
        Quantity quantity2 = new Quantity(BigDecimal.valueOf(1L));
        org.assertj.core.api.Assertions.assertThat(quantity1.equals(quantity2))
                .isFalse();
    }

}
