package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.application.dto.CreateEatInOrderCommand;
import kitchenpos.eatinorders.tobe.application.dto.CreateEatInOrderLineItemCommand;
import kitchenpos.eatinorders.tobe.domain.*;
import kitchenpos.eatinorders.tobe.domain.vo.Quantity;

import java.util.List;

public class EatInOrderService {

    private final EatInOrderMenuRepository eatInOrderMenuRepository = new InMemoryEatInOrderMenuRepository();
    private final EatInOrderTableRepository orderTableRepository = new InMemoryEatInOrderTableRepository();
    private final EatInOrderRepository eatInOrderRepository = new InMemoryEatInOrderRepository();

    public EatInOrder create(final CreateEatInOrderCommand command) {
        final EatInOrderMenus eatInOrderMenus = eatInOrderMenuRepository.findAllByIdIn(command.menuIds());
        final EatInOrderTable orderTable = orderTableRepository.findById(command.orderTableId())
                .orElseThrow(IllegalArgumentException::new);
        final EatInOrder eatInOrder = eatInOrder(command, eatInOrderMenus, orderTable);
        return eatInOrderRepository.save(eatInOrder);
    }

    private EatInOrder eatInOrder(final CreateEatInOrderCommand command, final EatInOrderMenus eatInOrderMenus, final EatInOrderTable orderTable) {
        final List<CreateEatInOrderLineItemCommand> createEatInOrderLineItemCommands = command.lineItems();
        final EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems(createEatInOrderLineItemCommands.stream()
                .map(it -> new EatInOrderLineItem(
                        null,
                        new EatInOrderLineItemMenu(it.command().menuId(), it.command().name(), it.command().price()),
                        new Quantity(it.quantity()))
                ).toList());
        return new EatInOrder(eatInOrderLineItems, eatInOrderMenus, orderTable);
    }
}
