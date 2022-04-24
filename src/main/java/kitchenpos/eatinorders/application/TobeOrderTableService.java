package kitchenpos.eatinorders.application;

import kitchenpos.eatinorders.domain.tobe.domain.EatInOrder;
import kitchenpos.eatinorders.domain.tobe.domain.EatInOrderRepository;
import kitchenpos.eatinorders.domain.tobe.domain.TobeOrderTable;
import kitchenpos.eatinorders.domain.tobe.domain.TobeOrderTableRepository;
import kitchenpos.eatinorders.domain.tobe.domain.vo.Guests;
import kitchenpos.eatinorders.domain.tobe.domain.vo.OrderTableName;
import kitchenpos.eatinorders.dto.*;
import kitchenpos.support.infra.profanity.Profanity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TobeOrderTableService {
    private final TobeOrderTableRepository orderTableRepository;
    private final EatInOrderRepository orderRepository;
    private final Profanity profanity;

    public TobeOrderTableService(final TobeOrderTableRepository orderTableRepository,
                                 final EatInOrderRepository orderRepository,
                                 final Profanity profanity)
    {
        this.orderTableRepository = orderTableRepository;
        this.orderRepository = orderRepository;
        this.profanity = profanity;
    }

    @Transactional
    public OrderTableResponse create(final OrderTableRegisterRequest request) {
        OrderTableName name = new OrderTableName(request.getName(), profanity);
        return new OrderTableResponse(orderTableRepository.save(TobeOrderTable.Of(name)));
    }

    @Transactional
    public OrderTableResponse sit(final OrderTableSitRequest request) {
        TobeOrderTable table = orderTableRepository.findById(request.getId())
                .orElseThrow(NoSuchElementException::new);
        return new OrderTableResponse(table.sit());
    }

    @Transactional
    public OrderTableResponse clear(final OrderTableClearRequest request) {
        TobeOrderTable table = orderTableRepository.findById(request.getId())
                .orElseThrow(NoSuchElementException::new);
        List<EatInOrder> orders = orderRepository.findAllByOrderTableId(request.getId());
        if(orders.stream().anyMatch(o -> !o.isCompleted())) {
            throw new IllegalStateException();
        }
        return new OrderTableResponse(table.clear());
    }

    @Transactional
    public OrderTableResponse changeNumberOfGuests(final ChangeNumberOfGuestsRequest request) {
        TobeOrderTable table = orderTableRepository.findById(request.getId())
                .orElseThrow(NoSuchElementException::new);
        return new OrderTableResponse(table.changeNumberOfGuests(new Guests(request.getNumberOfGuests())));
    }

    @Transactional(readOnly = true)
    public List<OrderTableResponse> findAll() {
        return orderTableRepository.findAll()
                .stream()
                .map(OrderTableResponse::new)
                .collect(Collectors.toList());
    }
}
