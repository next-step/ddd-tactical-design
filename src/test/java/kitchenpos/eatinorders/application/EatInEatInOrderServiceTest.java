package kitchenpos.eatinorders.application;

import static kitchenpos.eatinorders.EatInOrderFixtures.*;
import static kitchenpos.menus.MenuFixtures.menu;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;
import java.util.*;
import kitchenpos.eatinorders.domain.*;
import kitchenpos.eatinorders.ui.request.EatInOrderCreateRequest;
import kitchenpos.eatinorders.ui.request.EatInOrderLineItemCreateRequest;
import kitchenpos.eatinorders.ui.response.EatInOrderResponse;
import kitchenpos.menus.domain.InMemoryMenuRepository;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.orders.infra.FakeKitchenridersClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

class EatInEatInOrderServiceTest {
    private EatInOrderRepository eatInOrderRepository;
    private MenuRepository menuRepository;
    private EatInOrderTableRepository eatInOrderTableRepository;
    private FakeKitchenridersClient kitchenridersClient;
    private EatInOrderService eatInOrderService;

    @BeforeEach
    void setUp() {
        eatInOrderRepository = new InMemoryEatInOrderRepository();
        menuRepository = new InMemoryMenuRepository();
        eatInOrderTableRepository = new InMemoryEatInOrderTableRepository();
        kitchenridersClient = new FakeKitchenridersClient();
        eatInOrderService = new EatInOrderService(eatInOrderRepository, menuRepository,
            eatInOrderTableRepository, kitchenridersClient);
    }

    @DisplayName("1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다.")
    @Test
    void createEatInOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L)).getId();
        final UUID eatInOrderTableId = eatInOrderTableRepository.save(eatInOrderTable(true, 4)).getId();
        final EatInOrderCreateRequest request = createOrderRequest(eatInOrderTableId, createOrderLineItemRequest(menuId, 19_000L, 3L));
        final EatInOrderResponse response = eatInOrderService.create(request);
        assertThat(response).isNotNull();
        assertAll(
            () -> assertThat(response.getId()).isNotNull(),
            () -> assertThat(response.getStatus()).isEqualTo(EatInOrderStatus.WAITING),
            () -> assertThat(response.getEatInOrderDateTime()).isNotNull(),
            () -> assertThat(response.getEatInOrderLineItems()).hasSize(1),
            () -> assertThat(response.getEatInOrderTable().getId()).isEqualTo(request.getEatInOrderTableId())
        );
    }

    @DisplayName("메뉴가 없으면 등록할 수 없다.")
    @Test
    void create() {
        final EatInOrderLineItemCreateRequest orderLineItemRequest = createOrderLineItemRequest(INVALID_ID, 19_000L, 1);
        final UUID eatInOrderTableId = eatInOrderTableRepository.save(eatInOrderTable(true, 4)).getId();
        final EatInOrderCreateRequest request = createOrderRequest(eatInOrderTableId, orderLineItemRequest);
        assertThatThrownBy(() -> eatInOrderService.create(request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 항목의 수량이 0 미만일 수 있다.")
    @ValueSource(longs = -1L)
    @ParameterizedTest
    void createEatInOrder(final long quantity) {
        final UUID menuId = menuRepository.save(menu(19_000L)).getId();
        final UUID orderTableId = eatInOrderTableRepository.save(eatInOrderTable(true, 4)).getId();
        final EatInOrderCreateRequest request = createOrderRequest(orderTableId, createOrderLineItemRequest(menuId, 19_000L, quantity));
        assertDoesNotThrow(() -> eatInOrderService.create(request));
    }

    @DisplayName("빈 테이블에는 매장 주문을 등록할 수 없다.")
    @Test
    void createEmptyTableEatInOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L)).getId();
        final UUID orderTableId = eatInOrderTableRepository.save(eatInOrderTable(false, 0)).getId();
        final EatInOrderCreateRequest request = createOrderRequest(
            orderTableId, createOrderLineItemRequest(menuId, 19_000L, 3L)
        );
        assertThatThrownBy(() -> eatInOrderService.create(request))
            .isInstanceOf(IllegalStateException.class);
    }

    @Disabled
    @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
    @Test
    void createNotDisplayedMenuOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L)).getId();
        final UUID orderTableId = eatInOrderTableRepository.save(eatInOrderTable(false, 0)).getId();
        final EatInOrderCreateRequest request = createOrderRequest(orderTableId, createOrderLineItemRequest(menuId, 19_000L, 3L));
        assertThatThrownBy(() -> eatInOrderService.create(request))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
    @Test
    void createNotMatchedMenuPriceOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L)).getId();
        final UUID orderTableId = eatInOrderTableRepository.save(eatInOrderTable(false, 0)).getId();
        final EatInOrderCreateRequest request = createOrderRequest(orderTableId, createOrderLineItemRequest(menuId, 16_000L, 3L));
        assertThatThrownBy(() -> eatInOrderService.create(request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문을 접수한다.")
    @Test
    void accept() {
        final UUID orderId = eatInOrderRepository.save(eatInOrder(EatInOrderStatus.WAITING, eatInOrderTable(true, 4))).getId();
        final EatInOrder actual = eatInOrderService.accept(orderId);
        assertThat(actual.getStatus()).isEqualTo(EatInOrderStatus.ACCEPTED);
    }

    @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
    @EnumSource(value = EatInOrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void accept(final EatInOrderStatus status) {
        final UUID orderId = eatInOrderRepository.save(eatInOrder(status, eatInOrderTable(true, 4))).getId();
        assertThatThrownBy(() -> eatInOrderService.accept(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 서빙한다.")
    @Test
    void serve() {
        final EatInOrderTable eatInOrderTable = eatInOrderTableRepository.save(eatInOrderTable(true, 4));
        final UUID orderId = eatInOrderRepository.save(eatInOrder(
            EatInOrderStatus.ACCEPTED,
            eatInOrderTable
        )).getId();
        final EatInOrder actual = eatInOrderService.serve(orderId);
        assertThat(actual.getStatus()).isEqualTo(EatInOrderStatus.SERVED);
    }

    @DisplayName("접수된 주문만 서빙할 수 있다.")
    @EnumSource(value = EatInOrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void serve(final EatInOrderStatus status) {
        final EatInOrderTable eatInOrderTable = eatInOrderTableRepository.save(eatInOrderTable(true, 4));
        final UUID orderId = eatInOrderRepository.save(eatInOrder(status, eatInOrderTable)).getId();
        assertThatThrownBy(() -> eatInOrderService.serve(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 완료한다.")
    @Test
    void complete() {
        final EatInOrderTable eatInOrderTable = eatInOrderTableRepository.save(eatInOrderTable(true, 4));
        final EatInOrder expected = eatInOrderRepository.save(eatInOrder(
            EatInOrderStatus.SERVED,
            eatInOrderTable
        ));
        final EatInOrder actual = eatInOrderService.complete(expected.getId());
        assertThat(actual.getStatus()).isEqualTo(EatInOrderStatus.COMPLETED);
    }

    @DisplayName("매장 주문의 경우 서빙된 주문만 완료할 수 있다.")
    @EnumSource(value = EatInOrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeTakeoutAndEatInOrder(final EatInOrderStatus status) {
        final EatInOrderTable eatInOrderTable = eatInOrderTableRepository.save(eatInOrderTable(true, 4));
        final UUID orderId = eatInOrderRepository.save(eatInOrder(status, eatInOrderTable)).getId();
        assertThatThrownBy(() -> eatInOrderService.complete(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다.")
    @Test
    void completeEatInOrder() {
        final EatInOrderTable eatInOrderTable = eatInOrderTableRepository.save(eatInOrderTable(true, 4));
        final EatInOrder expected = eatInOrderRepository.save(eatInOrder(
            EatInOrderStatus.SERVED,
            eatInOrderTable
        ));
        final EatInOrder actual = eatInOrderService.complete(expected.getId());
        assertAll(
            () -> assertThat(actual.getStatus()).isEqualTo(EatInOrderStatus.COMPLETED),
            () -> assertThat(eatInOrderTableRepository.findById(eatInOrderTable.getId()).get().isOccupied()).isFalse(),
            () -> assertThat(eatInOrderTableRepository.findById(eatInOrderTable.getId()).get().getNumberOfGuestsValue()).isEqualTo(0)
        );
    }

    @DisplayName("완료되지 않은 매장 주문이 있는 주문 테이블은 빈 테이블로 설정하지 않는다.")
    @Test
    void completeNotTable() {
        final EatInOrderTable eatInOrderTable = eatInOrderTableRepository.save(eatInOrderTable(true, 4));
        eatInOrderRepository.save(eatInOrder(EatInOrderStatus.ACCEPTED, eatInOrderTable));
        final EatInOrder expected = eatInOrderRepository.save(eatInOrder(
            EatInOrderStatus.SERVED,
            eatInOrderTable
        ));
        final EatInOrder actual = eatInOrderService.complete(expected.getId());
        assertAll(
            () -> assertThat(actual.getStatus()).isEqualTo(EatInOrderStatus.COMPLETED),
            () -> assertThat(eatInOrderTableRepository.findById(eatInOrderTable.getId()).get().isOccupied()).isTrue(),
            () -> assertThat(eatInOrderTableRepository.findById(eatInOrderTable.getId()).get().getNumberOfGuestsValue()).isEqualTo(4)
        );
    }

    @DisplayName("주문의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        final EatInOrderTable eatInOrderTable = eatInOrderTableRepository.save(eatInOrderTable(true, 4));
        eatInOrderRepository.save(eatInOrder(EatInOrderStatus.SERVED, eatInOrderTable));
        eatInOrderRepository.save(eatInOrder(EatInOrderStatus.COMPLETED, eatInOrderTable));
        final List<EatInOrder> actual = eatInOrderService.findAll();
        assertThat(actual).hasSize(2);
    }

    private EatInOrderCreateRequest createOrderRequest(
        UUID eatInOrderTableId,
        EatInOrderLineItemCreateRequest... eatInOrderLineItemCreateRequests
    ) {
        return createOrderRequest(eatInOrderTableId, List.of(eatInOrderLineItemCreateRequests));
    }

    private EatInOrderCreateRequest createOrderRequest(
        UUID eatInOrderTableId,
        List<EatInOrderLineItemCreateRequest> eatInOrderLineItemCreateRequests
    ) {
        return new EatInOrderCreateRequest(eatInOrderTableId, eatInOrderLineItemCreateRequests);
    }

    private static EatInOrderLineItemCreateRequest createOrderLineItemRequest(UUID menuId, long price, long quantity) {
        return new EatInOrderLineItemCreateRequest(menuId, BigDecimal.valueOf(price), quantity);
    }
}
