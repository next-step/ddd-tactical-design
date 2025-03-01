package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.application.dto.CreateEatInOrderCommand;
import kitchenpos.eatinorders.tobe.application.dto.CreateEatInOrderLineItemCommand;
import kitchenpos.eatinorders.tobe.application.dto.CreateEatInOrderLineItemMenuCommand;
import kitchenpos.eatinorders.tobe.domain.InMemoryEatInOrderMenuRepository;
import kitchenpos.eatinorders.tobe.domain.InMemoryEatInOrderRepository;
import kitchenpos.eatinorders.tobe.domain.order.*;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderId;
import kitchenpos.eatinorders.tobe.domain.order.vo.Quantity;
import kitchenpos.eatinorders.tobe.domain.ordertable.InMemoryOrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTable;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.ordertable.vo.OrderTableId;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class EatInOrderService {

    private final EatInOrderMenuRepository eatInOrderMenuRepository = new InMemoryEatInOrderMenuRepository();
    private final OrderTableRepository orderTableRepository = new InMemoryOrderTableRepository();
    private final EatInOrderRepository eatInOrderRepository = new InMemoryEatInOrderRepository();

    public EatInOrder create(final CreateEatInOrderCommand command) {
        final EatInOrderMenus eatInOrderMenus = eatInOrderMenuRepository.findAllByIdIn(command.menuIds());
        final OrderTable orderTable = orderTableRepository.findById(new OrderTableId(command.orderTableId()))
                .orElseThrow(IllegalArgumentException::new);
        final EatInOrder eatInOrder = eatInOrder(command, eatInOrderMenus, orderTable);
        return eatInOrderRepository.save(eatInOrder);
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
        return eatInOrderRepository.save(eatInOrder);
    }

    public List<EatInOrder> findAll() {
        return eatInOrderRepository.findAll();
    }

    private EatInOrder eatInOrder(final CreateEatInOrderCommand command, final EatInOrderMenus eatInOrderMenus, final OrderTable orderTable) {
        final List<CreateEatInOrderLineItemCommand> createEatInOrderLineItemCommands = command.lineItems();
        final EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems(createEatInOrderLineItemCommands.stream()
                .map(eatInOrderLineItemCommand -> new EatInOrderLineItem(
                        null,
                        eatInOrderLineItemMenu(eatInOrderLineItemCommand.eatInOrderLineItemMenuCommand()),
                        new Quantity(eatInOrderLineItemCommand.quantity()))
                ).toList());
        return new EatInOrder(eatInOrderLineItems, eatInOrderMenus, orderTable);
    }

    private EatInOrderLineItemMenu eatInOrderLineItemMenu(final CreateEatInOrderLineItemMenuCommand command) {
        final UUID menuId = command.menuId();
        final String name = command.name();
        final int price = command.price();
        return new EatInOrderLineItemMenu(menuId, name, price);
    }
}
