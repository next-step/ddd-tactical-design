package kitchenpos.eatinorder.tobe.domain.order;

import kitchenpos.annotation.DomainService;
import kitchenpos.eatinorder.shared.MenuClient;
import kitchenpos.eatinorder.tobe.domain.ordertable.OrderTable;
import kitchenpos.eatinorder.tobe.domain.ordertable.OrderTableRepository;
import kitchenpos.menus.tobe.domain.menu.Menu;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@DomainService
public class EatInOrderFactory {
    private final MenuClient menuClient;
    private final OrderTableRepository orderTableRepository;

    public EatInOrderFactory(final MenuClient menuClient, final OrderTableRepository orderTableRepository) {
        this.menuClient = menuClient;
        this.orderTableRepository = orderTableRepository;
    }

    public EatInOrder create(UUID orderTableId, List<OrderLineItem> orderLineItemRequests) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
        if (!orderTable.isOccupied()) {
            throw new IllegalStateException("손님이 착석중인 테이블이 아닙니다.");
        }

        final var items = createOrderItems(orderLineItemRequests);

        return EatInOrder.create(LocalDateTime.now(), items, orderTable.getId());
    }

    private @NotNull OrderLineItems createOrderItems(List<OrderLineItem> orderLineItemRequests) {
        return new OrderLineItems(orderLineItemRequests.stream()
                .map(this::createOrderLineItem)
                .toList());
    }

    private @NotNull OrderLineItem createOrderLineItem(OrderLineItem orderLineItemRequest) {
        final Menu menu = validateOrderLineMenu(orderLineItemRequest);
        return OrderLineItem.of(
                orderLineItemRequest.getQuantity(),
                menu.getId(),
                BigDecimal.valueOf(menu.getMenuPrice()));
    }

    private @NotNull Menu validateOrderLineMenu(OrderLineItem orderLineItemRequest) {
        final Menu menu = menuClient.findByOrderLineMenuId(orderLineItemRequest.getMenuId());
        if (!menu.isMenuDisplayStatus()) {
            throw new IllegalStateException("노출된 메뉴만 주문 가능합니다.");
        }
        if (isDifferentPrice(orderLineItemRequest.getPrice(), menu.getMenuPrice())) {
            throw new IllegalArgumentException("주문 접수된 메뉴의 가격이 메뉴 가격과 다릅니다.");
        }
        return menu;
    }

    private static boolean isDifferentPrice(BigDecimal orderLinePrice, Long menuPrice) {
        return orderLinePrice.compareTo(BigDecimal.valueOf(menuPrice)) != 0;
    }
}
