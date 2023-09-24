package kitchenpos.eatinorders.domain.orders;

import kitchenpos.eatin_orders.infrastructure.InMemoryOrderTableRepository;
import kitchenpos.eatinorders.domain.ordertables.OrderTable;
import kitchenpos.eatinorders.domain.ordertables.OrderTableRepository;
import kitchenpos.eatinorders.infrastructure.MenuClientImpl;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static kitchenpos.Fixtures.menu;
import static kitchenpos.eatin_orders.EatInOrderFixtures.*;
import static kitchenpos.eatin_orders.EatInOrderFixtures.waitingOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EatInOrderTest {

    private ProductRepository productRepository;
    private MenuRepository menuRepository;
    private OrderTableRepository orderTableRepository;
    private MenuClient menuClient;
    private EatInOrderPolicy eatInOrderPolicy;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        menuRepository = new InMemoryMenuRepository();
        orderTableRepository = new InMemoryOrderTableRepository();
        menuClient = new MenuClientImpl(menuRepository);
        eatInOrderPolicy = new EatInOrderPolicy(orderTableRepository);
    }

    @DisplayName("Waiting인 EatInOrder을 생성할 수 있다.")
    @Test
    void createWaitingOrder() {
        Menu menu = menuRepository.save(menu(productRepository));
        final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        final EatInOrder eatInOrder = EatInOrder.waitingOrder(
                UUID.randomUUID(),
                LocalDateTime.of(2020, 1, 1, 12, 0, 0),
                EatInOrderLineItems.from(List.of(eatInOrderLineItemMaterial(menu.getId())), menuClient),
                orderTable.getId(),
                eatInOrderPolicy
        );
        assertAll(
                () -> assertNotNull(eatInOrder),
                () -> assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.WAITING),
                () -> assertThat(eatInOrder.getOrderTableId()).isEqualTo(orderTable.getId()),
                () -> assertThat(eatInOrder.getOrderLineItems().getOrderLineItems()).hasSize(1)
        );
    }

    @DisplayName("OrderTable이 존재하지 않으면 Waiting 중인 EatInOrder를 생성할 수 없다.")
    @Test
    void waitingOrderWithNonExistOrderTable() {
        Menu menu = menuRepository.save(menu(productRepository));
        assertThatThrownBy(() -> EatInOrder.waitingOrder(
                UUID.randomUUID(),
                LocalDateTime.of(2020, 1, 1, 12, 0, 0),
                EatInOrderLineItems.from(List.of(eatInOrderLineItemMaterial(menu.getId())), menuClient),
                UUID.randomUUID(),
                eatInOrderPolicy
        )).isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("Clear된 OrderTable에 EatInOrder을 생성할 수 없다.")
    @Test
    void waitingOrderWithEmptyOrderTable() {
        Menu menu = menuRepository.save(menu(productRepository));
        final OrderTable orderTable = orderTableRepository.save(orderTable(false, 4));
        assertThatThrownBy(() -> EatInOrder.waitingOrder(
                UUID.randomUUID(),
                LocalDateTime.of(2020, 1, 1, 12, 0, 0),
                EatInOrderLineItems.from(List.of(eatInOrderLineItemMaterial(menu.getId())), menuClient),
                orderTable.getId(),
                eatInOrderPolicy
        )).isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("Waiting인 EatInOrder을 Accept할 수 있다.")
    @Test
    void accept() {
        Menu menu = menuRepository.save(menu(productRepository));
        final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        final EatInOrder eatInOrder = EatInOrder.waitingOrder(
                UUID.randomUUID(),
                LocalDateTime.of(2020, 1, 1, 12, 0, 0),
                EatInOrderLineItems.from(List.of(eatInOrderLineItemMaterial(menu.getId())), menuClient),
                orderTable.getId(),
                eatInOrderPolicy
        );
        eatInOrder.accept();
        assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @DisplayName("Waiting이 아닌 EatInOrder을 Accept할 수 없다.")
    @Test
    void acceptWithNotWaitingOrder() {
        ordersOtherThanWaitingOrder().forEach(eatInOrder ->
                assertThatThrownBy(eatInOrder::accept).isInstanceOf(IllegalStateException.class)
        );
    }

    private List<EatInOrder> ordersOtherThanWaitingOrder() {
        final Menu menu = menuRepository.save(menu(productRepository));
        OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        return Arrays.asList(
                acceptedOrder(orderTable, menu, menuClient, eatInOrderPolicy),
                servedOrder(orderTable, menu, menuClient, eatInOrderPolicy),
                completedOrder(orderTable, menu, menuClient, eatInOrderPolicy)
        );
    }

    @DisplayName("Accepted인 EatInOrder를 Serve할 수 있다")
    @Test
    void served() {
        Menu menu = menuRepository.save(menu(productRepository));
        final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        final EatInOrder eatInOrder = EatInOrder.waitingOrder(
                UUID.randomUUID(),
                LocalDateTime.of(2020, 1, 1, 12, 0, 0),
                EatInOrderLineItems.from(List.of(eatInOrderLineItemMaterial(menu.getId())), menuClient),
                orderTable.getId(),
                eatInOrderPolicy
        );
        eatInOrder.accept();
        eatInOrder.served();
        assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.SERVED);
    }

    @DisplayName("Accepted가 아닌 EatInOrder는 Serve 할 수 없다.")
    @Test
    void servedWithNotAcceptedOrder() {
        ordersOtherThanAcceptedOrder().forEach(eatInOrder ->
                assertThatThrownBy(eatInOrder::served).isInstanceOf(IllegalStateException.class)
        );
    }

    private List<EatInOrder> ordersOtherThanAcceptedOrder() {
        final Menu menu = menuRepository.save(menu(productRepository));
        OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        return Arrays.asList(
                waitingOrder(orderTable, menu, menuClient, eatInOrderPolicy),
                servedOrder(orderTable, menu, menuClient, eatInOrderPolicy),
                completedOrder(orderTable, menu, menuClient, eatInOrderPolicy)
        );
    }

    @DisplayName("Served된 EatInOrder을 Complete할 수 있다.")
    @Test
    void complete() {
        Menu menu = menuRepository.save(menu(productRepository));
        final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        final EatInOrder eatInOrder = EatInOrder.waitingOrder(
                UUID.randomUUID(),
                LocalDateTime.of(2020, 1, 1, 12, 0, 0),
                EatInOrderLineItems.from(List.of(eatInOrderLineItemMaterial(menu.getId())), menuClient),
                orderTable.getId(),
                eatInOrderPolicy
        );
        eatInOrder.accept();
        eatInOrder.served();
        eatInOrder.complete(eatInOrderPolicy);
        assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.COMPLETED);
    }

    @DisplayName("Served가 아닌 EatInOrder는 Completed 할 수 없다.")
    @Test
    void servedWithNotServedOrder() {
        ordersOtherThanServedOrder().forEach(eatInOrder ->
                assertThatThrownBy(() -> eatInOrder.complete(eatInOrderPolicy)).isInstanceOf(IllegalStateException.class)
        );
    }

    private List<EatInOrder> ordersOtherThanServedOrder() {
        final Menu menu = menuRepository.save(menu(productRepository));
        OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        return Arrays.asList(
                waitingOrder(orderTable, menu, menuClient, eatInOrderPolicy),
                acceptedOrder(orderTable, menu, menuClient, eatInOrderPolicy),
                completedOrder(orderTable, menu, menuClient, eatInOrderPolicy)
        );
    }

    @DisplayName("EatInOrder가 Competed될 때 OrderTable을 비울 수 있다.")
    @Test
    void eatInOrderCompleteAndThenClearOrderTable() {
        Menu menu = menuRepository.save(menu(productRepository));
        final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        final EatInOrder eatInOrder = EatInOrder.waitingOrder(
                UUID.randomUUID(),
                LocalDateTime.of(2020, 1, 1, 12, 0, 0),
                EatInOrderLineItems.from(List.of(eatInOrderLineItemMaterial(menu.getId())), menuClient),
                orderTable.getId(),
                eatInOrderPolicy
        );
        eatInOrder.accept();
        eatInOrder.served();
        eatInOrder.complete(eatInOrderPolicy);
        OrderTable actual = orderTableRepository.findById(orderTable.getId())
                .orElseThrow(() -> new NoSuchElementException("OrderTable이 존재하지 않습니다."));
        assertThat(actual.isClear()).isTrue();
    }
}
