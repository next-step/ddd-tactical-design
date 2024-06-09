package kitchenpos.deliveryorders.application;

import kitchenpos.deliveryorders.domain.DeliveryOrder;
import kitchenpos.deliveryorders.domain.DeliveryOrderRepository;
import kitchenpos.deliveryorders.dto.DeliveryOrderCreateRequest;
import kitchenpos.deliveryorders.dto.DeliveryOrderResponse;
import kitchenpos.support.application.OrderLineItemsFactory;
import kitchenpos.support.domain.MenuClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class DeliveryOrderService {
    private final DeliveryOrderRepository orderRepository;
    private final MenuClient menuClient;
    private final DeliveryAgencyClient deliveryAgencyClient;

    public DeliveryOrderService(
        final DeliveryOrderRepository orderRepository,
        final MenuClient menuClient,
        final DeliveryAgencyClient deliveryAgencyClient
    ) {
        this.orderRepository = orderRepository;
        this.menuClient = menuClient;
        this.deliveryAgencyClient = deliveryAgencyClient;
    }

    @Transactional
    public DeliveryOrderResponse create(final DeliveryOrderCreateRequest request) {
        DeliveryOrder order = DeliveryOrder.create(
                new OrderLineItemsFactory(menuClient).create(request.orderLineItems()),
                request.deliveryAddress()
        );
        DeliveryOrder result = orderRepository.save(order);
        return DeliveryOrderResponse.from(result);
    }

    @Transactional
    public DeliveryOrderResponse accept(final UUID orderId) {
        final DeliveryOrder order = getOrder(orderId);
        order.accept();
        requestDelivery(order);
        return DeliveryOrderResponse.from(order);
    }

    @Transactional
    public DeliveryOrderResponse serve(final UUID orderId) {
        final DeliveryOrder order = getOrder(orderId);
        order.serve();
        return DeliveryOrderResponse.from(order);
    }

    @Transactional
    public DeliveryOrderResponse startDelivery(final UUID orderId) {
        final DeliveryOrder order = getOrder(orderId);
        order.startDelivery();
        return DeliveryOrderResponse.from(order);
    }

    @Transactional
    public DeliveryOrderResponse completeDelivery(final UUID orderId) {
        final DeliveryOrder order = getOrder(orderId);
        order.completeDelivery();
        return DeliveryOrderResponse.from(order);
    }

    @Transactional
    public DeliveryOrderResponse complete(final UUID orderId) {
        final DeliveryOrder order = getOrder(orderId);
        order.complete();
        return DeliveryOrderResponse.from(order);
    }

    @Transactional(readOnly = true)
    public List<DeliveryOrderResponse> findAll() {
        return orderRepository.findAll().stream()
                .map(DeliveryOrderResponse::from)
                .toList();
    }

    private DeliveryOrder getOrder(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
    }

    private void requestDelivery(DeliveryOrder order) {
        deliveryAgencyClient.requestDelivery(order.getId(), order.totalAmounts(), order.getDeliveryAddress());
    }
}
