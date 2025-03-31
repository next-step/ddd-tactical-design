package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.domain.exception.InvalidEatInOrderTableException;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderRepository;
import kitchenpos.eatinorders.tobe.domain.orderTable.EatInOrderTable;
import kitchenpos.eatinorders.tobe.domain.orderTable.EatInOrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.orderTable.OrderTableOrders;
import kitchenpos.eatinorders.tobe.domain.orderTable.vo.NumberOfGuests;
import kitchenpos.eatinorders.tobe.ui.dto.ChangeNumberOfGuestsRequest;
import kitchenpos.eatinorders.tobe.ui.dto.CreateEatInOrderTableRequest;
import kitchenpos.eatinorders.tobe.ui.dto.CreateEatInOrderTableResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@Transactional
@Service
public class EatInOrderTableService {

    private final EatInOrderRepository eatInOrderRepository;
    private final EatInOrderTableRepository eatInOrderTableRepository;
    private final OrderTableOrders orderTableOrders;

    public EatInOrderTableService(final EatInOrderRepository eatInOrderRepository,
                                  final EatInOrderTableRepository eatInOrderTableRepository,
                                  final OrderTableOrders orderTableOrders) {
        this.eatInOrderRepository = eatInOrderRepository;
        this.eatInOrderTableRepository = eatInOrderTableRepository;
        this.orderTableOrders = orderTableOrders;
    }

    public CreateEatInOrderTableResponse create(final CreateEatInOrderTableRequest request) {
        final EatInOrderTable eatInOrderTable = eatInOrderTableRepository.save(
                new EatInOrderTable(request.name()));
        return CreateEatInOrderTableResponse.from(eatInOrderTable);
    }

    public EatInOrderTable sit(final UUID orderTableId) {
        final EatInOrderTable eatInOrderTable = eatInOrderTableRepository.findById(orderTableId)
                .orElseThrow(() -> new InvalidEatInOrderTableException("주문 테이블이 존재해야 합니다."));
        eatInOrderTable.sit();
        return eatInOrderTable;
    }

    /**
     * 응용 서비스에서 유효성 검사를 도메인 서비스로 이동
     */
    public EatInOrderTable clear(final UUID orderTableId) {
        final EatInOrderTable eatInOrderTable = eatInOrderTableRepository.findById(orderTableId)
                .orElseThrow(() -> new InvalidEatInOrderTableException("주문 테이블이 존재해야 합니다."));

        eatInOrderTable.clear(orderTableOrders);
        return eatInOrderTable;
    }

    public EatInOrderTable changeNumberOfGuests(final UUID orderTableId, final ChangeNumberOfGuestsRequest request) {
        final NumberOfGuests numberOfGuests  = new NumberOfGuests(request.numberOfGuests());
        final EatInOrderTable eatInOrderTable = eatInOrderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
        eatInOrderTable.changeNumberOfGuests(numberOfGuests.getNumberOfGuests());
        return eatInOrderTable;
    }
}
