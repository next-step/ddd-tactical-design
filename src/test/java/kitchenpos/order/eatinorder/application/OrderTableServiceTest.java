package kitchenpos.order.eatinorder.application;

import static kitchenpos.TestFixtureFactory.createEatInOrderRequestWithEmptyTable;
import static kitchenpos.TestFixtureFactory.createEmptyOrderTable;
import static kitchenpos.TestFixtureFactory.createMenu;
import static kitchenpos.TestFixtureFactory.createMenuGroup;
import static kitchenpos.TestFixtureFactory.createProduct;
import static kitchenpos.TestFixtureFactory.createUsingOrderTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import kitchenpos.order.eatinorder.domain.model.EatInOrder;
import kitchenpos.order.eatinorder.domain.model.EatInOrderFlow;
import kitchenpos.order.eatinorder.domain.model.OrderTable;
import kitchenpos.order.eatinorder.domain.repository.EatInOrderRepository;
import kitchenpos.order.eatinorder.domain.repository.OrderTableRepository;
import kitchenpos.order.eatinorder.domain.service.OrderTableOccupationManager;
import kitchenpos.order.eatinorder.infra.persistence.FakeEatInOrderRepository;
import kitchenpos.order.eatinorder.application.dto.CreateOrderTableServiceRq;
import kitchenpos.order.eatinorder.application.dto.OrderTableServiceRs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class OrderTableServiceTest {

    private OrderTableService orderTableService;
    private OrderTableRepository orderTableRepository;
    private EatInOrderRepository eatInOrderRepository;
    private OrderTableOccupationManager orderTableOccupationManager;

    @BeforeEach
    void setUp() {
        orderTableRepository = mock(OrderTableRepository.class);
        eatInOrderRepository = new FakeEatInOrderRepository(new HashMap<>());
        orderTableOccupationManager = new OrderTableOccupationManager(eatInOrderRepository);
        orderTableService = new OrderTableService(orderTableRepository, orderTableOccupationManager);
    }

    @Test
    @DisplayName("매장 테이블을 만들 수 있다")
    void create() {
        // given
        CreateOrderTableServiceRq request = new CreateOrderTableServiceRq("1번 테이블");
        when(orderTableRepository.save(any(OrderTable.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        OrderTableServiceRs result = orderTableService.create(request);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("1번 테이블");
        assertThat(result.getNumberOfGuests()).isZero();
        assertThat(result.isOccupied()).isFalse();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("매장 테이블 이름이 없으면 예외가 발생한다.")
    void orderTable_name_exception(String name) {
        // given
        CreateOrderTableServiceRq request = new CreateOrderTableServiceRq(name);

        // when // then
        assertThatThrownBy(() -> {
            orderTableService.create(request);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("전체 매장 테이블을 볼 수 있다")
    void find_all() {
        // given
        List<OrderTable> orderTables = List.of(
                createEmptyOrderTable(),
                createUsingOrderTable()
        );
        when(orderTableRepository.findAll()).thenReturn(orderTables);

        // when
        List<OrderTableServiceRs> result = orderTableService.findAll();

        // then
        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("빈 테이블을 이용할 수 있다")
    void sit() {
        // given
        OrderTable orderTable = createEmptyOrderTable();
        when(orderTableRepository.findById(any())).thenReturn(Optional.of(orderTable));

        // when
        OrderTableServiceRs result = orderTableService.sit(orderTable.getId());

        // then
        assertThat(result.isOccupied()).isTrue();
    }

    @Test
    @DisplayName("모든 주문이 완료되면 테이블을 정리할 수 있다")
    void clear() {
        // given
        OrderTable orderTable = createUsingOrderTable();
        EatInOrder eatInOrder = createEatInOrderRequestWithEmptyTable(
                createMenu(createMenuGroup(), createProduct(BigDecimal.valueOf(1000))), EatInOrderFlow.COMPLETED);
        eatInOrder.occupyOrderTable(orderTable);
        eatInOrderRepository.save(eatInOrder);

        when(orderTableRepository.findById(any())).thenReturn(Optional.of(orderTable));

        // when
        OrderTableServiceRs result = orderTableService.clear(orderTable.getId());

        // then
        assertThat(result.isOccupied()).isFalse();
        assertThat(result.getNumberOfGuests()).isZero();
    }

    @Test
    @DisplayName("주문이 완료되지 않은 테이블을 정리할 시 예외가 발생한다.")
    void orderTable_occupied_exception() {
        // given
        OrderTable orderTable = createUsingOrderTable();
        EatInOrder eatInOrder = createEatInOrderRequestWithEmptyTable(
                createMenu(createMenuGroup(), createProduct(BigDecimal.valueOf(1000))), EatInOrderFlow.SERVED);
        eatInOrder.occupyOrderTable(orderTable);
        eatInOrderRepository.save(eatInOrder);

        when(orderTableRepository.findById(any())).thenReturn(Optional.of(orderTable));

        // when // then
        assertThatThrownBy(() -> orderTableService.clear(orderTable.getId()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("주문이 완료되지 않은 매장 테이블은 정리할 수 없습니다.");
    }

    @Test
    @DisplayName("테이블에 손님이 있는 경우에만 앉아 있는 손님의 수를 변경할 수 있다")
    void change_numberOfGuests() {
        // given
        OrderTable orderTable = createUsingOrderTable();
        orderTable.changeNumberOfGuests(4);

        when(orderTableRepository.findById(any())).thenReturn(Optional.of(orderTable));

        // when
        OrderTableServiceRs result = orderTableService.changeNumberOfGuests(orderTable.getId(),
                orderTable);

        // then
        assertThat(result.getNumberOfGuests()).isEqualTo(4);
    }

    @Test
    @DisplayName("빈 테이블의 손님 수 변경 시 예외가 발생한다.")
    void change_numberOfGuests_not_occupied_exception() {
        // given
        OrderTable orderTable = createEmptyOrderTable();
        when(orderTableRepository.findById(any())).thenReturn(Optional.of(orderTable));

        // when // then
        assertThatThrownBy(() -> {
            orderTable.changeNumberOfGuests(4);
            orderTableService.changeNumberOfGuests(orderTable.getId(), orderTable);
        }).isInstanceOf(IllegalStateException.class)
                .hasMessage("주문 테이블이 비어있습니다!");
    }

    @Test
    @DisplayName("손님 수를 음수로 변경하면 예외가 발생한다..")
    void change_numberOfGuests_negative_number_exception() {
        // given
        OrderTable orderTable = createUsingOrderTable();
        when(orderTableRepository.findById(any())).thenReturn(Optional.of(orderTable));

        // when // then
        assertThatThrownBy(() -> {
            orderTable.changeNumberOfGuests(-1);
            orderTableService.changeNumberOfGuests(orderTable.getId(), orderTable);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("손님 수가 음수일 수 없습니다!");
    }

    private OrderTable createOrderTableRequest() {
        return new OrderTable("1번 테이블", 0, false);
    }
}
