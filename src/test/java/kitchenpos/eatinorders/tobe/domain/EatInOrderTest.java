package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.domain.order.*;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class EatInOrderTest {

    @DisplayName("주문 테이블이 없으면 매장 주문을 생성할 수 없다.")
    @NullSource
    @ParameterizedTest(name = "주문 테이블: {0}")
    void createWithoutOrderTable(final EatInOrderTable eatInOrderTable) {
        final UUID firstMenuId = UUID.randomUUID();
        final UUID secondMenuId = UUID.randomUUID();
        final EatInOrderLineItem firstEatInOrderLineItem = new EatInOrderLineItem(1L, new EatInOrderLineItemMenu(firstMenuId, "후라이드 치킨", 16_000), 1);
        final EatInOrderLineItem secondEatInOrderLineItem = new EatInOrderLineItem(1L, new EatInOrderLineItemMenu(secondMenuId, "양념 치킨", 16_000), 1);
        final EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems(firstEatInOrderLineItem, secondEatInOrderLineItem);
        final EatInOrderMenus eatInOrderMenus = new DefaultEatInOrderMenus(
                new DefaultEatInOrderMenu(firstMenuId, 16_000, true),
                new DefaultEatInOrderMenu(secondMenuId, 16_000, true)
        );

        assertThatThrownBy(() -> new EatInOrder(eatInOrderLineItems, eatInOrderMenus, eatInOrderTable))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 항목 메뉴와 메뉴의 사이즈가 일치하지 않으면 매장 주문을 생성할 수 없다.")
    @Test
    void createWithDifferentMenuSize() {
        final UUID firstMenuId = UUID.randomUUID();
        final UUID secondMenuId = UUID.randomUUID();
        final EatInOrderLineItem firstEatInOrderLineItem = new EatInOrderLineItem(1L, new EatInOrderLineItemMenu(firstMenuId, "후라이드 치킨", 16_000), 1);
        final EatInOrderLineItem secondEatInOrderLineItem = new EatInOrderLineItem(1L, new EatInOrderLineItemMenu(secondMenuId, "양념 치킨", 16_000), 1);
        final EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems(firstEatInOrderLineItem, secondEatInOrderLineItem);
        final EatInOrderMenus eatInOrderMenus = new DefaultEatInOrderMenus(new DefaultEatInOrderMenu(firstMenuId, 16_000, true));
        final EatInOrderTable eatInOrderTable = new DefaultEatInOrderTable(UUID.randomUUID(), true);

        assertThatThrownBy(() -> new EatInOrder(eatInOrderLineItems, eatInOrderMenus, eatInOrderTable))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 항목 메뉴와 메뉴의 가격이 일치하지 않으면 매장 주문을 생성할 수 없다.")
    @Test
    void createWithDifferentMenuPrice() {
        final UUID firstMenuId = UUID.randomUUID();
        final UUID secondMenuId = UUID.randomUUID();
        final EatInOrderLineItem firstEatInOrderLineItem = new EatInOrderLineItem(1L, new EatInOrderLineItemMenu(firstMenuId, "후라이드 치킨", 16_000), 1);
        final EatInOrderLineItem secondEatInOrderLineItem = new EatInOrderLineItem(1L, new EatInOrderLineItemMenu(secondMenuId, "양념 치킨", 16_000), 1);
        final EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems(firstEatInOrderLineItem, secondEatInOrderLineItem);
        final EatInOrderMenus eatInOrderMenus = new DefaultEatInOrderMenus(
                new DefaultEatInOrderMenu(firstMenuId, 16_001, true),
                new DefaultEatInOrderMenu(secondMenuId, 16_001, true)
        );
        final EatInOrderTable eatInOrderTable = new DefaultEatInOrderTable(UUID.randomUUID(), true);

        assertThatThrownBy(() -> new EatInOrder(eatInOrderLineItems, eatInOrderMenus, eatInOrderTable))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 테이블이 비어있으면 매장 주문을 생성할 수 없다.")
    @Test
    void createWithEmptyOrderTable() {
        final UUID firstMenuId = UUID.randomUUID();
        final UUID secondMenuId = UUID.randomUUID();
        final EatInOrderLineItem firstEatInOrderLineItem = new EatInOrderLineItem(1L, new EatInOrderLineItemMenu(firstMenuId, "후라이드 치킨", 16_000), 1);
        final EatInOrderLineItem secondEatInOrderLineItem = new EatInOrderLineItem(1L, new EatInOrderLineItemMenu(secondMenuId, "양념 치킨", 16_000), 1);
        final EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems(firstEatInOrderLineItem, secondEatInOrderLineItem);
        final EatInOrderMenus eatInOrderMenus = new DefaultEatInOrderMenus(
                new DefaultEatInOrderMenu(firstMenuId, 16_000, true),
                new DefaultEatInOrderMenu(secondMenuId, 16_000, true)
        );
        final EatInOrderTable eatInOrderTable = new DefaultEatInOrderTable(UUID.randomUUID(), false);

        assertThatThrownBy(() -> new EatInOrder(eatInOrderLineItems, eatInOrderMenus, eatInOrderTable))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("매장 주문을 생성한다.")
    @Test
    void create() {
        final UUID firstMenuId = UUID.randomUUID();
        final UUID secondMenuId = UUID.randomUUID();
        final EatInOrderLineItem firstEatInOrderLineItem = new EatInOrderLineItem(1L, new EatInOrderLineItemMenu(firstMenuId, "후라이드 치킨", 16_000), 1);
        final EatInOrderLineItem secondEatInOrderLineItem = new EatInOrderLineItem(1L, new EatInOrderLineItemMenu(secondMenuId, "양념 치킨", 16_000), 1);
        final EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems(firstEatInOrderLineItem, secondEatInOrderLineItem);
        final EatInOrderMenus eatInOrderMenus = new DefaultEatInOrderMenus(
                new DefaultEatInOrderMenu(firstMenuId, 16_000, true),
                new DefaultEatInOrderMenu(secondMenuId, 16_000, true)
        );
        final EatInOrderTable eatInOrderTable = new DefaultEatInOrderTable(UUID.randomUUID(), true);

        final EatInOrder eatInOrder = new EatInOrder(eatInOrderLineItems, eatInOrderMenus, eatInOrderTable);

        assertAll(
                () -> assertThat(eatInOrder.getId()).isNotNull(),
                () -> assertThat(eatInOrder.status()).isEqualTo(EatInOrderStatus.WAITING)
        );
    }

    @DisplayName("대기중인 매장 주문이 아니면 주문 수락을 변경할 수 없다.")
    @EnumSource(value = EatInOrderStatus.class, names = {"ACCEPTED", "SERVED", "COMPLETED"})
    @ParameterizedTest(name = "{index}. 주문 상태: {0}")
    void acceptWithNotWaitingStatus(final EatInOrderStatus eatInOrderStatus) {
        final UUID firstMenuId = UUID.randomUUID();
        final UUID secondMenuId = UUID.randomUUID();
        final EatInOrderLineItem firstEatInOrderLineItem = new EatInOrderLineItem(1L, new EatInOrderLineItemMenu(firstMenuId, "후라이드 치킨", 16_000), 1);
        final EatInOrderLineItem secondEatInOrderLineItem = new EatInOrderLineItem(1L, new EatInOrderLineItemMenu(secondMenuId, "양념 치킨", 16_000), 1);
        final EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems(firstEatInOrderLineItem, secondEatInOrderLineItem);
        final EatInOrderMenus eatInOrderMenus = new DefaultEatInOrderMenus(
                new DefaultEatInOrderMenu(firstMenuId, 16_000, true),
                new DefaultEatInOrderMenu(secondMenuId, 16_000, true)
        );
        final EatInOrderTable eatInOrderTable = new DefaultEatInOrderTable(UUID.randomUUID(), true);

        final EatInOrder eatInOrder = new EatInOrder(
                new EatInOrderId(), eatInOrderStatus, new EatInOrderDateTime(),
                eatInOrderLineItems, eatInOrderMenus, eatInOrderTable
        );
        assertThatThrownBy(eatInOrder::accepted)
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("대기중인 매장 주문을 주문 수락으로 변경한다.")
    @Test
    void accept() {
        final UUID firstMenuId = UUID.randomUUID();
        final UUID secondMenuId = UUID.randomUUID();
        final EatInOrderLineItem firstEatInOrderLineItem = new EatInOrderLineItem(1L, new EatInOrderLineItemMenu(firstMenuId, "후라이드 치킨", 16_000), 1);
        final EatInOrderLineItem secondEatInOrderLineItem = new EatInOrderLineItem(1L, new EatInOrderLineItemMenu(secondMenuId, "양념 치킨", 16_000), 1);
        final EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems(firstEatInOrderLineItem, secondEatInOrderLineItem);
        final EatInOrderMenus eatInOrderMenus = new DefaultEatInOrderMenus(
                new DefaultEatInOrderMenu(firstMenuId, 16_000, true),
                new DefaultEatInOrderMenu(secondMenuId, 16_000, true)
        );
        final EatInOrderTable eatInOrderTable = new DefaultEatInOrderTable(UUID.randomUUID(), true);

        final EatInOrder eatInOrder = new EatInOrder(eatInOrderLineItems, eatInOrderMenus, eatInOrderTable);
        eatInOrder.accepted();

        assertThat(eatInOrder.status()).isEqualTo(EatInOrderStatus.ACCEPTED);
    }

    @DisplayName("수락중인 매장 주문이 아니면 주문 서빙을 변경할 수 없다.")
    @EnumSource(value = EatInOrderStatus.class, names = {"WAITING", "SERVED", "COMPLETED"})
    @ParameterizedTest(name = "{index}. 주문 상태: {0}")
    void serveWithNotAcceptedStatus(final EatInOrderStatus eatInOrderStatus) {
        final UUID firstMenuId = UUID.randomUUID();
        final UUID secondMenuId = UUID.randomUUID();
        final EatInOrderLineItem firstEatInOrderLineItem = new EatInOrderLineItem(1L, new EatInOrderLineItemMenu(firstMenuId, "후라이드 치킨", 16_000), 1);
        final EatInOrderLineItem secondEatInOrderLineItem = new EatInOrderLineItem(1L, new EatInOrderLineItemMenu(secondMenuId, "양념 치킨", 16_000), 1);
        final EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems(firstEatInOrderLineItem, secondEatInOrderLineItem);
        final EatInOrderMenus eatInOrderMenus = new DefaultEatInOrderMenus(
                new DefaultEatInOrderMenu(firstMenuId, 16_000, true),
                new DefaultEatInOrderMenu(secondMenuId, 16_000, true)
        );
        final EatInOrderTable eatInOrderTable = new DefaultEatInOrderTable(UUID.randomUUID(), true);

        final EatInOrder eatInOrder = new EatInOrder(
                new EatInOrderId(), eatInOrderStatus, new EatInOrderDateTime(),
                eatInOrderLineItems, eatInOrderMenus, eatInOrderTable
        );
        assertThatThrownBy(eatInOrder::served)
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("수락중인 매장 주문을 주문 서빙으로 변경한다.")
    @Test
    void serve() {
        final UUID firstMenuId = UUID.randomUUID();
        final UUID secondMenuId = UUID.randomUUID();
        final EatInOrderLineItem firstEatInOrderLineItem = new EatInOrderLineItem(1L, new EatInOrderLineItemMenu(firstMenuId, "후라이드 치킨", 16_000), 1);
        final EatInOrderLineItem secondEatInOrderLineItem = new EatInOrderLineItem(1L, new EatInOrderLineItemMenu(secondMenuId, "양념 치킨", 16_000), 1);
        final EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems(firstEatInOrderLineItem, secondEatInOrderLineItem);
        final EatInOrderMenus eatInOrderMenus = new DefaultEatInOrderMenus(
                new DefaultEatInOrderMenu(firstMenuId, 16_000, true),
                new DefaultEatInOrderMenu(secondMenuId, 16_000, true)
        );
        final EatInOrderTable eatInOrderTable = new DefaultEatInOrderTable(UUID.randomUUID(), true);

        final EatInOrder eatInOrder = new EatInOrder(eatInOrderLineItems, eatInOrderMenus, eatInOrderTable);
        eatInOrder.accepted();
        eatInOrder.served();

        assertThat(eatInOrder.status()).isEqualTo(EatInOrderStatus.SERVED);
    }
}
