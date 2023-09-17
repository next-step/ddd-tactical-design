package kitchenpos.ordermaster.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderMenuTest {
    @DisplayName("menuId는 필수 항목이다.")
    @Test
    void new1() {
        assertThatThrownBy(() -> new OrderMenu(null, OrderMenuPrice.of(30_000)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("메뉴가 없으면 등록할 수 없습니다.");
    }

    @DisplayName("menu 가격은 필수 항목이다.")
    @Test
    void name() {
        assertThatThrownBy(() -> new OrderMenu(UUID.randomUUID(), null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("메뉴 가격이 없으면 등록할 수 없습니다.");
    }
}
