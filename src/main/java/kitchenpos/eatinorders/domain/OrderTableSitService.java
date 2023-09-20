package kitchenpos.eatinorders.domain;

import org.springframework.stereotype.Component;

@Component
public class OrderTableSitService {

    private final OrderTableRepository orderTableRepository;

    public OrderTableSitService(final OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }

    public OrderTable sit(final OrderTable orderTable) {
        return orderTableRepository.save(new OrderTable(orderTable).sit());
    }
}
