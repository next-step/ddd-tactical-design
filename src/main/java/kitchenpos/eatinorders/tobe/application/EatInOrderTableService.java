package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.domain.orderTable.EatInOrderTable;
import kitchenpos.eatinorders.tobe.domain.orderTable.EatInOrderTableRepository;
import kitchenpos.eatinorders.tobe.ui.dto.CreateEatInOrderTableRequest;
import kitchenpos.eatinorders.tobe.ui.dto.CreateEatInOrderTableResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
