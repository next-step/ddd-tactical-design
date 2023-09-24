package kitchenpos.eatinorders.domain;

import kitchenpos.eatinorders.application.dto.request.OrderLineItemCreateRequest;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class OrderValidator {
    private final MenuRepository menuRepository;

    public OrderValidator(final MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public void validateMenu(OrderLineItemCreateRequest orderLineItemRequest) {
        final Menu menu = menuRepository.findById(orderLineItemRequest.getMenuId())
            .orElseThrow(NoSuchElementException::new);
        if (!menu.isDisplayed()) {
            throw new IllegalStateException();
        }
        menu.validateSamePrice(orderLineItemRequest.getPrice());
    }

    public static void validateOrderLineItemRequests(List<OrderLineItemCreateRequest> orderLineItemRequests) {
        if (Objects.isNull(orderLineItemRequests) || orderLineItemRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    public void validateExistMenu(List<OrderLineItemCreateRequest> orderLineItemRequests) {
        final List<Menu> menus = menuRepository.findAllByIdIn(
            orderLineItemRequests.stream()
                .map(OrderLineItemCreateRequest::getMenuId)
                .collect(Collectors.toList())
        );
        if (menus.size() != orderLineItemRequests.size()) {
            throw new IllegalArgumentException();
        }
    }
}
