package kitchenpos.eatinorders.tobe.domain.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.eatinorders.tobe.domain.order.createsupporter.OrderTable;
import kitchenpos.eatinorders.tobe.domain.order.createsupporter.OrderTableReader;
import kitchenpos.eatinorders.tobe.domain.order.createsupporter.OrderedMenu;
import kitchenpos.eatinorders.tobe.domain.order.createsupporter.OrderedMenuReader;
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
    List<OrderLineItemCreateCommand> orderLineItemCreateCommands = command.getOrderLineItems();
    Map<UUID, OrderedMenu> orderedMenus = getOrderedMenus(orderLineItemCreateCommands);
    List<EatInOrderLineItem> orderLineItems = new ArrayList<>();
    for (OrderLineItemCreateCommand orderLineItem : orderLineItemCreateCommands) {
      OrderedMenu orderedMenu = orderedMenus.get(orderLineItem.getMenuId());
      EatInOrderLineItem eatInOrderLineItem = createEatInOrderLineItem(orderedMenu, orderLineItem);
      orderLineItems.add(eatInOrderLineItem);
    }
    return orderLineItems;
  }

  private EatInOrderLineItem createEatInOrderLineItem(OrderedMenu orderedMenu, OrderLineItemCreateCommand command) {
    validatePurchasable(orderedMenu, command.getPrice());
    return new EatInOrderLineItem(command.getQuantity(), orderedMenu.getMenuId(), command.getPrice());
  }

  private void validatePurchasable(OrderedMenu orderedMenu, Price price) {
    if(Objects.isNull(orderedMenu)) {
      throw new NoSuchElementException();
    }
    if (!orderedMenu.isDisplayed()) {
      throw new IllegalStateException();
    }
    if (!orderedMenu.getPrice().equals(price)) {
      throw new IllegalArgumentException();
    }
  }

  private Map<UUID, OrderedMenu> getOrderedMenus(
      List<OrderLineItemCreateCommand> orderLineItemCreateCommands) {
    validateOrderLineItemSize(orderLineItemCreateCommands);
    List<UUID> menuIds = orderLineItemCreateCommands.stream()
        .map(OrderLineItemCreateCommand::getMenuId)
        .toList();
    Map<UUID, OrderedMenu> orderedMenuMap = orderedMenuReader.findAllByIdIn(menuIds)
        .stream()
        .collect(Collectors.toMap(OrderedMenu::getMenuId, menu -> menu));
    validateExistsMenu(orderLineItemCreateCommands, orderedMenuMap);
    return orderedMenuMap;
  }

  private void validateExistsMenu(List<OrderLineItemCreateCommand> orderLineItemCreateCommands,
      Map<UUID, OrderedMenu> orderedMenuMap) {
    if (orderLineItemCreateCommands.size() != orderedMenuMap.values().size()) {
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
