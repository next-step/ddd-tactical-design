package kitchenpos.eatinorders.application;

import kitchenpos.eatinorders.application.dto.OrderLineItemRequest;
import kitchenpos.eatinorders.application.dto.OrderRequest;
import kitchenpos.eatinorders.domain.eatinorder.*;
import kitchenpos.eatinorders.domain.menu.MenuClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Service
public class OrderService {
  private final OrderRepository orderRepository;
  private final PassToRiderService passToRiderService;
  private final ClearOrderTableService clearOrderTableService;
  private final MenuClient menuClient;
  private final OrderFactory orderFactory;

  public OrderService(
          final OrderRepository orderRepository, final PassToRiderService passToRiderService,
          final ClearOrderTableService clearOrderTableService, final MenuClient menuClient, final OrderFactory orderFactory) {
    this.orderRepository = orderRepository;
    this.clearOrderTableService = clearOrderTableService;
    this.menuClient = menuClient;
    this.passToRiderService = passToRiderService;
    this.orderFactory = orderFactory;
  }

  @Transactional
  public Order create(final OrderRequest request) {
    if (Objects.isNull(request.getOrderLineItems())) {
      throw new IllegalArgumentException("주문 상품들의 상태가 비정상입니다.");
    }

    final List<OrderLineItem> orderLineItemList = request.getOrderLineItems()
            .stream()
            .map(OrderLineItemRequest::to)
            .toList();

    OrderLineItems orderLineItems = OrderLineItems.of(menuClient, orderLineItemList);

    Order order = orderFactory.of(request.getType(), request.getStatus(), orderLineItems,
            request.getOrderTable().orElse(null), request.getDeliveryAddress());

    return orderRepository.save(order);
  }

  @Transactional
  public Order accept(final UUID orderId) {
    final Order orderRequest = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);

    orderRequest.accept(passToRiderService);
    return orderRequest;
  }

  @Transactional
  public Order serve(final UUID orderId) {
    final Order orderRequest = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);

    orderRequest.serve();
    return orderRequest;
  }

  @Transactional
  public Order startDelivery(final UUID orderId) {
    final Order orderRequest = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);

    orderRequest.delivering();
    return orderRequest;
  }

  @Transactional
  public Order completeDelivery(final UUID orderId) {
    final Order orderRequest = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);

    orderRequest.delivered();
    return orderRequest;
  }

  @Transactional
  public Order complete(final UUID orderId) {
    final Order orderRequest = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);

    orderRequest.complete(clearOrderTableService);
    return orderRequest;
  }

  @Transactional(readOnly = true)
  public List<Order> findAll() {
    return orderRepository.findAll();
  }
}
