package kitchenpos.eatinordertables.application;

import kitchenpos.common.infra.Profanities;
import kitchenpos.eatinordertables.domain.*;
import kitchenpos.eatinordertables.dto.ChangeNumberOfGuestsRequest;
import kitchenpos.eatinordertables.dto.CreateOrderTableRequest;
import kitchenpos.eatinordertables.dto.OrderTableResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("TobeOrderTableService")
public class OrderTableService {
    private final OrderTableRepository orderTableRepository;
    private final OrderTranslator orderTranslator;
    private final Profanities profanities;

    public OrderTableService(final OrderTableRepository orderTableRepository, final OrderTranslator orderTranslator, final Profanities profanities) {
        this.orderTableRepository = orderTableRepository;
        this.orderTranslator = orderTranslator;
        this.profanities = profanities;
    }

    @Transactional
    public OrderTableResponse create(final CreateOrderTableRequest request) {
        final OrderTable orderTable = new OrderTable(new OrderTableName(request.getName(), profanities));
        orderTableRepository.save(orderTable);
        return createResponse(orderTable);
    }

    @Transactional
    public OrderTableResponse sit(final UUID orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
        orderTable.sit();
        return createResponse(orderTable);
    }

    @Transactional
    public OrderTableResponse clear(final UUID orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
        orderTable.clear(orderTranslator.isOrderCompleted(orderTableId));
        return createResponse(orderTable);
    }

    @Transactional
    public OrderTableResponse changeNumberOfGuests(final UUID orderTableId, final ChangeNumberOfGuestsRequest request) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
        orderTable.changeNumberOfGuests(new NumberOfGuests(request.getNumberOfGuests()));
        return createResponse(orderTable);
    }

    @Transactional(readOnly = true)
    public List<OrderTableResponse> findAll() {
        return orderTableRepository.findAll()
                .stream().map(this::createResponse)
                .collect(Collectors.toList());
    }

    private OrderTableResponse createResponse(final OrderTable orderTable) {
        return new OrderTableResponse(
                orderTable.getId(),
                orderTable.getName(),
                orderTable.getNumberOfGuests(),
                orderTable.isEmpty());
    }
}
