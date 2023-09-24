package kitchenpos.eatinorders.application.eatinorder;

import java.util.Optional;
import kitchenpos.eatinorders.application.eatinorder.port.in.EatinOrderDTO;
import kitchenpos.eatinorders.application.eatinorder.port.in.EatinOrderUseCase;

public class DefaultEatinOrderService implements EatinOrderUseCase {


    @Override
    public Optional<EatinOrderDTO> init() {
        return Optional.empty();
    }

    @Override
    public Optional<EatinOrderDTO> accept() {
        return Optional.empty();
    }

    @Override
    public Optional<EatinOrderDTO> serve() {
        return Optional.empty();
    }

    @Override
    public Optional<EatinOrderDTO> complete() {
        return Optional.empty();
    }
}
