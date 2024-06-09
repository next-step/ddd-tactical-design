package kitchenpos.eatinorders.tobe.domain.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.eatinorders.tobe.domain.order.create_support.OrderTable;
import kitchenpos.eatinorders.tobe.domain.order.create_support.OrderTableReader;
import kitchenpos.eatinorders.tobe.domain.order.create_support.OrderedMenu;
import kitchenpos.eatinorders.tobe.domain.order.create_support.OrderedMenuReader;
import kitchenpos.eatinorders.tobe.domain.order.dto.OrderCreateCommand;
import kitchenpos.eatinorders.tobe.domain.order.dto.OrderLineItemCreateCommand;
import kitchenpos.supports.domain.tobe.Price;
import org.springframework.stereotype.Component;

@Component
public class EatInOrderCreator {

  private final OrderedMenuReader orderedMenuReader;
  private final OrderTableReader orderTableReader;

  public EatInOrderCreator(OrderedMenuReader orderedMenuReader, OrderTableReader orderTableReader) {
    this.orderedMenuReader = orderedMenuReader;
    this.orderTableReader = orderTableReader;
  }

  public EatInOrder create(OrderCreateCommand command) {
    validateOccupiedTable(command.getOrderTableId());
    List<EatInOrderLineItem> orderLineItems = createOrderLineItems(command);
    return new EatInOrder(command.getOrderTableId(), orderLineItems);
  }

  private List<EatInOrderLineItem> createOrderLineItems(OrderCreateCommand command) {
    List<OrderLineItemCreateCommand> orderLineItemList = command.getOrderLineItems();
    validateOrderLineItemSize(orderLineItemList);
    Map<UUID, OrderedMenu> orderedMenus = getOrderedMenus(orderLineItemList);
    List<EatInOrderLineItem> orderLineItems = new ArrayList<>();
    for (OrderLineItemCreateCommand orderLineItem : orderLineItemList) {
      if (!orderedMenus.containsKey(orderLineItem.getMenuId())) {
        throw new NoSuchElementException();
      }
      OrderedMenu orderedMenu = orderedMenus.get(orderLineItem.getMenuId());
      validatePurchasable(orderedMenu, orderLineItem.getPrice());
      EatInOrderLineItem eatInOrderLineItem = new EatInOrderLineItem(orderLineItem.getQuantity(),
          orderedMenu.getMenuId(), orderLineItem.getPrice());
      orderLineItems.add(eatInOrderLineItem);
    }
    return orderLineItems;
  }

  private void validatePurchasable(OrderedMenu orderedMenu, Price price) {
    if (!orderedMenu.isDisplayed()) {
      throw new IllegalStateException();
    }
    if (!orderedMenu.getPrice().equals(price)) {
      throw new IllegalArgumentException();
    }
  }

  private Map<UUID, OrderedMenu> getOrderedMenus(
      List<OrderLineItemCreateCommand> orderLineItemList) {
    List<UUID> menuIds = orderLineItemList.stream()
        .map(OrderLineItemCreateCommand::getMenuId)
        .toList();
    Map<UUID, OrderedMenu> orderedMenuMap = orderedMenuReader.findAllByIdIn(menuIds)
        .stream()
        .collect(Collectors.toMap(OrderedMenu::getMenuId, menu -> menu));
    validateExistsMenu(orderLineItemList, orderedMenuMap);
    return orderedMenuMap;
  }

  private void validateExistsMenu(List<OrderLineItemCreateCommand> orderLineItemList,
      Map<UUID, OrderedMenu> orderedMenuMap) {
    if (orderLineItemList.size() != orderedMenuMap.values().size()) {
      throw new IllegalArgumentException();
    }
  }

  private void validateOrderLineItemSize(List<OrderLineItemCreateCommand> orderLineItems) {
    if (Objects.isNull(orderLineItems) || orderLineItems.isEmpty()) {
      throw new IllegalArgumentException();
    }
  }

  private void validateOccupiedTable(UUID orderTableId) {
    OrderTable orderTable = orderTableReader.getById(orderTableId)
        .orElseThrow(NoSuchElementException::new);
    if (!orderTable.isOccupied()) {
      throw new IllegalStateException();
    }
  }
}
