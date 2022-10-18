package kitchenpos.eatinorders.application;

import static kitchenpos.eatinorders.EatInOrderFixtures.*;
import static kitchenpos.eatinordertables.EatInOrderTableFixtures.eatInOrderTable;
import static kitchenpos.menus.MenuFixtures.menu;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.eatinorders.domain.*;
import kitchenpos.eatinorders.ui.request.EatInOrderCreateRequest;
import kitchenpos.eatinorders.ui.request.EatInOrderLineItemCreateRequest;
import kitchenpos.eatinorders.ui.response.EatInOrderResponse;
import kitchenpos.eatinordertables.application.EatInOrderTableOccupiedCheckerImpl;
import kitchenpos.eatinordertables.domain.EatInOrderTable;
import kitchenpos.eatinordertables.domain.EatInOrderTableRepository;
import kitchenpos.eatinordertables.domain.InMemoryEatInOrderTableRepository;
import kitchenpos.menus.domain.InMemoryMenuRepository;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EatInOrderServiceTest {

    private EatInOrderRepository eatInOrderRepository;
    private MenuRepository menuRepository;
    private EatInOrderTableRepository eatInOrderTableRepository;
    private EatInOrderService eatInOrderService;

    @BeforeEach
    void setUp() {
        eatInOrderRepository = new InMemoryEatInOrderRepository();
        menuRepository = new InMemoryMenuRepository();
        eatInOrderTableRepository = new InMemoryEatInOrderTableRepository();
        FakeMenuPriceReader menuPriceReader = new FakeMenuPriceReader(menuRepository);
        eatInOrderService = new EatInOrderService(
            eatInOrderRepository,
            new EatInOrderTableOccupiedCheckerImpl(eatInOrderTableRepository),
            menuPriceReader
        );
    }

    @DisplayName("1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다.")
    @Test
    void createEatInOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L)).getId();
        final UUID eatInOrderTableId = eatInOrderTableRepository.save(eatInOrderTable(true, 4))
            .getId();
        final EatInOrderCreateRequest request = createOrderRequest(
            eatInOrderTableId,
            createOrderLineItemRequest(menuId, 3L)
        );
        final EatInOrderResponse response = eatInOrderService.create(request);
        assertThat(response).isNotNull();
        assertAll(
            () -> assertThat(response.getId()).isNotNull(),
            () -> assertThat(response.getStatus()).isEqualTo(EatInOrderStatus.WAITING),
            () -> assertThat(response.getEatInOrderDateTime()).isNotNull(),
            () -> assertThat(response.getEatInOrderLineItems()).hasSize(1),
            () -> assertThat(response.getEatInOrderTableId()).isEqualTo(request.getEatInOrderTableId())
        );
    }

    @DisplayName("메뉴가 없으면 등록할 수 없다.")
    @Test
    void create() {
        final EatInOrderLineItemCreateRequest orderLineItemRequest = createOrderLineItemRequest(
            INVALID_ID,
            1
        );
        final UUID eatInOrderTableId = eatInOrderTableRepository.save(eatInOrderTable(true, 4))
            .getId();
        final EatInOrderCreateRequest request = createOrderRequest(
            eatInOrderTableId,
            orderLineItemRequest
        );
        assertThatThrownBy(() -> eatInOrderService.create(request))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessage("ID 에 해당하는 메뉴를 찾을 수 없습니다.");
    }

    @DisplayName("주문 항목의 수량이 0 미만일 수 있다.")
    @ValueSource(longs = -1L)
    @ParameterizedTest
    void createEatInOrder(final long quantity) {
        final UUID menuId = menuRepository.save(menu(19_000L)).getId();
        final UUID orderTableId = eatInOrderTableRepository.save(eatInOrderTable(true, 4)).getId();
        final EatInOrderCreateRequest request = createOrderRequest(
            orderTableId,
            createOrderLineItemRequest(menuId, quantity)
        );
        assertDoesNotThrow(() -> eatInOrderService.create(request));
    }

    @DisplayName("빈 테이블에는 주문을 등록할 수 없다.")
    @Test
    void createEmptyTableEatInOrder() {
        final UUID menuId = menuRepository.save(menu(19_000L)).getId();
        final UUID orderTableId = eatInOrderTableRepository.save(eatInOrderTable(false, 0)).getId();
        final EatInOrderCreateRequest request = createOrderRequest(
            orderTableId, createOrderLineItemRequest(menuId, 3L)
        );
        assertThatThrownBy(() -> eatInOrderService.create(request))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
    @Test
    void createNotDisplayedMenuOrder() {
        Menu menu = menu(19_000L);
        menu.hide();
        final UUID menuId = menuRepository.save(menu).getId();
        EatInOrderTable eatInOrderTable = eatInOrderTable(false, 0);
        eatInOrderTable.sit();
        final UUID orderTableId = eatInOrderTableRepository.save(eatInOrderTable).getId();
        final EatInOrderCreateRequest request = createOrderRequest(
            orderTableId,
            createOrderLineItemRequest(menuId, 3L)
        );
        assertThatThrownBy(() -> eatInOrderService.create(request))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("메뉴가 전시중이지 않아 주문을 진행할 수 없습니다.");
    }


    // TODO: publish event 테스트
    @DisplayName("주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다.")
    @Test
    void completeEatInOrder() {
        final EatInOrderTable eatInOrderTable = eatInOrderTableRepository.save(eatInOrderTable(
            true,
            4
        ));
        final EatInOrder expected = eatInOrderRepository.save(eatInOrder(
            EatInOrderStatus.SERVED,
            eatInOrderTable.getId()
        ));
        final EatInOrderResponse response = eatInOrderService.complete(expected.getId());
        assertAll(
            () -> assertThat(response.getStatus()).isEqualTo(EatInOrderStatus.COMPLETED)
        );
    }

    // TODO: publish event 테스트
    @DisplayName("완료되지 않은 매장 주문이 있는 주문 테이블은 빈 테이블로 설정하지 않는다.")
    @Test
    void completeNotTable() {
        final EatInOrderTable eatInOrderTable = eatInOrderTableRepository.save(eatInOrderTable(
            true,
            4
        ));
        eatInOrderRepository.save(eatInOrder(EatInOrderStatus.ACCEPTED, eatInOrderTable.getId()));
        final EatInOrder expected = eatInOrderRepository.save(eatInOrder(
            EatInOrderStatus.SERVED,
            eatInOrderTable.getId()
        ));
        final EatInOrderResponse response = eatInOrderService.complete(expected.getId());
        assertAll(
            () -> assertThat(response.getStatus()).isEqualTo(EatInOrderStatus.COMPLETED)
        );
    }

    @DisplayName("주문의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        final EatInOrderTable eatInOrderTable = eatInOrderTableRepository.save(eatInOrderTable(
            true,
            4
        ));
        eatInOrderRepository.save(eatInOrder(EatInOrderStatus.SERVED, eatInOrderTable.getId()));
        eatInOrderRepository.save(eatInOrder(EatInOrderStatus.COMPLETED, eatInOrderTable.getId()));
        final List<EatInOrderResponse> responses = eatInOrderService.findAll();
        assertThat(responses).hasSize(2);
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

    private static EatInOrderLineItemCreateRequest createOrderLineItemRequest(
        UUID menuId,
        long quantity
    ) {
        return new EatInOrderLineItemCreateRequest(menuId, quantity);
    }
}
