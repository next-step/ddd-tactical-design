package kitchenpos.deliveryorders.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.deliveryorders.domain.DeliveryOrderRepository;
import kitchenpos.deliveryorders.domain.KitchenridersClient;
import kitchenpos.eatinorders.domain.*;
import kitchenpos.eatinorders.domain.EatInOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeliveryOrderService {
  private final DeliveryOrderRepository orderRepository;
  private final KitchenridersClient kitchenridersClient;

  public DeliveryOrderService(
      final DeliveryOrderRepository orderRepository, final KitchenridersClient kitchenridersClient) {
    this.orderRepository = orderRepository;
    this.kitchenridersClient = kitchenridersClient;
  }

  @Transactional
  public EatInOrder create(final EatInOrder request) {
    /*final OrderType type = request.getType();
    if (Objects.isNull(type)) {
        throw new IllegalArgumentException();
    }
    final List<OrderLineItem> orderLineItemRequests = request.getOrderLineItems();
    if (Objects.isNull(orderLineItemRequests) || orderLineItemRequests.isEmpty()) {
        throw new IllegalArgumentException();
    }
    final List<Menu> menus = menuRepository.findAllByIdIn(
        orderLineItemRequests.stream()
            .map(OrderLineItem::getMenuId)
            .toList()
    );
    if (menus.size() != orderLineItemRequests.size()) {
        throw new IllegalArgumentException();
    }
    final List<OrderLineItem> orderLineItems = new ArrayList<>();
    for (final OrderLineItem orderLineItemRequest : orderLineItemRequests) {
        final long quantity = orderLineItemRequest.getQuantity();
        if (type != OrderType.EAT_IN) {
            if (quantity < 0) {
                throw new IllegalArgumentException();
            }
        }
        final Menu menu = menuRepository.findById(orderLineItemRequest.getMenuId())
            .orElseThrow(NoSuchElementException::new);
        if (!menu.isDisplayed()) {
            throw new IllegalStateException();
        }
        if (menu.getPrice().compareTo(orderLineItemRequest.getPrice()) != 0) {
            throw new IllegalArgumentException();
        }
        final OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setMenu(menu);
        orderLineItem.setQuantity(quantity);
        orderLineItems.add(orderLineItem);
    }
    EatInOrder order = new EatInOrder();
    order.setId(UUID.randomUUID());
    order.setType(type);
    order.setStatus(DeliveryOrderStatus.WAITING);
    order.setOrderDateTime(LocalDateTime.now());
    order.setOrderLineItems(orderLineItems);
    if (type == OrderType.DELIVERY) {
        final String deliveryAddress = request.getDeliveryAddress();
        if (Objects.isNull(deliveryAddress) || deliveryAddress.isEmpty()) {
            throw new IllegalArgumentException();
        }
        order.setDeliveryAddress(deliveryAddress);
    }
    if (type == OrderType.EAT_IN) {
        final OrderTable orderTable = orderTableRepository.findById(request.getOrderTableId())
            .orElseThrow(NoSuchElementException::new);
        if (!orderTable.isOccupied()) {
            throw new IllegalStateException();
        }
        order.setOrderTable(orderTable);
    }
    return orderRepository.save(order);*/
    return null;
  }

  @Transactional
  public EatInOrder accept(final UUID orderId) {
    /*final EatInOrder order = orderRepository.findById(orderId)
        .orElseThrow(NoSuchElementException::new);
    if (order.getStatus() != DeliveryOrderStatus.WAITING) {
        throw new IllegalStateException();
    }
    if (order.getType() == OrderType.DELIVERY) {
        BigDecimal sum = BigDecimal.ZERO;
        for (final OrderLineItem orderLineItem : order.getOrderLineItems()) {
            sum = orderLineItem.getMenu()
                .getPrice()
                .multiply(BigDecimal.valueOf(orderLineItem.getQuantity()));
        }
        kitchenridersClient.requestDelivery(orderId, sum, order.getDeliveryAddress());
    }
    order.setStatus(DeliveryOrderStatus.ACCEPTED);
    return order;*/
    return null;
  }

  @Transactional
  public EatInOrder serve(final UUID orderId) {
    final EatInOrder order =
        orderRepository.findById(orderId).orElseThrow(NoSuchElementException::new);
    if (order.getStatus() != EatInOrderStatus.ACCEPTED) {
      throw new IllegalStateException();
    }
    order.setStatus(EatInOrderStatus.SERVED);
    return order;
  }

  @Transactional
  public EatInOrder startDelivery(final UUID orderId) {
    final EatInOrder order =
        orderRepository.findById(orderId).orElseThrow(NoSuchElementException::new);
    if (order.getType() != EatInOrderType.DELIVERY) {
      throw new IllegalStateException();
    }
    if (order.getStatus() != EatInOrderStatus.SERVED) {
      throw new IllegalStateException();
    }
    order.setStatus(EatInOrderStatus.DELIVERING);
    return order;
  }

  @Transactional
  public EatInOrder completeDelivery(final UUID orderId) {
    final EatInOrder order =
        orderRepository.findById(orderId).orElseThrow(NoSuchElementException::new);
    if (order.getStatus() != EatInOrderStatus.DELIVERING) {
      throw new IllegalStateException();
    }
    order.setStatus(EatInOrderStatus.DELIVERED);
    return order;
  }

  @Transactional
  public EatInOrder complete(final UUID orderId) {
    final EatInOrder order =
        orderRepository.findById(orderId).orElseThrow(NoSuchElementException::new);
    final EatInOrderType type = order.getType();
    final EatInOrderStatus status = order.getStatus();
    if (type == EatInOrderType.DELIVERY) {
      if (status != EatInOrderStatus.DELIVERED) {
        throw new IllegalStateException();
      }
    }
    if (type == EatInOrderType.TAKEOUT || type == EatInOrderType.EAT_IN) {
      if (status != EatInOrderStatus.SERVED) {
        throw new IllegalStateException();
      }
    }
    order.setStatus(EatInOrderStatus.COMPLETED);
    if (type == EatInOrderType.EAT_IN) {
      final EatInOrderTable orderTable = order.getOrderTable();
      if (!orderRepository.existsByOrderTableAndStatusNot(orderTable, EatInOrderStatus.COMPLETED)) {
        orderTable.setNumberOfGuests(0);
        orderTable.setOccupied(false);
      }
    }
    return order;
  }

  @Transactional(readOnly = true)
  public List<EatInOrder> findAll() {
    return orderRepository.findAll();
  }
}
