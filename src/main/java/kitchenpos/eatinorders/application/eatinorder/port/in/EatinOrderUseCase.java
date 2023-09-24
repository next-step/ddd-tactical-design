package kitchenpos.eatinorders.application.eatinorder.port.in;

import java.util.Optional;

public interface EatinOrderUseCase {

    Optional<EatinOrderDTO> init();

    Optional<EatinOrderDTO> accept();

    Optional<EatinOrderDTO> serve();

    Optional<EatinOrderDTO> complete();
}
