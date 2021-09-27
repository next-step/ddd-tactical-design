package kitchenpos.eatinorders.tobe.domain.infra;

import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.model.OrderTable;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTableManager;
import kitchenpos.eatinorders.tobe.domain.repository.OrderTableRepository;
import org.springframework.stereotype.Component;

@Component
public class ModuleOrderTableManger implements OrderTableManager {

    private final OrderTableRepository orderTableRepository;

    public ModuleOrderTableManger(final OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }

    @Override
    public OrderTable getOrderTable(final UUID orderTableId) {
        return orderTableRepository.findById(orderTableId)
            .orElseThrow(() -> new NoSuchElementException("orderTable을 찾을 수 없습니다."));
    }
}
