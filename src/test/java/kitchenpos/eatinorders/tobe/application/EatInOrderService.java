package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.application.dto.CreateEatInOrderCommand;
import kitchenpos.eatinorders.tobe.application.dto.CreateEatInOrderLineItemCommand;
import kitchenpos.eatinorders.tobe.domain.order.*;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderMenuRepository;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderMenus;
import kitchenpos.eatinorders.tobe.domain.order.event.EatInOrderCompletedEvent;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderId;
import kitchenpos.eatinorders.tobe.domain.ordertable.InMemoryOrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTable;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.ordertable.vo.OrderTableId;
import kitchenpos.shared.InMemoryApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class EatInOrderService {

    private final EatInOrderMenuRepository eatInOrderMenuRepository = new InMemoryEatInOrderMenuRepository();
    private final OrderTableRepository orderTableRepository = new InMemoryOrderTableRepository();
    private final EatInOrderRepository eatInOrderRepository = new InMemoryEatInOrderRepository();
    private final ApplicationEventPublisher eventPublisher = new InMemoryApplicationEventPublisher();

    public EatInOrder create(final CreateEatInOrderCommand command) {
        final EatInOrderMenus eatInOrderMenus = eatInOrderMenuRepository.findAllByIdIn(command.menuIds());
        final OrderTable orderTable = orderTableRepository.findById(new OrderTableId(command.orderTableId()))
                .orElseThrow(IllegalArgumentException::new);
        if (!orderTable.isOccupiedValue()) {
            throw new IllegalArgumentException();
        }
        return eatInOrderRepository.save(eatInOrder(command, eatInOrderMenus, orderTable.id()));
    }

    public EatInOrder accept(final UUID eatInOrderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findById(new EatInOrderId(eatInOrderId))
                .orElseThrow(NoSuchElementException::new);
        eatInOrder.accepted();
        return eatInOrderRepository.save(eatInOrder);
    }

    public EatInOrder serve(final UUID eatInOrderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findById(new EatInOrderId(eatInOrderId))
                .orElseThrow(NoSuchElementException::new);
        eatInOrder.served();
        return eatInOrderRepository.save(eatInOrder);
    }

    public EatInOrder complete(final UUID eatInOrderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findById(new EatInOrderId(eatInOrderId))
                .orElseThrow(NoSuchElementException::new);
        eatInOrder.completed();
        eventPublisher.publishEvent(new EatInOrderCompletedEvent(eatInOrder.idValue(), eatInOrder.orderTableId()));
        return eatInOrderRepository.save(eatInOrder);
    }

    public List<EatInOrder> findAll() {
        return eatInOrderRepository.findAll();
    }

    private EatInOrder eatInOrder(final CreateEatInOrderCommand command, final EatInOrderMenus eatInOrderMenus, final OrderTableId orderTableId) {
        final List<CreateEatInOrderLineItemCommand> createEatInOrderLineItemCommands = command.lineItems();
        final List<EatInOrderLineItem> eatInOrderLineItems = createEatInOrderLineItemCommands.stream()
                .map(this::eatInOrderLineItem)
                .toList();
        return new EatInOrder(eatInOrderLineItems, eatInOrderMenus, orderTableId);
    }

    private EatInOrderLineItem eatInOrderLineItem(final CreateEatInOrderLineItemCommand command) {
        return new EatInOrderLineItem(
                command.menuId(),
                command.name(),
                command.price(),
                command.quantity()
        );
    }
}
