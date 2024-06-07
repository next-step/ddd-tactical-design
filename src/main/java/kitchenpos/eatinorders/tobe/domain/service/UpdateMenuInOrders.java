package kitchenpos.eatinorders.tobe.domain.service;

import kitchenpos.eatinorders.tobe.domain.entity.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.repository.EatInOrderRepository;
import kitchenpos.menus.tobe.domain.entity.ChangedMenuEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdateMenuInOrders {
    private final EatInOrderRepository orderRepository;

    public UpdateMenuInOrders(EatInOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @EventListener
    public void updateMenu(ChangedMenuEvent menuEvent) {
        System.out.println("메뉴가 변경됐습니다.");

        List<EatInOrder> orders = orderRepository.findAllByMenuId(menuEvent.getMenuId());

        for (EatInOrder order : orders) {
            order.updateMenuInOrders(menuEvent.getMenuId(), menuEvent.getPrice(), menuEvent.isDisplayed());
        }
    }
}
