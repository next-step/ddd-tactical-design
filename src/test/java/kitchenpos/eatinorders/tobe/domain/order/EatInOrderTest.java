package kitchenpos.eatinorders.tobe.domain.order;

import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderLineItems;
import kitchenpos.eatinorders.tobe.infrastructure.DefaultEatInOrderMenu;
import kitchenpos.eatinorders.tobe.infrastructure.DefaultEatInOrderMenus;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@DisplayName("매장내 주문에 대한 테스트")
class EatInOrderTest {

    private EatInOrderMenu eatInOrderMenu;
    private EatInOrderMenus eatInOrderMenus;

    @BeforeEach
    void setUp() {
        eatInOrderMenu = new DefaultEatInOrderMenu();
        eatInOrderMenus = new DefaultEatInOrderMenus(eatInOrderMenu);
    }

    @Test
    void 주문유형이_존재해야_한다() {
        // given & when & then
        assertThatThrownBy(() ->
                new EatInOrder(UUID.randomUUID(),
                        null,
                        LocalDateTime.now(), new EatInOrderLineItems(
                        List.of(new EatInOrderLineItem(UUID.randomUUID(), 1, 20_000))),
                        eatInOrderMenus
                )
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주문항목이_존재해야_한다() {
        // given & when & then
        assertThatThrownBy(() ->
                new EatInOrder(
                        UUID.randomUUID(),
                        EatInOrderStatus.WAITING,
                        LocalDateTime.now(),
                        null,
                        eatInOrderMenus
                )
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
