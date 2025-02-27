package kitchenpos.eatinorder.adapter.out.client;

import kitchenpos.eatinorder.application.port.out.MenuEatInOrderLineItemMapper;
import kitchenpos.eatinorder.application.service.model.OrderLineItemRequest;
import kitchenpos.eatinorder.domain.model.todo.EatInOrderLineItem;
import kitchenpos.menu.adapter.out.persistance.JpaMenuEntityEntityRepository;
import kitchenpos.menu.adapter.out.persistance.entity.MenuEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Component
public class MenuClient implements MenuEatInOrderLineItemMapper {
    private final JpaMenuEntityEntityRepository menuEntityRepository;

    public MenuClient(final JpaMenuEntityEntityRepository menuEntityRepository) {
        this.menuEntityRepository = menuEntityRepository;
    }

    @Override
    public List<EatInOrderLineItem> toEatInOrderLines(final List<OrderLineItemRequest> orderLineItemRequests) {
        if (Objects.isNull(orderLineItemRequests) || orderLineItemRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }

        final List<MenuEntity> menus = menuEntityRepository.findAllByIdIn(
                orderLineItemRequests.stream()
                        .map(OrderLineItemRequest::getMenuId)
                        .toList()
        );
        if (menus.size() != orderLineItemRequests.size()) {
            throw new IllegalArgumentException();
        }

        final List<EatInOrderLineItem> eatInOrderLineItems = new ArrayList<>();
        for (final OrderLineItemRequest orderLineItemRequest : orderLineItemRequests) {
            final long quantity = orderLineItemRequest.getQuantity();
            final MenuEntity menu = menuEntityRepository.findById(orderLineItemRequest.getMenuId())
                    .orElseThrow(NoSuchElementException::new);
            if (!menu.isDisplayed()) {
                throw new IllegalStateException();
            }
            if (menu.getPrice().compareTo(orderLineItemRequest.getPrice()) != 0) {
                throw new IllegalArgumentException();
            }
            eatInOrderLineItems.add(EatInOrderLineItem.of(
                    null,
                    menu.getId(),
                    quantity,
                    menu.getPrice().longValue(),
                    menu.isDisplayed()
            ));
        }
        return eatInOrderLineItems;
    }
}
