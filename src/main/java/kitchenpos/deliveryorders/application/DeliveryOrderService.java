package kitchenpos.deliveryorders.application;

import java.math.BigDecimal;
import java.util.*;
import kitchenpos.deliveryorders.domain.DeliveryOrder;
import kitchenpos.deliveryorders.domain.DeliveryOrderLineItem;
import kitchenpos.deliveryorders.domain.DeliveryOrderRepository;
import kitchenpos.deliveryorders.domain.KitchenridersClient;
import kitchenpos.eatinorders.domain.*;
import kitchenpos.takeoutorders.domain.MenuPriceFoundEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeliveryOrderService {
  private final DeliveryOrderRepository orderRepository;
  private final KitchenridersClient kitchenridersClient;
  private final ApplicationEventPublisher applicationEventPublisher;

  public DeliveryOrderService(
      final DeliveryOrderRepository orderRepository,
      final KitchenridersClient kitchenridersClient,
      final ApplicationEventPublisher applicationEventPublisher) {
    this.orderRepository = orderRepository;
    this.kitchenridersClient = kitchenridersClient;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @Transactional
  public DeliveryOrderResponseDto create(final DeliveryOrderRequestDto request) {

    final List<DeliveryOrderLineItemRequestDto> deliveryOrderLineItemRequestDtos =
        request.getDeliveryOrderLineItemRequestDtos();

    final List<DeliveryOrderLineItem> deliveryOrderLineItems =
        this.createDeliveryOrderLineItems(deliveryOrderLineItemRequestDtos);
    final DeliveryOrder deliveryOrder =
        DeliveryOrder.createOrder(deliveryOrderLineItems, request.getDeliveryAddress());

    return DeliveryOrderResponseDto.create(orderRepository.save(deliveryOrder));
  }

  @Transactional
  public DeliveryOrderResponseDto accept(final UUID orderId) {
    final DeliveryOrder order =
        orderRepository.findById(orderId).orElseThrow(NoSuchElementException::new);

    order.accept(kitchenridersClient);
    return DeliveryOrderResponseDto.create(order);
  }

  @Transactional
  public DeliveryOrderResponseDto serve(final UUID orderId) {
    final DeliveryOrder order =
        orderRepository.findById(orderId).orElseThrow(NoSuchElementException::new);
    order.serve();
    return DeliveryOrderResponseDto.create(order);
  }

  @Transactional
  public DeliveryOrderResponseDto startDelivery(final UUID orderId) {
    final DeliveryOrder order =
        orderRepository.findById(orderId).orElseThrow(NoSuchElementException::new);

    order.startDelivery();
    return DeliveryOrderResponseDto.create(order);
  }

  @Transactional
  public DeliveryOrderResponseDto completeDelivery(final UUID orderId) {
    final DeliveryOrder order =
        orderRepository.findById(orderId).orElseThrow(NoSuchElementException::new);

    order.completeDelivery();
    return DeliveryOrderResponseDto.create(order);
  }

  @Transactional
  public DeliveryOrderResponseDto complete(final UUID orderId) {
    final DeliveryOrder order =
        orderRepository.findById(orderId).orElseThrow(NoSuchElementException::new);
    order.complete();
    return DeliveryOrderResponseDto.create(order);
  }

  @Transactional(readOnly = true)
  public List<DeliveryOrderResponseDto> findAll() {
    return orderRepository.findAll().stream().map(DeliveryOrderResponseDto::create).toList();
  }

  private List<DeliveryOrderLineItem> createDeliveryOrderLineItems(
      final List<DeliveryOrderLineItemRequestDto> deliveryOrderLineItemRequestDtos) {

    final List<DeliveryOrderLineItem> deliveryOrderLineItems = new ArrayList<>();
    for (DeliveryOrderLineItemRequestDto deliveryOrderLineItemRequestDto :
        deliveryOrderLineItemRequestDtos) {
      final UUID menuId = deliveryOrderLineItemRequestDto.getMenuId();
      final BigDecimal price = deliveryOrderLineItemRequestDto.getPrice();
      final long quantity = deliveryOrderLineItemRequestDto.getQuantity();

      applicationEventPublisher.publishEvent(new MenuPriceFoundEvent(this, menuId, price));
      deliveryOrderLineItems.add(DeliveryOrderLineItem.createItem(quantity, menuId, price));
    }
    return deliveryOrderLineItems;
  }
}
