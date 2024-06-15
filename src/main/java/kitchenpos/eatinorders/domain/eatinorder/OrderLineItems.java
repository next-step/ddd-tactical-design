package kitchenpos.eatinorders.domain.eatinorder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import kitchenpos.eatinorders.domain.menu.MenuClient;
import kitchenpos.eatinorders.domain.menu.OrderMenu;
import kitchenpos.menus.domain.tobe.menu.MenuProduct;
import kitchenpos.menus.domain.tobe.menu.MenuProducts;
import kitchenpos.menus.domain.tobe.menu.ProductClient;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Embeddable
public class OrderLineItems {

  @OneToMany(cascade = {CascadeType.PERSIST,
      CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "order")
  private List<OrderLineItem> orderLineItems = new ArrayList<>();

  protected OrderLineItems (){

  }

  private OrderLineItems(MenuClient menuClient, List<OrderLineItem> orderLineItems){
    validate(menuClient, orderLineItems);
    this.orderLineItems.addAll(orderLineItems);
  }
  public static OrderLineItems of(MenuClient menuClient, List<OrderLineItem> orderLineItems) {
    return new OrderLineItems(menuClient, orderLineItems);
  }

  protected void setOrder(final Order order){
    orderLineItems
            .forEach(item -> item.setOrder(order));
  }

  private void validate(MenuClient menuClient, List<OrderLineItem> orderLineItems){
    if (orderLineItems.size() <= 0){
      throw new IllegalArgumentException("주문 메뉴들이 비어있습니다.");
    }

    final List<UUID> menuIds = orderLineItems.stream()
            .map(OrderLineItem::getMenuId)
                    .toList();

    Map<UUID, OrderMenu> orderMenus = menuClient.findMenuInfoByMenuIds(menuIds);

    if (orderMenus.size() != orderLineItems.size()){
      throw new IllegalArgumentException("`메뉴` 내의 `주문상품`들이 삭제된 `상품`이면 안된다.");
    }

    validateHiddenMenu(orderLineItems, orderMenus);
    validateMenuPrice(orderLineItems, orderMenus);

  }

  private void validateMenuPrice(List<OrderLineItem> orderLineItems, Map<UUID, OrderMenu> orderMenus) {
    orderLineItems.stream()
            .filter(
                    orderLineItem -> matchPrice(orderMenus.getOrDefault(orderLineItem.getMenuId(), null), orderLineItem.getPrice())
            )
            .findAny()
            .ifPresent(menu -> {
              throw new IllegalArgumentException("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.");
            });
  }

  private void validateHiddenMenu(List<OrderLineItem> orderLineItems, Map<UUID, OrderMenu> orderMenus) {
    orderLineItems.stream()
            .filter(
                    orderLineItem -> matchIsDisplayed(orderMenus.getOrDefault(orderLineItem.getMenuId(), null))
            ).findAny()
            .ifPresent(menu -> {
              throw new IllegalArgumentException("숨겨진 메뉴는 주문할 수 없다.");
            });
  }

  private boolean matchIsDisplayed(OrderMenu menu){
    Optional<OrderMenu> orderMenu = Optional.ofNullable(menu);

    OrderMenu matched = orderMenu.orElseThrow(() -> new IllegalArgumentException("메뉴가 존재하지 않습니다."));

    return  !matched.isDisplayed();
  }

  private boolean matchPrice(OrderMenu menu, BigDecimal orderLineItemPrice){
    Optional<OrderMenu> orderMenu = Optional.ofNullable(menu);

    OrderMenu matched = orderMenu.orElseThrow(() -> new IllegalArgumentException("메뉴가 존재하지 않습니다."));

    return !orderLineItemPrice.equals(matched.price());
  }

  protected int getSize() {
    return orderLineItems.size();
  }
}
