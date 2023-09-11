package kitchenpos.eatinorders.tobe;

import kitchenpos.eatinorders.application.InMemoryOrderRepository;
import kitchenpos.eatinorders.application.InMemoryOrderTableRepository;
import kitchenpos.eatinorders.tobe.application.ToBeOrderTableService;
import kitchenpos.eatinorders.tobe.domain.*;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static kitchenpos.Fixtures.order;
import static kitchenpos.Fixtures.orderTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class NumberOfGuestsVOTest {
    private ToBeOrderTableRepository orderTableRepository;
    private ToBeOrderRepository orderRepository;
    private ToBeOrderTableService orderTableService;

    @BeforeEach
    void setUp() {
        orderTableRepository = new InMemoryOrderTableRepository();
        orderRepository = new InMemoryOrderRepository();
        orderTableService = new ToBeOrderTableService(orderTableRepository, orderRepository);

    }


    @DisplayName("방문한 손님 수가 올바르지 않으면 변경할 수 없다.")
    @ValueSource(ints = -1)
    @ParameterizedTest
    void changeNumberOfGuests(final int numberOfGuests) {
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 0)).getId();
        assertThatThrownBy(() -> changeNumberOfGuestsRequest(numberOfGuests))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 테이블은 방문한 손님 수를 변경할 수 없다.")
    @Test
    void changeNumberOfGuestsInEmptyTable() {
        final UUID orderTableId = orderTableRepository.save(orderTable(false, 0)).getId();
        final ToBeOrderTable expected = changeNumberOfGuestsRequest(4);
        assertThatThrownBy(() -> orderTableService.changeNumberOfGuests(orderTableId,expected))
                .isInstanceOf(IllegalStateException.class);
    }



    private ToBeOrderTable changeNumberOfGuestsRequest(final int numberOfGuests) {
        final ToBeOrderTable orderTable = new ToBeOrderTable();
        ReflectionTestUtils.setField(orderTable,"numberOfGuests", new NumberOfGuests(numberOfGuests));

        return orderTable;
    }
}
