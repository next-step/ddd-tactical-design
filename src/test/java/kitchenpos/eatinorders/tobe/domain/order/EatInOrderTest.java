package kitchenpos.eatinorders.tobe.domain.order;

import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderLineItems;
import kitchenpos.eatinorders.tobe.domain.orderTable.vo.OrderTableId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@DisplayName("매장내 주문에 대한 테스트")
class EatInOrderTest {

    private UUID menuId;
    private EatInOrderMenus eatInOrderMenus;

    @BeforeEach
    void setUp() {
        menuId = UUID.randomUUID();
        EatInOrderMenu eatInOrderMenu = new DefaultEatInOrderMenu(menuId, 20_000, true);
        eatInOrderMenus = new DefaultEatInOrderMenus(eatInOrderMenu);
    }

    @Test
    void 주문유형이_존재해야_한다() {
        // given
        EatInOrderLineItem eatInOrderLineItem = new EatInOrderLineItem(menuId, 1, 20_000);
        EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems(List.of(eatInOrderLineItem));

        // when & then
        assertThatThrownBy(() ->
                new EatInOrder(UUID.randomUUID(),
                        null,
                        LocalDateTime.now(),
                        eatInOrderLineItems,
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

    @Test
    void 매장_식사이면_주문수량이_0일_수도_있다() {
        // given
        EatInOrderLineItem item = new EatInOrderLineItem(menuId, 0, 20_000);
        EatInOrderLineItems items = new EatInOrderLineItems(List.of(item));

        // when
        EatInOrder eatInOrder = new EatInOrder(
                UUID.randomUUID(),
                EatInOrderStatus.WAITING,
                LocalDateTime.now(),
                items,
                eatInOrderMenus
        );

        // then
        assertThat(eatInOrder).isNotNull();
    }

    @Test
    void 주문항목_과_연관된_메뉴가_존재해야_한다() {
        // given
        UUID menuId = UUID.randomUUID();
        EatInOrderLineItem item = new EatInOrderLineItem(menuId, 1, 20_000);
        EatInOrderLineItems items = new EatInOrderLineItems(List.of(item));

        // when & then
        assertThatThrownBy(
                () -> new EatInOrder(
                        UUID.randomUUID(),
                        EatInOrderStatus.WAITING,
                        LocalDateTime.now(),
                        items,
                        eatInOrderMenus)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 메뉴입니다.");
    }

    @Test
    void 메뉴가_비노출_상태이면_주문을_생성할_수_없다() {
        // given
        UUID menuId = UUID.randomUUID();
        EatInOrderMenu eatInOrderMenu = new DefaultEatInOrderMenu(menuId, 1, false);
        EatInOrderMenus eatInOrderMenus = new DefaultEatInOrderMenus(eatInOrderMenu);
        EatInOrderLineItem item = new EatInOrderLineItem(menuId, 1, 20_000);
        EatInOrderLineItems items = new EatInOrderLineItems(List.of(item));

        // when
        assertThatThrownBy(
                () -> new EatInOrder(
                        UUID.randomUUID(),
                        EatInOrderStatus.WAITING,
                        LocalDateTime.now(),
                        items,
                        eatInOrderMenus)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("표시되지 않은 메뉴는 주문할 수 없습니다.");
    }

    @Test
    void 주문항목이_메뉴에_있는_가격과_같아야한다() {
        // given
        EatInOrderLineItem item = new EatInOrderLineItem(menuId, 1, 19_000);
        EatInOrderLineItems items = new EatInOrderLineItems(List.of(item));

        // when  &  then
        assertThatThrownBy(
                () -> new EatInOrder(
                        UUID.randomUUID(),
                        EatInOrderStatus.WAITING,
                        LocalDateTime.now(),
                        items,
                        eatInOrderMenus)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 항목에 있는 가격이 메뉴에 있는 가격과 동일하지 않습니다.");
    }

    @Test
    void 손님이_앉은_테이블에서만_주문을_생성할_수_있다() {
        // given
        EatInOrderLineItem eatInOrderLineItem = new EatInOrderLineItem(menuId, 1, 20_000);
        EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems(List.of(eatInOrderLineItem));

        OrderTableId tableId = new OrderTableId(UUID.randomUUID());
        boolean isOccupied = false;

        // when & then
        assertThatThrownBy(() ->
                new EatInOrder(
                        tableId.getId(),
                        isOccupied,
                        EatInOrderStatus.WAITING,
                        LocalDateTime.now(),
                        eatInOrderLineItems,
                        eatInOrderMenus
                )
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("빈 테이블에는 주문할 수 없습니다.");
    }
}
