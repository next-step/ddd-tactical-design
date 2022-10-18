package kitchenpos.eatinordertables.application;

import static kitchenpos.eatinorders.EatInOrderFixtures.eatInOrder;
import static kitchenpos.eatinorders.domain.EatInOrderStatus.*;
import static kitchenpos.eatinordertables.EatInOrderTableFixtures.eatInOrderTable;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.eatinorders.application.EatInOrderService;
import kitchenpos.eatinorders.domain.EatInOrder;
import kitchenpos.eatinorders.domain.EatInOrderRepository;
import kitchenpos.eatinordertables.domain.EatInOrderTable;
import kitchenpos.eatinordertables.domain.EatInOrderTableRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EatInOrderTableEventHandlerTest {

    @Autowired
    private EatInOrderService eatInOrderService;

    @Autowired
    private EatInOrderRepository eatInOrderRepository;

    @Autowired
    private EatInOrderTableRepository eatInOrderTableRepository;

    @DisplayName("주문이 완료되었다는 이벤트가 발행되었을 때, 테이블의 모든 주문이 완료된 상태라면 테이블을 비운다.")
    @Test
    void clearTable() {
        // given
        EatInOrderTable table = eatInOrderTableRepository.save(eatInOrderTable(true, 5));
        EatInOrder order = eatInOrderRepository.save(eatInOrder(SERVED, table.getId()));

        // when
        eatInOrderService.complete(order.getId());

        // then
        EatInOrderTable found = findEatInOrderTableById(table.getId());
        assertThat(found.isOccupied()).isFalse();
    }

    private EatInOrderTable findEatInOrderTableById(UUID eatInOrderTableId) {
        return eatInOrderTableRepository.findById(eatInOrderTableId)
            .orElseThrow(() -> new NoSuchElementException("ID 에 해당하는 주문 테이블을 찾을 수 없습니다."));
    }
}
