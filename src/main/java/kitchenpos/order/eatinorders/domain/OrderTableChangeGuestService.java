package kitchenpos.order.eatinorders.domain;

import kitchenpos.order.domain.OrderTable;
import org.springframework.stereotype.Component;


@Component
public class OrderTableChangeGuestService {

    private final OrderTableRepository orderTableRepository;

    public OrderTableChangeGuestService(final OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;

    }

    public OrderTable changeNumberOfGuests(final OrderTable orderTable,
                                           final int numberOfGuests) {
        if (numberOfGuests < 0) {
            throw new IllegalArgumentException();
        }
        if (!orderTable.isOccupied()) {
            throw new IllegalStateException();
        }
        return orderTableRepository.save(orderTable.setNumberOfGuests(numberOfGuests));
    }

}
