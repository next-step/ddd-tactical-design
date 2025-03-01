package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.application.dto.CreateEatInOrderCommand;
import kitchenpos.eatinorders.tobe.application.dto.CreateEatInOrderLineItemCommand;
import kitchenpos.eatinorders.tobe.domain.InMemoryEatInOrderMenuRepository;
import kitchenpos.eatinorders.tobe.domain.InMemoryEatInOrderRepository;
import kitchenpos.eatinorders.tobe.domain.InMemoryEatInOrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.order.*;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderId;
import kitchenpos.eatinorders.tobe.domain.order.vo.Quantity;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class EatInOrderService {

    private final EatInOrderMenuRepository eatInOrderMenuRepository = new InMemoryEatInOrderMenuRepository();
    private final EatInOrderTableRepository orderTableRepository = new InMemoryEatInOrderTableRepository();
    private final EatInOrderRepository eatInOrderRepository = new InMemoryEatInOrderRepository();

    public EatInOrder create(final CreateEatInOrderCommand command) {
        final EatInOrderMenus eatInOrderMenus = eatInOrderMenuRepository.findAllByIdIn(command.menuIds());
        final EatInOrderTable orderTable = orderTableRepository.findById(command.orderTableId()).orElseThrow(IllegalArgumentException::new);
        final EatInOrder eatInOrder = eatInOrder(command, eatInOrderMenus, orderTable);
        return eatInOrderRepository.save(eatInOrder);
    }

    public EatInOrder accept(final UUID orderId) {
        final EatInOrder order = eatInOrderRepository.findById(new EatInOrderId(orderId))
                .orElseThrow(NoSuchElementException::new);
        order.accepted();
        return order;
    }

    public EatInOrder serve(final UUID orderId) {
        final EatInOrder order = eatInOrderRepository.findById(new EatInOrderId(orderId))
                .orElseThrow(NoSuchElementException::new);
        order.served();
        return order;
    }

    public EatInOrder complete(final UUID orderId) {
        final EatInOrder order = eatInOrderRepository.findById(new EatInOrderId(orderId))
                .orElseThrow(NoSuchElementException::new);
        order.completed();
//        order.setStatus(OrderStatus.COMPLETED);
//        final OrderTable orderTable = order.getOrderTable();
//        if (!eatInOrderRepository.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED)) {
//            orderTable.setNumberOfGuests(0);
//            orderTable.setOccupied(false);
//        }
        return order;
    }

    public List<EatInOrder> findAll() {
        return eatInOrderRepository.findAll();
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
