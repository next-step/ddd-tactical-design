package kitchenpos.eatinorders.tobe.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.eatinorders.tobe.domain.interfaces.event.EventPublisher;
import kitchenpos.eatinorders.tobe.domain.model.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.model.Menu;
import kitchenpos.eatinorders.tobe.domain.model.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.model.OrderTable;
import kitchenpos.eatinorders.tobe.domain.repository.EatInOrderRepository;
import kitchenpos.eatinorders.tobe.domain.repository.MenuRepository;
import kitchenpos.eatinorders.tobe.domain.repository.OrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EatInOrderService {

    private final EatInOrderRepository eatInOrderRepository;
    private final MenuRepository menuRepository;
    private final OrderTableRepository orderTableRepository;
    private final EventPublisher eventPublishClient;

    public EatInOrderService(
            final EatInOrderRepository eatInOrderRepository,
            final MenuRepository menuRepository,
            final OrderTableRepository orderTableRepository,
            final EventPublisher eventPublishClient
    ) {
        this.eatInOrderRepository = eatInOrderRepository;
        this.menuRepository = menuRepository;
        this.orderTableRepository = orderTableRepository;
        this.eventPublishClient = eventPublishClient;
    }

    @Transactional
    public EatInOrder create(final EatInOrder request) {
        validateOrderLineItemRequests(request.getOrderLineItems());
        validateRequestOrderTable(request.getOrderTableId());

        EatInOrder eatInOrder = new EatInOrder(request.getOrderLineItems(), request.getOrderTableId());

        return eatInOrderRepository.save(eatInOrder);
    }

    @Transactional
    public EatInOrder accept(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);

        eatInOrder.accept();

        return eatInOrder;
    }

    @Transactional
    public EatInOrder serve(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);

        eatInOrder.serve();

        return eatInOrder;
    }

    @Transactional
    public EatInOrder complete(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);

        eatInOrder.complete(eventPublishClient);

        return eatInOrder;
    }

    @Transactional(readOnly = true)
    public List<EatInOrder> findAll() {
        return eatInOrderRepository.findAll();
    }

    private void validateOrderLineItemRequests(List<OrderLineItem> orderLineItemRequests) {
        if (Objects.isNull(orderLineItemRequests) || orderLineItemRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }

        final List<Menu> menus = menuRepository.findAllByIdIn(
                orderLineItemRequests.stream()
                        .map(OrderLineItem::getMenuId)
                        .collect(Collectors.toList())
        );
        if (menus.size() != orderLineItemRequests.size()) {
            throw new IllegalArgumentException();
        }

        validateMenuDisplayed(menus);
        validateOrderLineItemRequestPrice(orderLineItemRequests, menus);
    }

    private void validateMenuDisplayed(List<Menu> menus) {
        if (menus.stream().anyMatch(menu -> !menu.isDisplayed())) {
            throw new IllegalStateException();
        }
    }


    private void validateOrderLineItemRequestPrice(List<OrderLineItem> orderLineItemRequests, List<Menu> menus) {
        orderLineItemRequests.forEach(orderLineItem -> {
            final Optional<Menu> matchedMenu = menus.stream()
                    .filter(menu -> orderLineItem.getMenuId().equals(menu.getId()))
                    .findFirst();

            matchedMenu.orElseThrow(IllegalArgumentException::new);

            if (matchedMenu.get().getPrice().compareTo(orderLineItem.getMenuPrice()) != 0) {
                throw new IllegalArgumentException();
            }
        });
    }

    private void validateRequestOrderTable(UUID orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
        if (orderTable.isEmpty()) {
            throw new IllegalStateException();
        }
    }
}
