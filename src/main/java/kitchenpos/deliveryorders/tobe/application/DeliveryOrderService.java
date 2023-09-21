package kitchenpos.deliveryorders.tobe.application;

import kitchenpos.deliveryorders.tobe.application.dto.DeliveryOrderRequest;
import kitchenpos.deliveryorders.tobe.application.dto.OrderLineItemRequest;
import kitchenpos.deliveryorders.tobe.domain.DeliveryOrder;
import kitchenpos.deliveryorders.tobe.domain.DeliveryOrderLineItem;
import kitchenpos.deliveryorders.tobe.domain.DeliveryOrderRepository;
import kitchenpos.deliveryorders.tobe.infra.KitchenridersClient;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeliveryOrderService {
    private final DeliveryOrderRepository deliveryOrderRepository;
    private final MenuRepository menuRepository;
    private final KitchenridersClient kitchenridersClient;

    public DeliveryOrderService(
        final DeliveryOrderRepository deliveryOrderRepository,
        final MenuRepository menuRepository,
        final KitchenridersClient kitchenridersClient
    ) {
        this.deliveryOrderRepository = deliveryOrderRepository;
        this.menuRepository = menuRepository;
        this.kitchenridersClient = kitchenridersClient;
    }

    @Transactional
    public DeliveryOrder create(final DeliveryOrderRequest request) {
        request.validate();

        final List<DeliveryOrderLineItem> deliveryOrderLineItems = request.getOrderLineItems()
            .stream()
            .map(OrderLineItemRequest::toOrderLineItem)
            .collect(Collectors.toList());

        final List<Menu> menus = findMenus(deliveryOrderLineItems);

        return DeliveryOrder.create(
            menus,
            deliveryOrderLineItems,
            request.getDeliveryAddress()
        );
    }

    @Transactional
    public DeliveryOrder accept(final UUID orderId) {
        final DeliveryOrder order = findOrder(orderId);
        final BigDecimal sumOfOrderLineItemPrice = order.getSumOfOrderLineItemPrice(
            findMenus(order.getOrderLineItemList())
        );
        kitchenridersClient.requestDelivery(orderId, sumOfOrderLineItemPrice, order.getStringDeliveryAddress());
        order.accept();
        return order;
    }

    @Transactional
    public DeliveryOrder serve(final UUID orderId) {
        final DeliveryOrder order = findOrder(orderId);
        order.serve();
        return order;
    }

    @Transactional
    public DeliveryOrder startDelivery(final UUID orderId) {
        final DeliveryOrder order = findOrder(orderId);
        order.startDelivery();
        return order;
    }

    @Transactional
    public DeliveryOrder completeDelivery(final UUID orderId) {
        final DeliveryOrder order = findOrder(orderId);
        order.completeDelivery();
        return order;
    }

    @Transactional
    public DeliveryOrder complete(final UUID orderId) {
        final DeliveryOrder order = findOrder(orderId);
        order.complete();
        return order;
    }

    @Transactional(readOnly = true)
    public List<DeliveryOrder> findAll() {
        return deliveryOrderRepository.findAll();
    }

    private DeliveryOrder findOrder(final UUID orderId) {
        return deliveryOrderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
    }

    private List<Menu> findMenus(final List<DeliveryOrderLineItem> deliveryOrderLineItems) {
        return menuRepository.findAllByIdIn(
            deliveryOrderLineItems.stream()
                .map(DeliveryOrderLineItem::getMenuId)
                .collect(Collectors.toList())
        );
    }
}
