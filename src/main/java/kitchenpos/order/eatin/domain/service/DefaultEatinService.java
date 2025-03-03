package kitchenpos.order.eatin.domain.service;

import java.util.List;
import java.util.UUID;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.global.exception.NotFoundException;
import kitchenpos.order.common.domain.entity.OrderStatus;
import kitchenpos.order.common.domain.repository.OrderTableRepository;
import kitchenpos.order.eatin.domain.entity.OrderTable;
import kitchenpos.order.eatin.domain.model.OrderTableId;
import kitchenpos.order.eatin.domain.model.OrderTableName;
import kitchenpos.order.eatin.domain.model.OrderTableVo.Create;
import kitchenpos.order.eatin.domain.model.OrderTableVo.OrderTableInfo;
import kitchenpos.order.eatin.domain.model.OrderTableVo.Update;
import kitchenpos.order.eatin.domain.repository.EatinOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class DefaultEatinService implements EatinService {

    private final EatinOrderRepository orderRepository;
    private final OrderTableRepository orderTableRepository;

    public DefaultEatinService(EatinOrderRepository orderRepository, OrderTableRepository orderTableRepository) {
        this.orderRepository = orderRepository;
        this.orderTableRepository = orderTableRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderTableInfo> findAll() {
        return orderTableRepository.findAll()
            .stream()
            .map(OrderTableInfo::fromEntity)
            .toList();
    }

    @Override
    public OrderTableInfo create(Create request) {
        final OrderTableName name = request.name();
        final var orderTableId = OrderTableId.of(UUID.randomUUID());
        return OrderTableInfo.fromEntity(orderTableRepository.save(new OrderTable(orderTableId, name, request.guests(), request.occupied())));
    }

    @Override
    public OrderTableInfo sit(OrderTableId orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ORDER_TABLE.toString()));

        orderTable.updateOccupied(true);
        return OrderTableInfo.fromEntity(orderTable);
    }

    @Override
    public OrderTableInfo clear(OrderTableId orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ORDER_TABLE.toString()));

        if (orderRepository.existsByOrderTableIdAndStatusNot(orderTableId, OrderStatus.COMPLETED)) {
            throw new IllegalStateException();
        }
        orderTable.clear();
        return OrderTableInfo.fromEntity(orderTable);
    }

    @Override
    public OrderTableInfo changeNumberOfGuests(Update request) {
        final OrderTable orderTable = orderTableRepository.findById(request.orderTableId())
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ORDER_TABLE.toString()));

        orderTable.validateOccupied();

        orderTable.updateNumberOfGuests(request.guests());
        return OrderTableInfo.fromEntity(orderTable);
    }

    @Override
    public void complete(OrderTableId orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ORDER_TABLE.toString()));

        if (!orderRepository.existsByOrderTableIdAndStatusNot(orderTableId, OrderStatus.COMPLETED)) {
            orderTable.clear();
        }
    }
}
