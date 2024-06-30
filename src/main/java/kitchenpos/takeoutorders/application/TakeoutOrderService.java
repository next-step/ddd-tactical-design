package kitchenpos.takeoutorders.application;

import java.math.BigDecimal;
import java.util.*;
import kitchenpos.takeoutorders.domain.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TakeoutOrderService {
  private final TakeoutOrderRepository orderRepository;
  private final ApplicationEventPublisher applicationEventPublisher;

  public TakeoutOrderService(
      final TakeoutOrderRepository orderRepository,
      ApplicationEventPublisher applicationEventPublisher) {

    this.orderRepository = orderRepository;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @Transactional
  public TakeoutOrderResponseDto create(final TakeoutOrderRequestDto request) {

    final List<TakeoutOrderLineItemRequestDto> takeoutOrderLineItemRequestDtos =
        request.getTakeoutOrderLineItemRequestDtos();

    final List<TakeoutOrderLineItem> takeoutOrderLineItems =
        this.createTakeoutOrderLineItems(takeoutOrderLineItemRequestDtos);
    final TakeoutOrder takeoutOrder = TakeoutOrder.createOrder(takeoutOrderLineItems);
    orderRepository.save(takeoutOrder);
    return TakeoutOrderResponseDto.create(takeoutOrder);
  }

  @Transactional
  public TakeoutOrderResponseDto accept(final UUID orderId) {

    final TakeoutOrder takeoutOrder =
        orderRepository.findById(orderId).orElseThrow(NoSuchElementException::new);
    takeoutOrder.accepted();
    return TakeoutOrderResponseDto.create(takeoutOrder);
  }

  @Transactional
  public TakeoutOrderResponseDto serve(final UUID orderId) {

    final TakeoutOrder takeoutOrder =
        orderRepository.findById(orderId).orElseThrow(NoSuchElementException::new);
    takeoutOrder.serve();
    return TakeoutOrderResponseDto.create(takeoutOrder);
  }

  @Transactional
  public TakeoutOrderResponseDto complete(final UUID orderId) {

    final TakeoutOrder takeoutOrder =
        orderRepository.findById(orderId).orElseThrow(NoSuchElementException::new);
    takeoutOrder.completed();
    return TakeoutOrderResponseDto.create(takeoutOrder);
  }

  @Transactional(readOnly = true)
  public List<TakeoutOrderResponseDto> findAll() {
    final List<TakeoutOrder> takeoutOrders = orderRepository.findAll();
    return takeoutOrders.stream().map(TakeoutOrderResponseDto::create).toList();
  }

  private List<TakeoutOrderLineItem> createTakeoutOrderLineItems(
      final List<TakeoutOrderLineItemRequestDto> takeoutOrderLineItemRequestDtos) {

    if (Objects.isNull(takeoutOrderLineItemRequestDtos)) {
      throw new IllegalArgumentException();
    }

    final List<TakeoutOrderLineItem> takeoutOrderLineItems = new ArrayList<>();
    for (TakeoutOrderLineItemRequestDto takeoutOrderLineItemRequestDto :
        takeoutOrderLineItemRequestDtos) {
      final UUID menuId = takeoutOrderLineItemRequestDto.getMenuId();
      final BigDecimal price = takeoutOrderLineItemRequestDto.getPrice();
      final long quantity = takeoutOrderLineItemRequestDto.getQuantity();

      applicationEventPublisher.publishEvent(new MenuPriceFoundEvent(this, menuId, price));
      takeoutOrderLineItems.add(TakeoutOrderLineItem.createItem(quantity, menuId));
    }
    return takeoutOrderLineItems;
  }
}
