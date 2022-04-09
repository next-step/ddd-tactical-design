package kitchenpos.eatinorders.tobe.domain.order;

import static kitchenpos.menus.fixture.OrderLineItemReqFixture.buildByMenuAndQuantity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import kitchenpos.eatinorders.tobe.domain.dto.MenuRes;
import kitchenpos.eatinorders.tobe.domain.domainservice.OrderTableDomainService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EatInOrderTest {

  @DisplayName("1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다.")
  @Test
  void createTest() {
    //given & when
    EatInOrder result = buildWaitingOrder();

    //then
    assertThat(result).isNotNull();
  }

  @DisplayName("메뉴를 고르지 않으면 주문을 등록할 수 없다.")
  @Test
  void createFailTest() {
    //given
    MenuRes existMenu = new MenuRes(1L, "테스트메뉴", BigDecimal.valueOf(1000L));
    Long orderTableId = 1L;
    MenuDomainService existMenuSupplyMenuDomainService = new MenuDomainService(existMenu);
    OrderTableRelatedService notEmptyTableSupplyService = new OrderTableRelatedService(false, false);

    //when & then
    assertThatThrownBy(() -> new EatInOrder(Collections.emptyList(),
        existMenuSupplyMenuDomainService, orderTableId, notEmptyTableSupplyService))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("고른 메뉴가 현재 보이지 않는 메뉴면 주문을 등록할 수 없다.")
  @Test
  void createFailCauseInvisibleMenuTest() {
    //given
    MenuRes existMenu = new MenuRes(1L, "테스트메뉴", BigDecimal.valueOf(1000L));
    Long orderTableId = 1L;
    MenuDomainService invisibleMenuSupplyMenuDomainService = new MenuDomainService(null);
    OrderTableRelatedService notEmptyTableSupplyService = new OrderTableRelatedService(false, false);

    //when & then
    assertThatThrownBy(() -> new EatInOrder(Arrays.asList(buildByMenuAndQuantity(existMenu, 2L)),
        invisibleMenuSupplyMenuDomainService, orderTableId, notEmptyTableSupplyService))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("고른 메뉴의 현재 가격과 선택한 가격이 다르면 주문을 등록할 수 없다.")
  @Test
  void createFailCauseInvalidMenuPriceTest() {
    //given
    MenuRes existMenu = new MenuRes(1L, "테스트메뉴", BigDecimal.valueOf(1000L));
    Long orderTableId = 1L;
    MenuDomainService differencePriceMenuSupplyMenuDomainService = new MenuDomainService(
        new MenuRes(1L, "테스트메뉴", BigDecimal.valueOf(2000L)));
    OrderTableRelatedService notEmptyTableSupplyService = new OrderTableRelatedService(false, false);

    //when & then
    assertThatThrownBy(() -> new EatInOrder(Arrays.asList(buildByMenuAndQuantity(existMenu, 2L)),
        differencePriceMenuSupplyMenuDomainService, orderTableId,
        notEmptyTableSupplyService))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("매장 주문이 이루어진 테이블의 상태가 사용중이 아니면 주문을 등록할 수 없다.")
  @Test
  void createFailCauseNotUsedTableTest() {
    //given
    MenuRes existMenu = new MenuRes(1L, "테스트메뉴", BigDecimal.valueOf(1000L));
    Long orderTableId = 1L;
    MenuDomainService existMenuSupplyMenuDomainService = new MenuDomainService(existMenu);
    OrderTableRelatedService emptyTableSupplyService = new OrderTableRelatedService(true, false);

    //when & then
    assertThatThrownBy(() -> new EatInOrder(Arrays.asList(buildByMenuAndQuantity(existMenu, 2L)),
        existMenuSupplyMenuDomainService, orderTableId,
        emptyTableSupplyService))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("접수 대기 상태의 주문을 접수할 수 있다.")
  @Test
  void acceptTest() {
    //given
    EatInOrder waitingOrder = buildWaitingOrder();

    //when
    EatInOrder result = waitingOrder.accept();

    //then
    assertThat(result.getOrderStatus()).isEqualTo(EatInOrderStatus.ACCEPTED);
  }

  @DisplayName("접수된 주문을 서빙할 수 있다.")
  @Test
  void serveTest() {
    //given
    EatInOrder acceptedOrder = buildAcceptedOrder();

    //when
    EatInOrder result = acceptedOrder.serve();

    //then
    assertThat(result.getOrderStatus()).isEqualTo(EatInOrderStatus.SERVED);
  }

  @DisplayName("서빙된 주문을 완료할 수 있다.")
  @Test
  void completeTest() {
    //given
    EatInOrder servedOrder = buildServedOrder();
    OrderTableRelatedService notHasInCompletedOrdersOrderTableSupplyService = new OrderTableRelatedService(false, false);

    //when
    EatInOrder result = servedOrder.complete(notHasInCompletedOrdersOrderTableSupplyService);

    //then
    assertThat(result.getOrderStatus()).isEqualTo(EatInOrderStatus.COMPLETED);
  }

  @DisplayName("주문을 완료할 때 주문 테이블에 속한 주문이 모두 완료 된 상태면 주문 테이블을 비운다.")
  @Test
  void emptyTableTest() {
    //given
    EatInOrder servedOrder = buildServedOrder();
    OrderTableRelatedService notHasInCompletedOrdersOrderTableSupplyService = new OrderTableRelatedService(false, false);

    //when
    servedOrder.complete(notHasInCompletedOrdersOrderTableSupplyService);

    //then
    assertThat(notHasInCompletedOrdersOrderTableSupplyService.isEmptyTableMethodCalled()).isTrue();
  }

  @DisplayName("주문을 완료할 때 주문 테이블에 속한 주문이 모두 완료 되지않았으면 주문 테이블을 비우지 않는다.")
  @Test
  void emptyTableNotCalledTest() {
    //given
    EatInOrder servedOrder = buildServedOrder();
    OrderTableRelatedService hasInCompletedOrdersOrderTableSupplyService = new OrderTableRelatedService(false, true);

    //when
    servedOrder.complete(hasInCompletedOrdersOrderTableSupplyService);

    //then
    assertThat(hasInCompletedOrdersOrderTableSupplyService.isEmptyTableMethodCalled()).isFalse();
  }


  private EatInOrder buildWaitingOrder() {
    MenuRes existMenu = new MenuRes(1L, "테스트메뉴", BigDecimal.valueOf(1000L));
    Long orderTableId = 1L;
    kitchenpos.eatinorders.tobe.domain.domainservice.MenuDomainService existMenuSupplyMenuDomainService = new MenuDomainService(existMenu);
    OrderTableDomainService notEmptyTableSupplyService = new OrderTableRelatedService(false, false);

    return new EatInOrder(Arrays.asList(buildByMenuAndQuantity(existMenu, 2L)),
        existMenuSupplyMenuDomainService, orderTableId, notEmptyTableSupplyService);
  }

  private EatInOrder buildAcceptedOrder() {
    return buildWaitingOrder().accept();
  }

  private EatInOrder buildServedOrder() {
    return buildAcceptedOrder().serve();
  }

  private static class MenuDomainService implements kitchenpos.eatinorders.tobe.domain.domainservice.MenuDomainService {

    private MenuRes menu;

    public MenuDomainService(MenuRes menu) {
      this.menu = menu;
    }

    @Override
    public Map<Long, MenuRes> findDisplayedMenus(Iterable<Long> ids) {
      if (menu == null) {
        return Collections.emptyMap();
      }
      return Stream.of(menu)
          .collect(Collectors.toMap(MenuRes::getMenuId, Function.identity()));
    }
  }

  private static class OrderTableRelatedService implements OrderTableDomainService {

    private boolean empty;

    private boolean inCompletedOrders;

    private boolean emptyTableMethodCalled = false;

    public OrderTableRelatedService(boolean empty, boolean inCompletedOrders) {
      this.empty = empty;
      this.inCompletedOrders = inCompletedOrders;
    }

    @Override
    public boolean isEmptyOrderTable(Long orderTableId) {
      return empty;
    }

    @Override
    public boolean hasInCompletedOrders(Long orderTableId) {
      return inCompletedOrders;
    }

    @Override
    public void emptyTable(Long orderTableId) {
      this.emptyTableMethodCalled = true;
    }

    public boolean isEmptyTableMethodCalled() {
      return emptyTableMethodCalled;
    }
  }

}
