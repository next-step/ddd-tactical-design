package kitchenpos.eatinorders.tobe.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.eatinorders.tobe.domain.handler.DomainExceptionHandler;
import kitchenpos.eatinorders.tobe.domain.interfaces.KitchenridersClient;
import kitchenpos.eatinorders.tobe.domain.model.DeliveryOrder;
import kitchenpos.eatinorders.tobe.domain.model.Menu;
import kitchenpos.eatinorders.tobe.domain.model.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.repository.DeliveryOrderRepository;
import kitchenpos.eatinorders.tobe.domain.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeliveryOrderService {

    private final DeliveryOrderRepository deliveryOrderRepository;
    private final MenuRepository menuRepository;
    private final KitchenridersClient kitchenridersClient;
    private final DomainExceptionHandler domainExceptionHandler;

    public DeliveryOrderService(DeliveryOrderRepository deliveryOrderRepository, MenuRepository menuRepository, KitchenridersClient kitchenridersClient, DomainExceptionHandler domainExceptionHandler) {
        this.deliveryOrderRepository = deliveryOrderRepository;
        this.menuRepository = menuRepository;
        this.kitchenridersClient = kitchenridersClient;
        this.domainExceptionHandler = domainExceptionHandler;
    }

    @Transactional
    public DeliveryOrder create(final DeliveryOrder request) {
        validateOrderLineItemRequests(request.getOrderLineItems());

        DeliveryOrder deliveryOrder = new DeliveryOrder(request.getOrderLineItems(), request.getDeliveryAddress());
        return deliveryOrderRepository.save(deliveryOrder);
    }

    @Transactional
    public DeliveryOrder accept(final UUID orderId) {
        final DeliveryOrder deliveryOrder = deliveryOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);

        deliveryOrder.accept(kitchenridersClient, domainExceptionHandler);

        return deliveryOrder;
    }

    @Transactional
    public DeliveryOrder serve(final UUID orderId) {
        final DeliveryOrder deliveryOrder = deliveryOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);

        deliveryOrder.serve(domainExceptionHandler);
        return deliveryOrder;
    }

    @Transactional
    public DeliveryOrder startDelivery(final UUID orderId) {
        final DeliveryOrder deliveryOrder = deliveryOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);

        deliveryOrder.startDelivery(domainExceptionHandler);

        return deliveryOrder;
    }

    @Transactional
    public DeliveryOrder completeDelivery(final UUID orderId) {
        final DeliveryOrder deliveryOrder = deliveryOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);

        deliveryOrder.completeDelivery(domainExceptionHandler);

        return deliveryOrder;
    }

    @Transactional
    public DeliveryOrder complete(final UUID orderId) {
        final DeliveryOrder deliveryOrder = deliveryOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);

        deliveryOrder.complete(domainExceptionHandler);

        return deliveryOrder;
    }

    @Transactional(readOnly = true)
    public List<DeliveryOrder> findAll() {
        return deliveryOrderRepository.findAll();
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
        validateOrderLineItemRequestQuantityAndPrice(orderLineItemRequests, menus);
    }

    private void validateMenuDisplayed(List<Menu> menus) {
        if (menus.stream().anyMatch(menu -> !menu.isDisplayed())) {
            throw new IllegalStateException();
        }
    }

    private void validateOrderLineItemRequestQuantityAndPrice(List<OrderLineItem> orderLineItemRequests, List<Menu> menus) {
        orderLineItemRequests.forEach(orderLineItem -> {
            if (orderLineItem.getQuantity() < 0) {
                throw new IllegalArgumentException();
            }

            final Optional<Menu> matchedMenu = menus.stream()
                    .filter(menu -> orderLineItem.getMenuId().equals(menu.getId()))
                    .findFirst();

            matchedMenu.orElseThrow(IllegalArgumentException::new);

            if (matchedMenu.get().getPrice().compareTo(orderLineItem.getMenuPrice()) != 0) {
                throw new IllegalArgumentException();
            }
        });
    }
}
