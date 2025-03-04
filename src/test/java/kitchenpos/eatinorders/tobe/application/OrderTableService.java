package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.application.dto.CreateOrderTableCommand;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTable;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTableOrders;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.ordertable.vo.OrderTableId;

import java.util.UUID;

public class OrderTableService {

    private final OrderTableRepository orderTableRepository;
    private final OrderTableOrders orderTableOrders;


    public OrderTableService(final OrderTableRepository orderTableRepository, final OrderTableOrders orderTableOrders) {
        this.orderTableRepository = orderTableRepository;
        this.orderTableOrders = orderTableOrders;
    }

    public OrderTable create(final CreateOrderTableCommand command) {
        final OrderTable orderTable = new OrderTable(command.name(), command.numberOfGuests(), command.occupied());
        return orderTableRepository.save(orderTable);
    }

    public OrderTable sit(final UUID orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(new OrderTableId(orderTableId))
                .orElseThrow(IllegalArgumentException::new);
        orderTable.sit();
        return orderTableRepository.save(orderTable);
    }

    public OrderTable changeNumberOfGuests(final UUID orderTableId, final int numberOfGuests) {
        final OrderTable orderTable = orderTableRepository.findById(new OrderTableId(orderTableId))
                .orElseThrow(IllegalArgumentException::new);
        orderTable.changeNumberOfGuests(numberOfGuests);
        return orderTableRepository.save(orderTable);
    }

    public OrderTable clear(final UUID orderTableId) {
        final OrderTableId id = new OrderTableId(orderTableId);
        final OrderTable orderTable = orderTableRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        orderTable.clear(orderTableOrders);
        return orderTableRepository.save(orderTable);
    }
}
