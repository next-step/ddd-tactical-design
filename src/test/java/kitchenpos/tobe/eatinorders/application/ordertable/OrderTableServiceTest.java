package kitchenpos.tobe.eatinorders.application.ordertable;

import static kitchenpos.tobe.eatinorders.application.Fixtures.notCompletedOrderTable;
import static kitchenpos.tobe.eatinorders.application.Fixtures.orderTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.application.OrderTableService;
import kitchenpos.eatinorders.tobe.domain.model.NumberOfGuests;
import kitchenpos.eatinorders.tobe.domain.model.OrderTable;
import kitchenpos.eatinorders.tobe.domain.model.OrderTableName;
import kitchenpos.eatinorders.tobe.domain.repository.OrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.service.OrderTableDomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class OrderTableServiceTest {

    private OrderTableRepository orderTableRepository;
    private OrderTableService orderTableService;
    private OrderTableDomainService orderTableDomainService;

    @BeforeEach
    void setUp() {
        orderTableRepository = new InMemoryOrderTableRepository();
        orderTableDomainService = new OrderTableDomainService(new FakeEatInOrderRepository());
        orderTableService = new OrderTableService(orderTableRepository, orderTableDomainService);
    }

    @DisplayName("주문 테이블을 등록할 수 있다.")
    @Test
    void create() {
        final OrderTable expected = createOrderTableRequest("1번");
        final OrderTable actual = orderTableService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getNumberOfGuests()).isZero(),
                () -> assertThat(actual.isEmpty()).isTrue()
        );
    }

    @DisplayName("주문 테이블의 이름이 올바르지 않으면 등록할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create(final String name) {
        final OrderTable expected = createOrderTableRequest(name);
        assertThatThrownBy(() -> orderTableService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 테이블을 해지할 수 있다.")
    @Test
    void sit() {
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 0)).getId();
        final OrderTable actual = orderTableService.sit(orderTableId);
        assertThat(actual.isEmpty()).isFalse();
    }

    @DisplayName("빈 테이블로 설정할 수 있다.")
    @Test
    void clear() {
        final UUID orderTableId = orderTableRepository.save(orderTable(false, 4)).getId();
        final OrderTable actual = orderTableService.clear(orderTableId);
        assertAll(
                () -> assertThat(actual.getNumberOfGuests()).isZero(),
                () -> assertThat(actual.isEmpty()).isTrue()
        );
    }

    @DisplayName("완료되지 않은 주문이 있는 주문 테이블은 빈 테이블로 설정할 수 없다.")
    @Test
    void clearWithUncompletedOrders() {
        final OrderTable orderTable = orderTableRepository.save(notCompletedOrderTable());
        final UUID orderTableId = orderTable.getId();
        assertThatThrownBy(() -> orderTableService.clear(orderTableId))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("방문한 손님 수를 변경할 수 있다.")
    @Test
    void changeNumberOfGuests() {
        final UUID orderTableId = orderTableRepository.save(orderTable(false, 0)).getId();
        final OrderTable expected = changeNumberOfGuestsRequest(4);
        final OrderTable actual = orderTableService.changeNumberOfGuests(orderTableId, expected);
        assertThat(actual.getNumberOfGuests()).isEqualTo(4);
    }

    @DisplayName("방문한 손님 수가 올바르지 않으면 변경할 수 없다.")
    @ValueSource(ints = -1)
    @ParameterizedTest
    void changeNumberOfGuests(final int numberOfGuests) {
        final UUID orderTableId = orderTableRepository.save(orderTable(false, 0)).getId();
        final OrderTable expected = changeNumberOfGuestsRequest(numberOfGuests);
        assertThatThrownBy(() -> orderTableService.changeNumberOfGuests(orderTableId, expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 테이블은 방문한 손님 수를 변경할 수 없다.")
    @Test
    void changeNumberOfGuestsInEmptyTable() {
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 0)).getId();
        final OrderTable expected = changeNumberOfGuestsRequest(4);
        assertThatThrownBy(() -> orderTableService.changeNumberOfGuests(orderTableId, expected))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문 테이블의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        orderTableRepository.save(orderTable());
        final List<OrderTable> actual = orderTableService.findAll();
        assertThat(actual).hasSize(1);
    }

    private OrderTable createOrderTableRequest(final String name) {
        OrderTable orderTable = new OrderTable("테이블이름");

        try {
            final Field nameField = orderTable.getClass().getDeclaredField("name");
            nameField.setAccessible(true);
            OrderTableName orderTableName = new OrderTableName("테이블이름");
            final Field orderTableNameField = orderTableName.getClass().getDeclaredField("name");
            orderTableNameField.setAccessible(true);
            orderTableNameField.set(orderTableName, name);
            nameField.set(orderTable, orderTableName);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return orderTable;
    }

    private OrderTable changeNumberOfGuestsRequest(final int numberOfGuests) {
        final OrderTable orderTable = new OrderTable();

        final Optional<Field> numberOfGuestsField = Arrays.stream(orderTable.getClass().getDeclaredFields())
                .filter(field -> field.getName().equals("numberOfGuests"))
                .findFirst();

        try {
            numberOfGuestsField.get().setAccessible(true);
            NumberOfGuests numberOfGuest = new NumberOfGuests(1);
            final Field number = numberOfGuest.getClass().getDeclaredField("number");
            number.setAccessible(true);
            number.set(numberOfGuest, numberOfGuests);
            numberOfGuestsField.get().set(orderTable, numberOfGuest);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        return orderTable;
    }

}
