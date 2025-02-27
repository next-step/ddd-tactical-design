package kitchenpos.eatinorders.application.tobe;

import kitchenpos.eatinorders.tobe.domain.OrderTable;
import kitchenpos.eatinorders.tobe.domain.OrderTableRepository;
import kitchenpos.eatinorders.ui.dto.OrderTableCreateResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderTableService {

    private final OrderTableRepository orderTableRepository;

    public OrderTableService(OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }

    @Transactional
    public OrderTableCreateResponse create(final String tableName) {
        OrderTable orderTable = orderTableRepository.save(OrderTable.create(tableName));
        return OrderTableCreateResponse.from(orderTable);
    }
}

