package kitchenpos.menus.domain.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class MenuProductTest {

    @DisplayName("상품 가격, 수량으로 메뉴 상품을 생성한다")
    @Test
    void create() {
        assertThatCode(() -> new MenuProduct(UUID.randomUUID(), BigDecimal.TEN, 0L))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("메뉴 상품의 수량은 0이상이어야 한다")
    void inValidQuantity() {
        assertThatThrownBy(() -> new MenuProduct(UUID.randomUUID(), BigDecimal.TEN, -1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("수량은 0이상의 정수 이어야 합니다. 입력 값 : " + -1);
    }

    @DisplayName("메뉴 상품의 합계 금액을 가져온다")
    @Test
    void getAmount() {
        // given
        MenuProduct menuProduct = new MenuProduct(UUID.randomUUID(), BigDecimal.valueOf(1_000), 5L);

        // when
        long amount = menuProduct.getAmount();

        // then
        assertThat(amount).isEqualTo(5_000L);
    }
}
