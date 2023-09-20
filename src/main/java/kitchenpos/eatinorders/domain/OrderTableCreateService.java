package kitchenpos.eatinorders.domain;

import org.springframework.stereotype.Component;

@Component
public class OrderTableCreateService {

    private final OrderTableRepository orderTableRepository;

    public OrderTableCreateService(final OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }

    public OrderTable create(final OrderTable orderTable) {
        return orderTableRepository.save(new OrderTable(orderTable));
    }

}
