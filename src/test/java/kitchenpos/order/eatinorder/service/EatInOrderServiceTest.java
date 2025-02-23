package kitchenpos.order.eatinorder.service;

import static kitchenpos.TestFixtureFactory.createEatInOrderRequestWithEmptyTable;
import static kitchenpos.TestFixtureFactory.createEmptyOrderTable;
import static kitchenpos.TestFixtureFactory.createMenuWithProductAndGroup;
import static kitchenpos.TestFixtureFactory.createUsingOrderTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import kitchenpos.menu.domain.model.Menu;
import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.order.eatinorder.domain.model.EatInOrder;
import kitchenpos.order.eatinorder.domain.model.EatInOrderFactory;
import kitchenpos.order.eatinorder.domain.model.EatInOrderFlow;
import kitchenpos.order.common.model.OrderLineItemValidator;
import kitchenpos.order.eatinorder.domain.model.OrderTable;
import kitchenpos.order.eatinorder.domain.repository.EatInOrderRepository;
import kitchenpos.order.eatinorder.domain.repository.OrderTableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EatInOrderServiceTest {

    private EatInOrderService eatInOrderService;
    private EatInOrderRepository eatInOrderRepository;
    private MenuRepository menuRepository;
    private OrderTableRepository orderTableRepository;
    private OrderLineItemValidator orderLineItemValidator;
    private EatInOrderFactory eatInOrderFactory;

    @BeforeEach
    void setUp() {
        eatInOrderRepository = mock(EatInOrderRepository.class);
        menuRepository = mock(MenuRepository.class);
        orderTableRepository = mock(OrderTableRepository.class);
        orderLineItemValidator = new OrderLineItemValidator(menuRepository);
        eatInOrderFactory = new EatInOrderFactory(orderLineItemValidator, orderTableRepository);
        eatInOrderService = new EatInOrderService(eatInOrderRepository, menuRepository, eatInOrderFactory);
    }

    @Test
    @DisplayName("이용 중이지 않은 테이블에서 주문 시 예외가 발생한다.")
    void not_occupied_table_order_exception() {
        // given
        Menu menu = createMenuWithProductAndGroup();
        OrderTable orderTable = createEmptyOrderTable();
        EatInOrder request = createEatInOrderRequestWithEmptyTable(menu, EatInOrderFlow.WAITING);

        when(menuRepository.findAllByIdIn(anyList())).thenReturn(List.of(menu));
        when(menuRepository.findById(any())).thenReturn(Optional.of(menu));
        when(orderTableRepository.findById(any())).thenReturn(Optional.of(orderTable));

        // when // then
        assertThatThrownBy(() -> eatInOrderService.create(request))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("매장 식사 주문 완료 시 다른 주문이 없으면 테이블을 비운다")
    void clear_table_with_complete_status() {
        // given
        Menu menu = createMenuWithProductAndGroup();
        OrderTable orderTable = createUsingOrderTable();
        EatInOrder order = createEatInOrderRequestWithEmptyTable(menu, EatInOrderFlow.SERVED);
        order.occupyOrderTable(orderTable);

        when(eatInOrderRepository.findById(any())).thenReturn(Optional.of(order));
        when(eatInOrderRepository.existsByOrderTableAndEatInOrderFlowNot(any(), any())).thenReturn(false);

        // when
        eatInOrderService.complete(order.getId());

        // then
        assertThat(orderTable.isOccupied()).isFalse();
        assertThat(orderTable.getNumberOfGuests()).isZero();
    }

    @ParameterizedTest
    @EnumSource(value = EatInOrderFlow.class, names = {"ACCEPTED", "SERVED", "COMPLETED"})
    @DisplayName("대기 중이 아닌 주문 시 예외가 발생한다.")
    void accept_exception(EatInOrderFlow flow) {
        // given
        Menu menu = createMenuWithProductAndGroup();
        OrderTable orderTable = createUsingOrderTable();
        EatInOrder order = createEatInOrderRequestWithEmptyTable(menu, flow);
        order.occupyOrderTable(orderTable);
        when(eatInOrderRepository.findById(any())).thenReturn(Optional.of(order));

        // when // then
        assertThatThrownBy(() -> eatInOrderService.accept(order.getId()))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("대기 중인 주문만 접수할 수 있다")
    void accept_success() {
        // given
        Menu menu = createMenuWithProductAndGroup();
        OrderTable orderTable = createUsingOrderTable();
        EatInOrder order = createEatInOrderRequestWithEmptyTable(menu, EatInOrderFlow.WAITING);
        order.occupyOrderTable(orderTable);
        when(eatInOrderRepository.findById(any())).thenReturn(Optional.of(order));

        // when
        EatInOrder result = eatInOrderService.accept(order.getId());

        // then
        assertThat(result.getEatInOrderFlow()).isEqualTo(EatInOrderFlow.ACCEPTED);
    }

    @Test
    @DisplayName("매장 주문은 서빙이 완료 되면 주문을 완료할 수 있다")
    void complete_order() {
        // given
        Menu menu = createMenuWithProductAndGroup();
        OrderTable orderTable = createUsingOrderTable();
        EatInOrder order = createEatInOrderRequestWithEmptyTable(menu, EatInOrderFlow.SERVED);
        order.occupyOrderTable(orderTable);
        when(eatInOrderRepository.findById(any())).thenReturn(Optional.of(order));

        // when
        EatInOrder result = eatInOrderService.complete(order.getId());

        // then
        assertThat(result.getEatInOrderFlow()).isEqualTo(EatInOrderFlow.COMPLETED);
    }
}
