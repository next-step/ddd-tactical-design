package kitchenpos.eatinorders.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kitchenpos.eatinorders.domain.tobe.domain.EatInOrderMenu;
import kitchenpos.eatinorders.domain.tobe.domain.EatInOrderMenuPrice;

class EatInOrderMenuTest {
    @DisplayName("menuId는 필수 항목이다.")
    @Test
    void new1() {
        assertThatThrownBy(() -> new EatInOrderMenu(null, EatInOrderMenuPrice.of(30_000)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("메뉴가 없으면 등록할 수 없습니다.");
    }

    @DisplayName("menu 가격은 필수 항목이다.")
    @Test
    void name() {
        assertThatThrownBy(() -> new EatInOrderMenu(UUID.randomUUID(), null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("메뉴 가격이 없으면 등록할 수 없습니다.");
    }
}
