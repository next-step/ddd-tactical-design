package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.domain.*;

public class EatInOrderService {

    private final EatInOrderMenuRepository eatInOrderMenuRepository = new InMemoryEatInOrderMenuRepository();
    private final OrderTableRepository orderTableRepository = new InMemoryOrderTableRepository();
    private final EatInOrderRepository eatInOrderRepository = new InMemoryEatInOrderRepository();

    public EatInOrder create(final EatInOrder eatInOrder) {
        final EatInOrderMenus eatInOrderMenus = eatInOrderMenuRepository.findAllByIdIn(eatInOrder.menuIds());
        final OrderTable orderTable = orderTableRepository.findById(eatInOrder.orderTableId())
                .orElseThrow(IllegalArgumentException::new);
        eatInOrder.verify(eatInOrderMenus);
        eatInOrder.verify(orderTable);
        return eatInOrderRepository.save(eatInOrder);
    }
}
