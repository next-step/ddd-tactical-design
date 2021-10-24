package kitchenpos.menus.tobe.domain.model;

import kitchenpos.common.domain.model.Quantity;
import kitchenpos.common.domain.model.Price;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class MenuProductTest {

    @DisplayName("생성 검증")
    @Test
    void create() {

        Assertions.assertDoesNotThrow(
                () -> new MenuProduct(UUID.randomUUID(), new Price(BigDecimal.valueOf(10000L)), new Quantity(BigDecimal.valueOf(2L)))
        );
    }

    @DisplayName("올바르지 않은 생성시 에러 검증 - 올바르지 않은 가격")
    @Test
    void wrongPriceCreate() {
        // wrong price
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->
                        new MenuProduct(UUID.randomUUID(), new Price(null), new Quantity(BigDecimal.valueOf(2L))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("올바르지 않은 생성시 에러 검증 - 올바르지 않은 양")
    @Test
    void wrongQuantityCreate() {
        // wrong quantity
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->
                        new MenuProduct(UUID.randomUUID(), new Price(BigDecimal.valueOf(10000L)), new Quantity(null)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("합산 검증")
    @Test
    void getAmountTest() {
        MenuProduct menuProduct = new MenuProduct(UUID.randomUUID(), new Price(BigDecimal.valueOf(10000L)), new Quantity(BigDecimal.valueOf(2L)));
        assertThat(menuProduct.getAmount().compareTo(BigDecimal.valueOf(20000L))).isEqualTo(0);
    }


}
