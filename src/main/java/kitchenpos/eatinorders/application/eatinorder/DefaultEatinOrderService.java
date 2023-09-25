package kitchenpos.eatinorders.application.eatinorder;

import java.util.UUID;
import kitchenpos.eatinorders.application.eatinorder.port.in.EatinOrderDTO;
import kitchenpos.eatinorders.application.eatinorder.port.in.EatinOrderInitCommand;
import kitchenpos.eatinorders.application.eatinorder.port.in.EatinOrderUseCase;
import kitchenpos.eatinorders.application.eatinorder.port.out.EatinOrderRepository;
import kitchenpos.eatinorders.application.eatinorder.port.out.ValidMenuPort;
import kitchenpos.eatinorders.application.exception.NotExistEatinOrderException;
import kitchenpos.eatinorders.application.ordertable.port.out.OrderTableNewRepository;
import kitchenpos.eatinorders.domain.eatinorder.EatinOrder;
import org.springframework.transaction.annotation.Transactional;

public class DefaultEatinOrderService implements EatinOrderUseCase {

    private final EatinOrderRepository eatinOrderRepository;
    private final OrderTableNewRepository orderTableRepository;
    private final ValidMenuPort menuFindPort;

    public DefaultEatinOrderService(final EatinOrderRepository eatinOrderRepository,
        final OrderTableNewRepository orderTableRepository, final ValidMenuPort menuFindPort) {

        this.eatinOrderRepository = eatinOrderRepository;
        this.orderTableRepository = orderTableRepository;
        this.menuFindPort = menuFindPort;
    }

    @Override
    public EatinOrderDTO init(final EatinOrderInitCommand command) {
        final EatinOrder eatinOrder = eatinOrderRepository.save(
            EatinOrder.create(command, orderTableRepository, menuFindPort));

        return new EatinOrderDTO(eatinOrder);
    }

    @Override
    public void accept(final UUID id) {
        final EatinOrder order = eatinOrderRepository.findById(id)
            .orElseThrow(() -> new NotExistEatinOrderException(id));

        order.acceptOrder();
    }

    @Override
    public void serve(final UUID id) {
        final EatinOrder order = eatinOrderRepository.findById(id)
            .orElseThrow(() -> new NotExistEatinOrderException(id));

        order.serveOrder();
    }

    @Transactional
    @Override
    public void complete(final UUID id) {
        final EatinOrder order = eatinOrderRepository.findById(id)
            .orElseThrow(() -> new NotExistEatinOrderException(id));

        order.completeOrder(orderTableRepository);
    }
}
