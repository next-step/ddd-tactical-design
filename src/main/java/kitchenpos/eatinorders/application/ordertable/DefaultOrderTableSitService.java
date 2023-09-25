package kitchenpos.eatinorders.application.ordertable;

import static kitchenpos.support.ParameterValidateUtils.checkNotNull;

import java.util.UUID;
import kitchenpos.eatinorders.application.exception.NotExistOrderTableException;
import kitchenpos.eatinorders.application.ordertable.port.in.OrderTableSitUseCase;
import kitchenpos.eatinorders.application.ordertable.port.out.OrderTableNewRepository;
import kitchenpos.eatinorders.domain.ordertable.OrderTableNew;

public class DefaultOrderTableSitService implements OrderTableSitUseCase {

    private final OrderTableNewRepository repository;

    public DefaultOrderTableSitService(final OrderTableNewRepository repository) {
        this.repository = repository;
    }

    @Override
    public void sit(final UUID id, final int numberOfGuests) {
        checkNotNull(id, "id");

        final OrderTableNew orderTable = repository.findById(id)
            .orElseThrow(() -> new NotExistOrderTableException(id));

        orderTable.sit(numberOfGuests);
    }

    @Override
    public void changeGuests(final UUID id, final int numberOfGuests) {
        checkNotNull(id, "id");

        final OrderTableNew orderTable = repository.findById(id)
            .orElseThrow(() -> new NotExistOrderTableException(id));

        orderTable.changeNumberOfGuests(numberOfGuests);
    }
}