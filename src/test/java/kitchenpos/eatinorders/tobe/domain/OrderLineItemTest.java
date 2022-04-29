package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.math.BigDecimal;

import static kitchenpos.eatinorders.tobe.domain.MenuFixture.*;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderLineItemTest {

    @DisplayName("주문 메뉴를 생성한다")
    @Test
    void create() {
        assertThatCode(() -> new OrderLineItem(displayedMenu(), 3L, BigDecimal.valueOf(10_000)))
                .doesNotThrowAnyException();
    }

    @DisplayName("메뉴가 없으면 주문 메뉴를 생성할 수 없다")
    @ParameterizedTest
    @NullSource
    void createWithInvalidMenuId(Menu menu) {
        assertThatThrownBy(() -> new OrderLineItem(menu, 3L, BigDecimal.valueOf(10_000)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴는 빈 값이 아니어야 합니다.");
    }

    @DisplayName("숨겨진 메뉴로는 주문 메뉴를 생성할 수 없다")
    @Test
    void createWithHiddenMenu() {
        assertThatThrownBy(() -> new OrderLineItem(hiddenMenu(), 3L, BigDecimal.valueOf(10_000)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("숨겨진 메뉴로는 생성할 수 없습니다.");
    }

    @DisplayName("메뉴 가격과 다르면 생성할 수 없다")
    @Test
    void createWithDifferentPrice() {
        assertThatThrownBy(() -> new OrderLineItem(displayedMenu(), 3L, BigDecimal.valueOf(20_000)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴 가격과 다르면 생성할 수 없습니다.");
    }
}
