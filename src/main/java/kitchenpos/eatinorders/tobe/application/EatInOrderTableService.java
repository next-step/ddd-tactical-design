package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.domain.exception.InvalidEatInOrderTableException;
import kitchenpos.eatinorders.tobe.domain.orderTable.EatInOrderTable;
import kitchenpos.eatinorders.tobe.domain.orderTable.EatInOrderTableRepository;
import kitchenpos.eatinorders.tobe.ui.dto.CreateEatInOrderTableRequest;
import kitchenpos.eatinorders.tobe.ui.dto.CreateEatInOrderTableResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
@Service
public class EatInOrderTableService {

    private final EatInOrderTableRepository eatInOrderTableRepository;

    public EatInOrderTableService(final EatInOrderTableRepository eatInOrderTableRepository) {
        this.eatInOrderTableRepository = eatInOrderTableRepository;
    }

    public CreateEatInOrderTableResponse create(final CreateEatInOrderTableRequest request) {
        final String name = request.name();
        final EatInOrderTable eatInOrderTable = eatInOrderTableRepository.save(
                new EatInOrderTable(name, 0, false));
        return CreateEatInOrderTableResponse.from(eatInOrderTable);
    }

    public EatInOrderTable sit(final UUID orderTableId) {
        final EatInOrderTable eatInOrderTable = eatInOrderTableRepository.findById(orderTableId)
                .orElseThrow(() -> new InvalidEatInOrderTableException("주문 테이블이 존재해야 합니다."));
        eatInOrderTable.sit();
        return eatInOrderTable;
    }
}
