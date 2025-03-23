package kitchenpos.eatinorders.tobe.domain.order;

import kitchenpos.eatinorders.tobe.domain.exception.InvalidEatInOrderException;
import kitchenpos.eatinorders.tobe.domain.exception.InvalidOccupiedException;
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
        final EatInOrderLineItem eatInOrderLineItem = new EatInOrderLineItem(menuId, 1, 20_000);
        final EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems(List.of(eatInOrderLineItem));

        // when & then
        assertThatThrownBy(() ->
                new EatInOrder(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        null,
                        LocalDateTime.now(),
                        eatInOrderLineItems,
                        eatInOrderMenus
                )
        ).isInstanceOf(InvalidEatInOrderException.class)
                .hasMessage("주문 생성에 필요한 정보가 누락되었습니다.");
    }

    @Test
    void 주문항목이_존재해야_한다() {
        // given & when & then
        assertThatThrownBy(() ->
                new EatInOrder(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        EatInOrderStatus.WAITING,
                        LocalDateTime.now(),
                        null,
                        eatInOrderMenus
                )
        ).isInstanceOf(InvalidEatInOrderException.class)
                .hasMessage("주문 생성에 필요한 정보가 누락되었습니다.");
    }

    @DisplayName("고객이 미리 자리에 앉아 있고 아직 메뉴를 정하지 않는 상태에서 자리만 확보할 수 있기 때문에, 주문수량이 0일 수도 있다.")
    @Test
    void 매장_식사이면_주문수량이_0일_수도_있다() {
        // given
        final EatInOrderLineItem item = new EatInOrderLineItem(menuId, 0, 20_000);
        final EatInOrderLineItems items = new EatInOrderLineItems(List.of(item));

        // when
        EatInOrder eatInOrder = new EatInOrder(
                UUID.randomUUID(),
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
        final UUID menuId = UUID.randomUUID();
        final EatInOrderLineItem item = new EatInOrderLineItem(menuId, 1, 20_000);
        final EatInOrderLineItems items = new EatInOrderLineItems(List.of(item));

        // when & then
        assertThatThrownBy(
                () -> new EatInOrder(
                        UUID.randomUUID(),
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
        final UUID menuId = UUID.randomUUID();
        final EatInOrderMenu eatInOrderMenu = new DefaultEatInOrderMenu(menuId, 1, false);
        final EatInOrderMenus eatInOrderMenus = new DefaultEatInOrderMenus(eatInOrderMenu);
        final EatInOrderLineItem item = new EatInOrderLineItem(menuId, 1, 20_000);
        final EatInOrderLineItems items = new EatInOrderLineItems(List.of(item));

        // when
        assertThatThrownBy(
                () -> new EatInOrder(
                        UUID.randomUUID(),
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
        final EatInOrderLineItem item = new EatInOrderLineItem(menuId, 1, 19_000);
        final EatInOrderLineItems items = new EatInOrderLineItems(List.of(item));

        // when  &  then
        assertThatThrownBy(
                () -> new EatInOrder(
                        UUID.randomUUID(),
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
        final EatInOrderLineItem eatInOrderLineItem = new EatInOrderLineItem(menuId, 1, 20_000);
        final EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems(List.of(eatInOrderLineItem));

        final OrderTableId tableId = new OrderTableId(UUID.randomUUID());
        final boolean isOccupied = false;

        // when & then
        assertThatThrownBy(() ->
                new EatInOrder(
                        UUID.randomUUID(),
                        tableId.getId(),
                        isOccupied,
                        EatInOrderStatus.WAITING,
                        LocalDateTime.now(),
                        eatInOrderLineItems,
                        eatInOrderMenus
                )
        ).isInstanceOf(InvalidOccupiedException.class)
                .hasMessageContaining("매장 주문은 손님이 앉은 테이블에서만 가능합니다.");
    }

    @Test
    void 매장_주문을_등록한다() {
        // given
        final UUID firstMenuId = UUID.randomUUID();
        final UUID secondMenuId = UUID.randomUUID();
        final EatInOrderMenus eatInOrderMenus = new DefaultEatInOrderMenus(
                new DefaultEatInOrderMenu(firstMenuId, 20_000, true),
                new DefaultEatInOrderMenu(secondMenuId, 22_000, true)
        );

        final EatInOrderLineItem firstEatInOrderLineItem = new EatInOrderLineItem(firstMenuId, 1, 20_000);
        final EatInOrderLineItem secondEatInOrderLineItem = new EatInOrderLineItem(secondMenuId, 1, 22_000);
        final EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems(List.of(firstEatInOrderLineItem, secondEatInOrderLineItem));

        // when
        final EatInOrder eatInOrder = new EatInOrder(eatInOrderLineItems, eatInOrderMenus);

        // then
        assertThat(eatInOrder.getId()).isNotNull();
        assertThat(eatInOrder.getStatus()).isEqualTo(EatInOrderStatus.WAITING);
    }
}
