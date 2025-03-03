package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.domain.order.*;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderDateTime;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderId;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderStatus;
import kitchenpos.eatinorders.tobe.domain.order.DefaultEatInOrderMenu;
import kitchenpos.eatinorders.tobe.domain.order.DefaultEatInOrderMenus;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderMenus;
import kitchenpos.eatinorders.tobe.domain.ordertable.vo.OrderTableId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("매장 주문 테스트")
public class EatInOrderTest {

    @DisplayName("주문 테이블이 없으면 매장 주문을 생성할 수 없다.")
    @NullSource
    @ParameterizedTest(name = "주문 테이블 식별자: {0}")
    void createWithoutOrderTable(final OrderTableId orderTable) {
        final UUID firstMenuId = UUID.randomUUID();
        final UUID secondMenuId = UUID.randomUUID();
        final EatInOrderMenus eatInOrderMenus = new DefaultEatInOrderMenus(
                new DefaultEatInOrderMenu(firstMenuId, 16_000, true),
                new DefaultEatInOrderMenu(secondMenuId, 16_000, true)
        );

        final EatInOrderLineItem firstEatInOrderLineItem = new EatInOrderLineItem(firstMenuId, "후라이드 치킨", 16_000, 1);
        final EatInOrderLineItem secondEatInOrderLineItem = new EatInOrderLineItem(secondMenuId, "양념 치킨", 16_000, 1);
        final List<EatInOrderLineItem> eatInOrderLineItems = List.of(firstEatInOrderLineItem, secondEatInOrderLineItem);

        assertThatThrownBy(() -> new EatInOrder(eatInOrderLineItems, eatInOrderMenus, orderTable))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 항목 메뉴와 메뉴의 개수가 일치하지 않으면 매장 주문을 생성할 수 없다.")
    @Test
    void createWithDifferentMenuSize() {
        final UUID firstMenuId = UUID.randomUUID();
        final UUID secondMenuId = UUID.randomUUID();
        final EatInOrderMenus eatInOrderMenus = new DefaultEatInOrderMenus(new DefaultEatInOrderMenu(firstMenuId, 16_000, true));

        final EatInOrderLineItem firstEatInOrderLineItem = new EatInOrderLineItem(firstMenuId, "후라이드 치킨", 16_000, 1);
        final EatInOrderLineItem secondEatInOrderLineItem = new EatInOrderLineItem(secondMenuId, "양념 치킨", 16_000, 1);
        final List<EatInOrderLineItem> eatInOrderLineItems = List.of(firstEatInOrderLineItem, secondEatInOrderLineItem);

        assertThatThrownBy(() -> new EatInOrder(eatInOrderLineItems, eatInOrderMenus, new OrderTableId()))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 항목 메뉴와 메뉴의 가격이 일치하지 않으면 매장 주문을 생성할 수 없다.")
    @Test
    void createWithDifferentMenuPrice() {
        final UUID firstMenuId = UUID.randomUUID();
        final UUID secondMenuId = UUID.randomUUID();
        final EatInOrderMenus eatInOrderMenus = new DefaultEatInOrderMenus(
                new DefaultEatInOrderMenu(firstMenuId, 16_001, true),
                new DefaultEatInOrderMenu(secondMenuId, 16_001, true)
        );

        final EatInOrderLineItem firstEatInOrderLineItem = new EatInOrderLineItem(firstMenuId, "후라이드 치킨", 16_000, 1);
        final EatInOrderLineItem secondEatInOrderLineItem = new EatInOrderLineItem(secondMenuId, "양념 치킨", 16_000, 1);
        final List<EatInOrderLineItem> eatInOrderLineItems = List.of(firstEatInOrderLineItem, secondEatInOrderLineItem);

        assertThatThrownBy(() -> new EatInOrder(eatInOrderLineItems, eatInOrderMenus, new OrderTableId()))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 항목 메뉴가 미노출 상태이면 매장 주문을 생성할 수 없다.")
    @Test
    void createWithNotDisplayedMenu() {
        final UUID firstMenuId = UUID.randomUUID();
        final UUID secondMenuId = UUID.randomUUID();
        final EatInOrderMenus eatInOrderMenus = new DefaultEatInOrderMenus(
                new DefaultEatInOrderMenu(firstMenuId, 16_000, false),
                new DefaultEatInOrderMenu(secondMenuId, 16_000, true)
        );

        final EatInOrderLineItem firstEatInOrderLineItem = new EatInOrderLineItem(firstMenuId, "후라이드 치킨", 16_000, 1);
        final EatInOrderLineItem secondEatInOrderLineItem = new EatInOrderLineItem(secondMenuId, "양념 치킨", 16_000, 1);
        final List<EatInOrderLineItem> eatInOrderLineItems = List.of(firstEatInOrderLineItem, secondEatInOrderLineItem);

        assertThatThrownBy(() -> new EatInOrder(eatInOrderLineItems, eatInOrderMenus, new OrderTableId()))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    /**
     * Application 에서는 OrderTable 가 occupied 를 확인하는 로직을 통해 생성 가능한지 판단.
     * DB 저장 및 조회시에는 OrderTableId 만 사용함.
     * OrderTable 의 상태를 확인하는 로직은 Application 에서 처리함
     * poc 패키지에 존재하는 EatInOrderTable 관련 클래스들을 활용하면,
     * 생성자내에서 OrderTable 의 상태를 확인할 수 있으나, 트레이드 오프 영역이라 판단
     */
//    @DisplayName("주문 테이블이 비어있으면 매장 주문을 생성할 수 없다.")
//    @Test
//    void createWithEmptyOrderTable() {
//        final UUID firstMenuId = UUID.randomUUID();
//        final UUID secondMenuId = UUID.randomUUID();
//        final EatInOrderLineItem firstEatInOrderLineItem = new EatInOrderLineItem(firstMenuId, "후라이드 치킨", 16_000, 1);
//        final EatInOrderLineItem secondEatInOrderLineItem = new EatInOrderLineItem(secondMenuId, "양념 치킨", 16_000, 1);
//        final EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems(firstEatInOrderLineItem, secondEatInOrderLineItem);
//        final EatInOrderMenus eatInOrderMenus = new DefaultEatInOrderMenus(
//                new DefaultEatInOrderMenu(firstMenuId, 16_000, true),
//                new DefaultEatInOrderMenu(secondMenuId, 16_000, true)
//        );
//        final OrderTable orderTable = new OrderTable("1번", 0, false);
//
//        assertThatThrownBy(() -> new EatInOrder(eatInOrderLineItems, eatInOrderMenus, orderTable))
//                .isExactlyInstanceOf(IllegalArgumentException.class);
//    }

    @DisplayName("매장 주문을 생성한다.")
    @Test
    void create() {
        final UUID firstMenuId = UUID.randomUUID();
        final UUID secondMenuId = UUID.randomUUID();
        final EatInOrderMenus eatInOrderMenus = new DefaultEatInOrderMenus(
                new DefaultEatInOrderMenu(firstMenuId, 16_000, true),
                new DefaultEatInOrderMenu(secondMenuId, 16_000, true)
        );

        final EatInOrderLineItem firstEatInOrderLineItem = new EatInOrderLineItem(firstMenuId, "후라이드 치킨", 16_000, 1);
        final EatInOrderLineItem secondEatInOrderLineItem = new EatInOrderLineItem(secondMenuId, "양념 치킨", 16_000, 1);
        final List<EatInOrderLineItem> eatInOrderLineItems = List.of(firstEatInOrderLineItem, secondEatInOrderLineItem);

        final EatInOrder eatInOrder = new EatInOrder(eatInOrderLineItems, eatInOrderMenus, new OrderTableId());
        assertThat(eatInOrder.isSameStatus(EatInOrderStatus.WAITING)).isTrue();
    }

    @DisplayName("대기중인 매장 주문이 아니면 주문 수락을 변경할 수 없다.")
    @EnumSource(value = EatInOrderStatus.class, names = {"ACCEPTED", "SERVED", "COMPLETED"})
    @ParameterizedTest(name = "{index}. 주문 상태: {0}")
    void acceptWithNotWaitingStatus(final EatInOrderStatus eatInOrderStatus) {
        final UUID firstMenuId = UUID.randomUUID();
        final UUID secondMenuId = UUID.randomUUID();
        final EatInOrderMenus eatInOrderMenus = new DefaultEatInOrderMenus(
                new DefaultEatInOrderMenu(firstMenuId, 16_000, true),
                new DefaultEatInOrderMenu(secondMenuId, 16_000, true)
        );

        final EatInOrderLineItem firstEatInOrderLineItem = new EatInOrderLineItem(firstMenuId, "후라이드 치킨", 16_000, 1);
        final EatInOrderLineItem secondEatInOrderLineItem = new EatInOrderLineItem(secondMenuId, "양념 치킨", 16_000, 1);
        final EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems(eatInOrderMenus, firstEatInOrderLineItem, secondEatInOrderLineItem);

        final EatInOrder eatInOrder = new EatInOrder(
                new EatInOrderId(), eatInOrderStatus, new EatInOrderDateTime(), eatInOrderLineItems, new OrderTableId()
        );
        assertThatThrownBy(eatInOrder::accepted)
                .isExactlyInstanceOf(IllegalStateException.class);
    }

    @DisplayName("대기중인 매장 주문을 주문 수락으로 변경한다.")
    @Test
    void accept() {
        final UUID firstMenuId = UUID.randomUUID();
        final UUID secondMenuId = UUID.randomUUID();
        final EatInOrderMenus eatInOrderMenus = new DefaultEatInOrderMenus(
                new DefaultEatInOrderMenu(firstMenuId, 16_000, true),
                new DefaultEatInOrderMenu(secondMenuId, 16_000, true)
        );

        final EatInOrderLineItem firstEatInOrderLineItem = new EatInOrderLineItem(firstMenuId, "후라이드 치킨", 16_000, 1);
        final EatInOrderLineItem secondEatInOrderLineItem = new EatInOrderLineItem(secondMenuId, "양념 치킨", 16_000, 1);
        final List<EatInOrderLineItem> eatInOrderLineItems = List.of(firstEatInOrderLineItem, secondEatInOrderLineItem);

        final EatInOrder eatInOrder = new EatInOrder(eatInOrderLineItems, eatInOrderMenus,  new OrderTableId());
        eatInOrder.accepted();
        assertThat(eatInOrder.isSameStatus(EatInOrderStatus.ACCEPTED)).isTrue();
    }

    @DisplayName("수락중인 매장 주문이 아니면 주문 서빙을 변경할 수 없다.")
    @EnumSource(value = EatInOrderStatus.class, names = {"WAITING", "SERVED", "COMPLETED"})
    @ParameterizedTest(name = "{index}. 주문 상태: {0}")
    void serveWithNotAcceptedStatus(final EatInOrderStatus eatInOrderStatus) {
        final UUID firstMenuId = UUID.randomUUID();
        final UUID secondMenuId = UUID.randomUUID();
        final EatInOrderMenus eatInOrderMenus = new DefaultEatInOrderMenus(
                new DefaultEatInOrderMenu(firstMenuId, 16_000, true),
                new DefaultEatInOrderMenu(secondMenuId, 16_000, true)
        );

        final EatInOrderLineItem firstEatInOrderLineItem = new EatInOrderLineItem(firstMenuId, "후라이드 치킨", 16_000, 1);
        final EatInOrderLineItem secondEatInOrderLineItem = new EatInOrderLineItem(secondMenuId, "양념 치킨", 16_000, 1);
        final EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems(eatInOrderMenus, firstEatInOrderLineItem, secondEatInOrderLineItem);

        final EatInOrder eatInOrder = new EatInOrder(
                new EatInOrderId(), eatInOrderStatus, new EatInOrderDateTime(), eatInOrderLineItems, new OrderTableId()
        );
        assertThatThrownBy(eatInOrder::served)
                .isExactlyInstanceOf(IllegalStateException.class);
    }

    @DisplayName("수락중인 매장 주문을 주문 서빙으로 변경한다.")
    @Test
    void serve() {
        final UUID firstMenuId = UUID.randomUUID();
        final UUID secondMenuId = UUID.randomUUID();
        final EatInOrderMenus eatInOrderMenus = new DefaultEatInOrderMenus(
                new DefaultEatInOrderMenu(firstMenuId, 16_000, true),
                new DefaultEatInOrderMenu(secondMenuId, 16_000, true)
        );

        final EatInOrderLineItem firstEatInOrderLineItem = new EatInOrderLineItem(firstMenuId, "후라이드 치킨", 16_000, 1);
        final EatInOrderLineItem secondEatInOrderLineItem = new EatInOrderLineItem(secondMenuId, "양념 치킨", 16_000, 1);
        final EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems(eatInOrderMenus, firstEatInOrderLineItem, secondEatInOrderLineItem);

        final EatInOrder eatInOrder = new EatInOrder(
                new EatInOrderId(), EatInOrderStatus.ACCEPTED, new EatInOrderDateTime(), eatInOrderLineItems, new OrderTableId()
        );
        eatInOrder.served();
        assertThat(eatInOrder.isSameStatus(EatInOrderStatus.SERVED)).isTrue();
    }

    @DisplayName("서빙중인 매장 주문이 아니면 주문 완료를 변경할 수 없다.")
    @EnumSource(value = EatInOrderStatus.class, names = {"WAITING", "ACCEPTED", "COMPLETED"})
    @ParameterizedTest(name = "{index}. 주문 상태: {0}")
    void completeWithNotServedStatus(final EatInOrderStatus eatInOrderStatus) {
        final UUID firstMenuId = UUID.randomUUID();
        final UUID secondMenuId = UUID.randomUUID();
        final EatInOrderMenus eatInOrderMenus = new DefaultEatInOrderMenus(
                new DefaultEatInOrderMenu(firstMenuId, 16_000, true),
                new DefaultEatInOrderMenu(secondMenuId, 16_000, true)
        );

        final EatInOrderLineItem firstEatInOrderLineItem = new EatInOrderLineItem(firstMenuId, "후라이드 치킨", 16_000, 1);
        final EatInOrderLineItem secondEatInOrderLineItem = new EatInOrderLineItem(secondMenuId, "양념 치킨", 16_000, 1);
        final EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems(eatInOrderMenus, firstEatInOrderLineItem, secondEatInOrderLineItem);

        final EatInOrder eatInOrder = new EatInOrder(
                new EatInOrderId(), eatInOrderStatus, new EatInOrderDateTime(), eatInOrderLineItems, new OrderTableId()
        );
        assertThatThrownBy(eatInOrder::completed)
                .isExactlyInstanceOf(IllegalStateException.class);
    }

    @DisplayName("서빙중인 매장 주문을 주문 완료로 변경한다.")
    @Test
    void complete() {
        final UUID firstMenuId = UUID.randomUUID();
        final UUID secondMenuId = UUID.randomUUID();
        final EatInOrderMenus eatInOrderMenus = new DefaultEatInOrderMenus(
                new DefaultEatInOrderMenu(firstMenuId, 16_000, true),
                new DefaultEatInOrderMenu(secondMenuId, 16_000, true)
        );

        final EatInOrderLineItem firstEatInOrderLineItem = new EatInOrderLineItem(firstMenuId, "후라이드 치킨", 16_000, 1);
        final EatInOrderLineItem secondEatInOrderLineItem = new EatInOrderLineItem(secondMenuId, "양념 치킨", 16_000, 1);
        final EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems(eatInOrderMenus, firstEatInOrderLineItem, secondEatInOrderLineItem);

        final EatInOrder eatInOrder = new EatInOrder(
                new EatInOrderId(), EatInOrderStatus.SERVED, new EatInOrderDateTime(),
                eatInOrderLineItems, new OrderTableId()
        );
        eatInOrder.completed();
        assertThat(eatInOrder.isSameStatus(EatInOrderStatus.COMPLETED)).isTrue();
    }
}
