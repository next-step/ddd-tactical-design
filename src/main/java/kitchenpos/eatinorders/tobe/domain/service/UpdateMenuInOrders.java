package kitchenpos.eatinorders.tobe.domain.service;

import kitchenpos.eatinorders.tobe.domain.entity.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.repository.EatInOrderRepository;
import kitchenpos.menus.tobe.domain.entity.ChangedMenuEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdateMenuInOrders {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateMenuInOrders.class);
    private final EatInOrderRepository orderRepository;

    public UpdateMenuInOrders(EatInOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @EventListener
    public void updateMenu(ChangedMenuEvent menuEvent) {
        LOGGER.info("메뉴가 변경되어 주문 항목을 확인합니다.");

        List<EatInOrder> orders = orderRepository.findAllByMenuId(menuEvent.getMenuId());

        for (EatInOrder order : orders) {
            order.updateMenuInOrders(menuEvent.getMenuId(), menuEvent.getPrice(), menuEvent.isDisplayed());
        }
    }
}
