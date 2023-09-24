package kitchenpos.eatinorders.application.ordertable;

import static kitchenpos.support.ParameterValidateUtils.checkNotNull;

import java.util.UUID;
import kitchenpos.eatinorders.application.ordertable.port.in.OrderTableCreationUseCase;
import kitchenpos.eatinorders.application.ordertable.port.out.OrderTableNewRepository;
import kitchenpos.eatinorders.domain.ordertable.OrderTableNew;
import kitchenpos.support.vo.Name;

public class DefaultOrderTableCreationService implements OrderTableCreationUseCase {

    private final OrderTableNewRepository repository;

    public DefaultOrderTableCreationService(final OrderTableNewRepository repository) {
        this.repository = repository;
    }

    @Override
    public UUID create(final Name name) {
        checkNotNull(name, "name");

        final OrderTableNew orderTable = repository.save(OrderTableNew.create(name));

        return orderTable.getId();
    }
}
