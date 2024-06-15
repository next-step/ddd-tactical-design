package kitchenpos.eatinorders.application;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.eatinorders.domain.*;
import kitchenpos.eatinorders.domain.EatInOrder;
import kitchenpos.takeoutorders.domain.MenuPriceFoundEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EatInOrderService {
  private final EatInOrderRepository orderRepository;
  private final EatInOrderTableRepository orderTableRepository;
  private final ApplicationEventPublisher applicationEventPublisher;

  public EatInOrderService(
      final EatInOrderRepository orderRepository,
      final EatInOrderTableRepository orderTableRepository,
      final ApplicationEventPublisher applicationEventPublisher) {
    this.orderRepository = orderRepository;
    this.orderTableRepository = orderTableRepository;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @Transactional
  public EatInOrderResponseDto create(final EatInOrderRequestDto request) {

    final List<EatInOrderLineItemRequestDto> eatInOrderLineItemRequestDtos =
        request.getEatInOrderLineItemRequestDtos();

    final List<EatInOrderLineItem> eatInOrderLineItems =
        this.createTakeoutOrderLineItems(eatInOrderLineItemRequestDtos);

    final EatInOrderTable orderTable = findOrderTable(request.getOrderTableId());

    final EatInOrder eatInOrder = EatInOrder.createOrder(eatInOrderLineItems, orderTable);
    orderRepository.save(eatInOrder);
    return EatInOrderResponseDto.create(eatInOrder);
  }

  @Transactional
  public EatInOrderResponseDto accept(final UUID orderId) {

    final EatInOrder eatInOrder =
        orderRepository.findById(orderId).orElseThrow(NoSuchElementException::new);
    eatInOrder.accepted();
    return EatInOrderResponseDto.create(eatInOrder);
  }

  @Transactional
  public EatInOrderResponseDto serve(final UUID orderId) {
    final EatInOrder eatInOrder =
        orderRepository.findById(orderId).orElseThrow(NoSuchElementException::new);
    eatInOrder.serve();
    return EatInOrderResponseDto.create(eatInOrder);
  }

  @Transactional
  public EatInOrderResponseDto complete(final UUID orderId) {
    final EatInOrder eatInOrder =
        orderRepository.findById(orderId).orElseThrow(NoSuchElementException::new);
    eatInOrder.completed();
    return EatInOrderResponseDto.create(eatInOrder);
  }

  @Transactional(readOnly = true)
  public List<EatInOrder> findAll() {
    return orderRepository.findAll();
  }

  private List<EatInOrderLineItem> createTakeoutOrderLineItems(
      final List<EatInOrderLineItemRequestDto> eatInOrderLineItemRequestDtos) {

    final List<EatInOrderLineItem> eatInOrderLineItems = new ArrayList<>();
    for (EatInOrderLineItemRequestDto eatInOrderLineItemRequestDto :
        eatInOrderLineItemRequestDtos) {
      final UUID menuId = eatInOrderLineItemRequestDto.getMenuId();
      final BigDecimal price = eatInOrderLineItemRequestDto.getPrice();
      final long quantity = eatInOrderLineItemRequestDto.getQuantity();

      applicationEventPublisher.publishEvent(new MenuPriceFoundEvent(this, menuId, price));
      eatInOrderLineItems.add(EatInOrderLineItem.createItem(quantity, menuId));
    }
    return eatInOrderLineItems;
  }

  private EatInOrderTable findOrderTable(UUID orderTableId) {
    final EatInOrderTable orderTable =
        orderTableRepository.findById(orderTableId).orElseThrow(NoSuchElementException::new);
    if (!orderTable.getOccupied()) {
      throw new IllegalStateException();
    }

    return orderTable;
  }
}
