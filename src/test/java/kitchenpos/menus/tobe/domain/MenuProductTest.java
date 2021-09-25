package kitchenpos.menus.tobe.domain;

import kitchenpos.common.domain.Price;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

public class MenuProductTest {

    @DisplayName("생성 검증")
    @Test
    void create() {

        Assertions.assertDoesNotThrow(
                () -> new MenuProduct(UUID.randomUUID(), new Price(BigDecimal.valueOf(10000L)), new Quantity(BigDecimal.valueOf(2L)))
        );
    }

    @DisplayName("올바르지 않은 생성시 에러 검증")
    @Test
    void invalidCreate() {
        // wrong price
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->
                        new MenuProduct(UUID.randomUUID(), new Price(null), new Quantity(BigDecimal.valueOf(2L))))
                .isInstanceOf(IllegalArgumentException.class);

        // wrong quantity
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->
                        new MenuProduct(UUID.randomUUID(), new Price(BigDecimal.valueOf(10000L)), new Quantity(null)))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
