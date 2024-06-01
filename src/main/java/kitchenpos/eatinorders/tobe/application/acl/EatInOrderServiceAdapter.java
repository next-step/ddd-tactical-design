package kitchenpos.eatinorders.tobe.application.acl;

import kitchenpos.eatinorders.tobe.domain.entity.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.entity.OrderTable;
import kitchenpos.eatinorders.tobe.domain.repository.EatInOrderRepository;
import kitchenpos.eatinorders.tobe.domain.repository.OrderTableRepository;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;

import java.util.NoSuchElementException;
import java.util.UUID;

public class EatInOrderServiceAdapter {
    private final EatInOrderRepository orderRepository;
    private final OrderTableRepository orderTableRepository;
    private final MenuRepository menuRepository;

    public EatInOrderServiceAdapter(EatInOrderRepository orderRepository,
                                    OrderTableRepository orderTableRepository,
                                    MenuRepository menuRepository
    ) {
        this.orderRepository = orderRepository;
        this.orderTableRepository = orderTableRepository;
        this.menuRepository = menuRepository;
    }

    public boolean existHideMenu(UUID menuId) {
        Menu menu = findMenuById(menuId);
        if (menu.isNotDisplayed()) {
            return true;
        }
        return false;
    }

    private Menu findMenuById(UUID menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new NoSuchElementException());
    }

    public boolean existIsNotOccupiedOrderTable(EatInOrder order) {
        OrderTable orderTable = findOrderTableBy(order.getOrderTableId());
        return orderTable.isNotOccupied();
    }

    public void clearOrderTable(EatInOrder order) {
        OrderTable orderTable = findOrderTableBy(order.getOrderTableId());
        if (orderRepository.isAllCompleteByOrderTable(orderTable)) {
            orderTable.clear();
        }
    }

    private OrderTable findOrderTableBy(UUID id) {
        return orderTableRepository.findBy(id)
                .orElseThrow(() -> new NoSuchElementException());
    }
}
