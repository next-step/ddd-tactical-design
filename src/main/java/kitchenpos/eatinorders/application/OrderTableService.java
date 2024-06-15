package kitchenpos.eatinorders.application;

import kitchenpos.eatinorders.application.dto.OrderTableRequest;
import kitchenpos.eatinorders.domain.eatinorder.ordertable.OrderTable;
import kitchenpos.eatinorders.domain.eatinorder.ordertable.OrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class OrderTableService {
    private final OrderTableRepository orderTableRepository;

    public OrderTableService(final OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }

    @Transactional
    public OrderTable create(final OrderTableRequest request) {

        final OrderTable orderTableRequest = OrderTable.of(request.getName(), request.getNumberOfGuests());

        return orderTableRepository.save(orderTableRequest);
    }

    @Transactional
    public OrderTable sit(final UUID orderTableId) {
        final OrderTable orderTableRequest = orderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);
        orderTableRequest.occupy();

        return orderTableRequest;
    }

    @Transactional
    public OrderTable clear(final UUID orderTableId) {
        final OrderTable orderTableRequest = orderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);
        orderTableRequest.clear();

        return orderTableRequest;
    }

    @Transactional
    public OrderTable changeNumberOfGuests(final UUID orderTableId, final OrderTableRequest request) {

        final OrderTable orderTableRequest = orderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);

        orderTableRequest.changeCustomerHeadCounts(request.getNumberOfGuests());
        return orderTableRequest;
    }

    @Transactional(readOnly = true)
    public List<OrderTable> findAll() {
        return orderTableRepository.findAll();
    }
}
