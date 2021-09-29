package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.commons.tobe.domain.model.Price;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class OrderMenusTest {

    @DisplayName("주문 메뉴 목록을 생성한다.")
    @Test
    void 주문_메뉴_목록_생성_성공() {
        final OrderMenu orderMenu = new OrderMenu(UUID.randomUUID(), new Price(16_000L), true);

        final OrderMenus orderMenus = new OrderMenus(Collections.singletonList(orderMenu));

        assertThat(orderMenus.getOrderMenuByMenuId(orderMenu.getId())).isEqualTo(orderMenu);
    }

    @DisplayName("주문 메뉴 목록이 주문 가능한지 검증한다.")
    @Test
    void 유효성_검증_성공() {
        final OrderMenu orderMenu = new OrderMenu(UUID.randomUUID(), new Price(16_000L), true);
        final OrderMenus orderMenus = new OrderMenus(Collections.singletonList(orderMenu));

        try {
            orderMenus.validate(Collections.singletonList(orderMenu.getId()));
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException을 던지지 않아야 한다.");
        } catch (IllegalStateException e) {
            fail("IllegalStateException을 던지지 않아야 한다.");
        }
    }

    @DisplayName("메뉴 식별자에 대응하는 주문 메뉴가 없으면 IllegalArgumentException을 던진다.")
    @Test
    void 등록되지_않은_식별자로_인한_유효성_검증_실패() {
        final OrderMenu orderMenu = new OrderMenu(UUID.randomUUID(), new Price(16_000L), true);
        final OrderMenus orderMenus = new OrderMenus(Collections.singletonList(orderMenu));

        ThrowableAssert.ThrowingCallable when = () -> orderMenus.validate(Collections.singletonList(UUID.randomUUID()));

        assertThatIllegalArgumentException().isThrownBy(when)
                .withMessage("등록된 메뉴가 없습니다.");
    }

    @DisplayName("공개되지 않은 메뉴가 있으면 IllegalStateException을 던진다.")
    @Test
    void 공개되지_않은_메뉴로_인한_유효성_검증_실패() {
        final OrderMenu orderMenu = new OrderMenu(UUID.randomUUID(), new Price(16_000L), false);
        final OrderMenus orderMenus = new OrderMenus(Collections.singletonList(orderMenu));

        ThrowableAssert.ThrowingCallable when = () -> orderMenus.validate(Collections.singletonList(orderMenu.getId()));

        assertThatIllegalStateException().isThrownBy(when)
                .withMessage("노출되지 않은 메뉴는 주문할 수 없습니다.");
    }
}
