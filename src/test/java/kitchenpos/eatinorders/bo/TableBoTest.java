package kitchenpos.eatinorders.bo;

import kitchenpos.eatinorders.dao.OrderDao;
import kitchenpos.eatinorders.dao.OrderTableDao;
import kitchenpos.eatinorders.model.Order;
import kitchenpos.eatinorders.model.OrderStatus;
import kitchenpos.eatinorders.model.OrderTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static kitchenpos.eatinorders.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

class TableBoTest {
    private final OrderDao orderDao = new InMemoryOrderDao();
    private final OrderTableDao orderTableDao = new InMemoryOrderTableDao();

    private TableBo tableBo;

    @BeforeEach
    void setUp() {
        tableBo = new TableBo(orderDao, orderTableDao);
    }

    @DisplayName("주문 테이블을 등록할 수 있다.")
    @Test
    void create() {
        // given
        final OrderTable expected = table1();

        // when
        final OrderTable actual = tableBo.create(expected);

        // then
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getTableGroupId()).isNull(),
                () -> assertThat(actual.getNumberOfGuests()).isEqualTo(expected.getNumberOfGuests()),
                () -> assertThat(actual.isEmpty()).isEqualTo(expected.isEmpty())
        );
    }

    @DisplayName("주문 테이블의 목록을 조회할 수 있다.")
    @Test
    void list() {
        // given
        final OrderTable table1 = orderTableDao.save(table1());

        // when
        final List<OrderTable> actual = tableBo.list();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(Arrays.asList(table1));
    }

    @DisplayName("빈 테이블 설정 또는 해지할 수 있다.")
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void changeEmpty(final boolean empty) {
        // given
        final Long orderTableId = orderTableDao.save(table1()).getId();

        final OrderTable expected = new OrderTable();
        expected.setEmpty(empty);

        // when
        final OrderTable actual = tableBo.changeEmpty(orderTableId, expected);

        // then
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
        // given
        final Long orderTableId = orderTableDao.save(groupedTable1()).getId();

        final OrderTable expected = new OrderTable();
        expected.setEmpty(empty);

        // when
        // then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> tableBo.changeEmpty(orderTableId, expected))
        ;
    }

    @DisplayName("주문 상태가 조리 또는 식사인 주문 테이블은 빈 테이블 설정 또는 해지할 수 없다.")
    @ParameterizedTest
    @CsvSource(value = {"true,COOKING", "false,COOKING", "true,MEAL", "false,MEAL"})
    void changeEmpty(final boolean empty, final OrderStatus orderStatus) {
        // given
        final Long orderTableId = orderTableDao.save(table1()).getId();

        final Order orderForTable1 = orderForTable1();
        orderForTable1.setOrderStatus(orderStatus.name());
        orderDao.save(orderForTable1);

        final OrderTable expected = new OrderTable();
        expected.setEmpty(empty);

        // when
        // then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> tableBo.changeEmpty(orderTableId, expected))
        ;
    }

    @DisplayName("방문한 손님 수를 입력할 수 있다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    void changeNumberOfGuests(final int numberOfGuests) {
        // given
        final Long orderTableId = orderTableDao.save(table1()).getId();

        final OrderTable expected = new OrderTable();
        expected.setNumberOfGuests(numberOfGuests);

        // when
        final OrderTable actual = tableBo.changeNumberOfGuests(orderTableId, expected);

        // then
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isEqualTo(orderTableId),
                () -> assertThat(actual.getNumberOfGuests()).isEqualTo(expected.getNumberOfGuests())
        );
    }

    @DisplayName("방문한 손님 수가 올바르지 않으면 입력할 수 없다.")
    @Test
    void changeNumberOfGuests() {
        // given
        final Long orderTableId = orderTableDao.save(table1()).getId();

        final OrderTable expected = new OrderTable();
        expected.setNumberOfGuests(-1);

        // when
        // then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> tableBo.changeNumberOfGuests(orderTableId, expected))
        ;
    }

    @DisplayName("빈 테이블은 방문한 손님 수를 입력할 수 없다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    void changeNumberOfGuestsInEmptyTable(final int numberOfGuests) {
        // given
        final Long orderTableId = orderTableDao.save(emptyTable1()).getId();

        final OrderTable expected = new OrderTable();
        expected.setNumberOfGuests(numberOfGuests);

        // when
        // then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> tableBo.changeNumberOfGuests(orderTableId, expected))
        ;
    }
}
