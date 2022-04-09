package kitchenpos.eatinorders.tobe.domain.order;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import kitchenpos.eatinorders.tobe.domain.dto.MenuRes;
import kitchenpos.eatinorders.tobe.domain.dto.OrderLineItemReq;
import kitchenpos.eatinorders.tobe.domain.domainservice.MenuDomainService;

@Embeddable
public class EatInOrderLineItems {

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<EatInOrderLineItem> items;

  protected EatInOrderLineItems() {}

  public EatInOrderLineItems(MenuDomainService menuDomainService,
      List<OrderLineItemReq> orderLineItemReqs) {
    this.items = createOrderLIneItems(menuDomainService, orderLineItemReqs);
  }

  private List<EatInOrderLineItem> createOrderLIneItems(MenuDomainService menuDomainService,
      List<OrderLineItemReq> orderLineItemReqs) {
    validateEmptyItemRequest(orderLineItemReqs);
    Map<Long, MenuRes> menus = menuDomainService.findDisplayedMenus(extractMenuIds(orderLineItemReqs));
    return transformRequest(orderLineItemReqs, menus);
  }

  private List<EatInOrderLineItem> transformRequest(List<OrderLineItemReq> orderLineItemReqs, Map<Long, MenuRes> menus) {
    return orderLineItemReqs.stream()
        .map(req -> {
          MenuRes menu = menus.get(req.getMenuId());
          validateNotExistMenu(menu);
          validateRequestedPrice(req, menu);
          return req.toOrderLineItem(menu);
        })
        .collect(Collectors.toList());
  }

  private void validateRequestedPrice(OrderLineItemReq req, MenuRes menu) {
    if (req.isInvalidPrice(menu)) {
      throw new IllegalArgumentException("주문 가격과 메뉴 가격이 달라 주문할 수 없습니다.");
    }
  }

  private void validateNotExistMenu(MenuRes menu) {
    if (menu == null) {
      throw new IllegalArgumentException("선택한 메뉴 항목이 존재하지 않아 주문할 수 없습니다.");
    }
  }

  private void validateEmptyItemRequest(List<OrderLineItemReq> orderLineItemReqs) {
    if (isNullOrEmpty(orderLineItemReqs)) {
      throw new IllegalArgumentException("메뉴 항목은 1개 이상이어야 합니다.");
    }
  }

  private boolean isNullOrEmpty(List<OrderLineItemReq> orderLineItemReqs) {
    return Objects.isNull(orderLineItemReqs) || orderLineItemReqs.isEmpty();
  }

  private Iterable<Long> extractMenuIds(List<OrderLineItemReq> orderLineItemReqs) {
    return orderLineItemReqs.stream()
        .map(OrderLineItemReq::getMenuId)
        .collect(Collectors.toList());
  }
}
