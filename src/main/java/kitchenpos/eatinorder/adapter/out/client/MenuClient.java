package kitchenpos.eatinorder.adapter.out.client;

import kitchenpos.eatinorder.application.port.out.MenuEatInOrderLineItemMapper;
import kitchenpos.eatinorder.application.service.model.OrderLineItemRequests;
import kitchenpos.eatinorder.domain.model.todo.EatInOrderLineItem;
import kitchenpos.menu.adapter.out.persistance.JpaMenuEntityEntityRepository;
import kitchenpos.menu.adapter.out.persistance.entity.MenuEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MenuClient implements MenuEatInOrderLineItemMapper {
    private final JpaMenuEntityEntityRepository menuEntityRepository;

    public MenuClient(final JpaMenuEntityEntityRepository menuEntityRepository) {
        this.menuEntityRepository = menuEntityRepository;
    }

    @Override
    public List<EatInOrderLineItem> toEatInOrderLines(OrderLineItemRequests orderLineItemRequests) {
        final List<MenuEntity> menus = menuEntityRepository.findAllByIdIn(orderLineItemRequests.getMenuIds());
        if (menus.size() != orderLineItemRequests.size()) {
            throw new IllegalArgumentException();
        }
        return menus.stream()
                .map(menu -> EatInOrderLineItem.of(
                        null,
                        menu.getId(),
                        orderLineItemRequests.getQuantity(menu.getId()),
                        orderLineItemRequests.getPrice(menu.getId()),
                        menu.getPrice().longValue(),
                        menu.isDisplayed()
                ))
                .toList();
    }
}
