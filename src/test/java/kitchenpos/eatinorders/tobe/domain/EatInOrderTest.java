package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.domain.order.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
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
}
