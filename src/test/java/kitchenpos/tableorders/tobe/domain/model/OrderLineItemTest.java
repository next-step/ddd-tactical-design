package kitchenpos.tableorders.tobe.domain.model;

import kitchenpos.global.domain.vo.Price;
import kitchenpos.tableorders.tobe.domain.dto.MenuResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderLineItemTest {

    private static final MenuResponse 진열되지_않은_상품 = new MenuResponse(UUID.randomUUID(), new Price(BigDecimal.valueOf(1000L)), false);
    private static final MenuResponse 진열된_상품 = new MenuResponse(UUID.randomUUID(), new Price(BigDecimal.valueOf(1000L)), true);

    @DisplayName("메뉴(Menu) 가 노출(visible) 상태여야 한다.")
    @Test
    void create01() {
        //when & then
        assertThatThrownBy(() -> new OrderLineItem(진열되지_않은_상품, new Price(BigDecimal.valueOf(1000L)), 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("현재 판매중인 상품이 아닙니다.");
    }

    @DisplayName("메뉴(Menu) 표기된(displayed) 가격과 실제 메뉴의 가격이 일치해야 한다.")
    @Test
    void create02() {
        //when & then
        assertThatThrownBy(() -> new OrderLineItem(진열된_상품, new Price(BigDecimal.valueOf(2000L)), 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("진열된 메뉴의 가격과 현재 메뉴의 가격이 일치하지 않습니다.");
    }

}
