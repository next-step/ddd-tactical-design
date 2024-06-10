package kitchenpos.eatinorders.tobe.domain.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.eatinorders.tobe.domain.order.createsupporter.RegisteredOrderTable;
import kitchenpos.eatinorders.tobe.domain.order.createsupporter.RegisteredOrderTableReader;
import kitchenpos.eatinorders.tobe.domain.order.createsupporter.RegisteredMenu;
import kitchenpos.eatinorders.tobe.domain.order.createsupporter.RegisteredMenuReader;
import kitchenpos.eatinorders.tobe.domain.order.dto.OrderCreateCommand;
import kitchenpos.eatinorders.tobe.domain.order.dto.OrderLineItemCreateCommand;
import kitchenpos.supports.domain.tobe.Price;
import org.springframework.stereotype.Component;

@Component
public class EatInOrderCreator {

  private final RegisteredMenuReader registeredMenuReader;
  private final RegisteredOrderTableReader registeredOrderTableReader;

  public EatInOrderCreator(RegisteredMenuReader registeredMenuReader, RegisteredOrderTableReader registeredOrderTableReader) {
    this.registeredMenuReader = registeredMenuReader;
    this.registeredOrderTableReader = registeredOrderTableReader;
  }

  public EatInOrder create(OrderCreateCommand command) {
    validateOccupiedTable(command.getOrderTableId());
    List<EatInOrderLineItem> orderLineItems = createOrderLineItems(command);
    return new EatInOrder(command.getOrderTableId(), orderLineItems);
  }

  private List<EatInOrderLineItem> createOrderLineItems(OrderCreateCommand command) {
    List<OrderLineItemCreateCommand> orderLineItemCreateCommands = command.getOrderLineItems();
    Map<UUID, RegisteredMenu> orderedMenus = getOrderedMenus(orderLineItemCreateCommands);
    List<EatInOrderLineItem> orderLineItems = new ArrayList<>();
    for (OrderLineItemCreateCommand orderLineItem : orderLineItemCreateCommands) {
      RegisteredMenu registeredMenu = orderedMenus.get(orderLineItem.getMenuId());
      EatInOrderLineItem eatInOrderLineItem = createEatInOrderLineItem(registeredMenu, orderLineItem);
      orderLineItems.add(eatInOrderLineItem);
    }
    return orderLineItems;
  }

  private EatInOrderLineItem createEatInOrderLineItem(RegisteredMenu registeredMenu, OrderLineItemCreateCommand command) {
    validatePurchasable(registeredMenu, command.getPrice());
    return new EatInOrderLineItem(command.getQuantity(), registeredMenu.getMenuId(), command.getPrice());
  }

  private void validatePurchasable(RegisteredMenu registeredMenu, Price price) {
    if(Objects.isNull(registeredMenu)) {
      throw new NoSuchElementException();
    }
    if (!registeredMenu.isDisplayed()) {
      throw new IllegalStateException();
    }
    if (!registeredMenu.getPrice().equals(price)) {
      throw new IllegalArgumentException();
    }
  }

  private Map<UUID, RegisteredMenu> getOrderedMenus(
      List<OrderLineItemCreateCommand> orderLineItemCreateCommands) {
    validateOrderLineItemSize(orderLineItemCreateCommands);
    List<UUID> menuIds = orderLineItemCreateCommands.stream()
        .map(OrderLineItemCreateCommand::getMenuId)
        .toList();
    Map<UUID, RegisteredMenu> orderedMenuMap = registeredMenuReader.findAllByIdIn(menuIds)
        .stream()
        .collect(Collectors.toMap(RegisteredMenu::getMenuId, menu -> menu));
    validateExistsMenu(orderLineItemCreateCommands, orderedMenuMap);
    return orderedMenuMap;
  }

  private void validateExistsMenu(List<OrderLineItemCreateCommand> orderLineItemCreateCommands,
      Map<UUID, RegisteredMenu> orderedMenuMap) {
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
    RegisteredOrderTable registeredOrderTable = registeredOrderTableReader.getById(orderTableId)
        .orElseThrow(NoSuchElementException::new);
    if (!registeredOrderTable.isOccupied()) {
      throw new IllegalStateException();
    }
  }
}
