package kitchenpos.eatinorders.tobe.domain.eatinorder.service;

import kitchenpos.eatinorders.model.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.eatinorder.domain.OrderTable;
import kitchenpos.eatinorders.tobe.domain.eatinorder.repository.OrderTableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static kitchenpos.eatinorders.TobeFixtures.emptyTable;
import static kitchenpos.eatinorders.TobeFixtures.nonEmptyTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class OrderTableServiceTest {

    @Autowired
    private OrderTableRepository orderTableRepository;

    @MockBean
    private OrderService orderService;

    private OrderTableService orderTableService;

    @BeforeEach
    void setUp() {
        orderTableService = new OrderTableService(orderService, orderTableRepository);
    }

    @DisplayName("주문 테이블을 등록할 수 있다.")
    @Test
    void create() {
        final OrderTable actual = orderTableService.create(nonEmptyTable());

        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getTableGroupId()).isNull(),
                () -> assertThat(actual.getNumberOfGuests()).isEqualTo(nonEmptyTable().getNumberOfGuests()),
                () -> assertThat(actual.isEmpty()).isEqualTo(nonEmptyTable().isEmpty())
        );
    }

    @DisplayName("주문 테이블의 목록을 조회할 수 있다.")
    @Test
    void list() {
        final OrderTable table1 = orderTableService.create(nonEmptyTable());

        final List<OrderTable> actual = orderTableService.list();

        assertThat(actual).contains(table1);
    }

    @DisplayName("빈 테이블 설정 또는 해지할 수 있다.")
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void changeEmpty(final boolean empty) {
        final Long orderTableId = orderTableService.create(nonEmptyTable()).getId();
        final OrderTable expected = new OrderTable(orderTableId, empty);
        when(orderService.getOrderStatusByOrderTableId(orderTableId)).thenReturn(OrderStatus.COMPLETION);

        final OrderTable actual = orderTableService.changeEmpty(orderTableId, expected);

        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isEqualTo(orderTableId),
                () -> assertThat(actual.isEmpty()).isEqualTo(expected.isEmpty())
        );
    }

    @DisplayName("단체 지정된 주문 테이블은 빈 테이블 설정 또는 해지할 수 없다.")
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void changeEmptyOfGroupedTable(final boolean empty) {
        final Long orderTableId = orderTableService.create(nonEmptyTable()).getId();
        OrderTable expected = new OrderTable(orderTableId, empty);
        expected.group(1L);

        when(orderService.getOrderStatusByOrderTableId(orderTableId)).thenReturn(OrderStatus.COMPLETION);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> orderTableService.changeEmpty(orderTableId, expected))
        ;
    }

    @DisplayName("주문 상태가 조리 또는 식사인 주문 테이블은 빈 테이블 설정 또는 해지할 수 없다.")
    @ParameterizedTest
    @CsvSource(value = {"true,COOKING", "false,COOKING", "true,MEAL", "false,MEAL"})
    void changeEmpty(final boolean empty, final OrderStatus orderStatus) {
        final Long orderTableId = orderTableService.create(nonEmptyTable()).getId();
        final OrderTable expected = new OrderTable(orderTableId, empty);
        when(orderService.getOrderStatusByOrderTableId(orderTableId)).thenReturn(orderStatus);


        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> orderTableService.changeEmpty(orderTableId, expected))
        ;
    }

    @DisplayName("방문한 손님 수를 입력할 수 있다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    void changeNumberOfGuests(final int numberOfGuests) {
        final Long orderTableId = orderTableService.create(nonEmptyTable()).getId();
        final OrderTable expected = OrderTable.nonEmptyTable(numberOfGuests);

        final OrderTable actual = orderTableService.changeNumberOfGuests(orderTableId, expected);

        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isEqualTo(orderTableId),
                () -> assertThat(actual.getNumberOfGuests()).isEqualTo(expected.getNumberOfGuests())
        );
    }

    @DisplayName("방문한 손님 수가 올바르지 않으면 입력할 수 없다.")
    @Test
    void changeNumberOfGuests() {
        final Long orderTableId = orderTableService.create(nonEmptyTable()).getId();

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> orderTableService.changeNumberOfGuests(orderTableId, OrderTable.nonEmptyTable(-1)))
        ;
    }

    @DisplayName("빈 테이블은 방문한 손님 수를 입력할 수 없다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    void changeNumberOfGuestsInEmptyTable(final int numberOfGuests) {
        final Long orderTableId = orderTableService.create(emptyTable()).getId();
        final OrderTable expected = OrderTable.nonEmptyTable(numberOfGuests);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> orderTableService.changeNumberOfGuests(orderTableId, expected))
        ;
    }
}
