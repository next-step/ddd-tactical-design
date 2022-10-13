package kitchenpos.eatinorders.tobe.domain.ordertable;

import kitchenpos.eatinorders.tobe.domain.order.EmptyTablePolicy;
import kitchenpos.eatinorders.tobe.domain.vo.NumberOfGuests;
import kitchenpos.global.vo.DisplayedName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTableEmptyTablePolicyTest {

    private UUID orderTableId;
    private OrderTable orderTable;

    private OrderTableRepository orderTableRepository;

    @BeforeEach
    void setUp() {
        orderTableRepository = new InMemoryOrderTableRepository();

        orderTableId = UUID.randomUUID();
        orderTable = new OrderTable(orderTableId, new DisplayedName("1번"), new NumberOfGuests(2), true);

        orderTableRepository.save(orderTable);
    }

    @DisplayName("테이블 사용여부 확인")
    @Test
    void emptyOrderTable() {
        EmptyTablePolicy emptyTablePolicy = new OrderTableEmptyTablePolicy(orderTableRepository);

        assertThat(emptyTablePolicy.isEmptyTable(orderTableId)).isFalse();
    }
}
