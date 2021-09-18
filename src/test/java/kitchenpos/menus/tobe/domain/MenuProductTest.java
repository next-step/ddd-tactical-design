package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuProductTest {
    @DisplayName("MenuProduct를 생성한다.")
    @Test
    void create() {
        //given
        long seq = new Random().nextLong();
        UUID productId = UUID.randomUUID();
        long quantity = 2L;
        Price price = new Price(BigDecimal.valueOf(15_000L));

        //when
        MenuProduct menuProduct = new MenuProduct(seq, productId, quantity, price);

        //then
        assertAll(
                () -> assertThat(menuProduct).isNotNull(),
                () -> assertThat(menuProduct.getSeq()).isEqualTo(seq),
                () -> assertThat(menuProduct.getProductId()).isEqualTo(productId),
                () -> assertThat(menuProduct.getQuantity()).isEqualTo(quantity),
                () -> assertThat(menuProduct.getPrice()).isEqualTo(price)
        );
    }

    @DisplayName("메뉴에 속한 상품의 수량은 0개 이상이어야 한다.")
    @ValueSource(longs = -1L)
    @ParameterizedTest
    void create_fail_MenuProduct_has_one_more_quantity(long quantity) {
        //given
        long seq = new Random().nextLong();
        UUID productId = UUID.randomUUID();
        Price price = new Price(BigDecimal.valueOf(15_000L));

        //when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new MenuProduct(seq, productId, quantity, price));
    }
}
