package kitchenpos.eatinorders.application.eatinorder.port.in;

import java.util.UUID;
import kitchenpos.eatinorders.application.exception.InvalidAcceptStatusException;
import kitchenpos.eatinorders.application.exception.InvalidCompleteStatusException;
import kitchenpos.eatinorders.application.exception.InvalidServeStatusException;
import kitchenpos.eatinorders.application.exception.NotExistOrderTableException;

public interface EatinOrderUseCase {

    /**
     * @throws NotExistOrderTableException orderTableId에 해당하는 table이 없을 때
     * @throws IllegalStateException       orderLineItem에 있는 menu가 없는 menu일 때
     */
    EatinOrderDTO init(final EatinOrderInitCommand command);

    /**
     * @throws NotExistOrderTableException  orderTableId에 해당하는 table이 없을 때
     * @throws InvalidAcceptStatusException accept를 할 수 없는 상태일 때
     */
    void accept(final UUID id);

    /**
     * @throws NotExistOrderTableException orderTableId에 해당하는 table이 없을 때
     * @throws InvalidServeStatusException serve를 할 수 없는 상태일 때
     */
    void serve(final UUID id);

    /**
     * @throws NotExistOrderTableException    orderTableId에 해당하는 table이 없을 때
     * @throws InvalidCompleteStatusException complete를 할 수 없는 상태일 때
     */
    void complete(final UUID id);
}
