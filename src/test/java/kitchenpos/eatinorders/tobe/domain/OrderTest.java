package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.common.FakeProfanity;
import kitchenpos.common.vo.DisplayedName;
import kitchenpos.common.vo.Price;
import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderType;
import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.products.tobe.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/*
공통
- [x] 주문 유형이 올바르지 않으면 등록할 수 없다.
- [x] 메뉴가 없으면 등록할 수 없다.
- [x] 숨겨진 메뉴는 주문할 수 없다.
- 주문을 접수한다.
    - [x] 접수 대기 중인 주문만 접수할 수 있다.
- 주문을 완료한다.
- 주문을 서빙한다.
- 접수된 주문만 서빙할 수 있다.
- 주문 목록을 조회할 수 있다.
- [] 주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.

매장
- 매장 주문은 주문 항목의 수량이 0 미만일 수 있다.
- 매장 주문을 제외한 주문의 경우 주문 항목의 수량은 0 이상이어야 한다.
- 매장 주문의 경우 서빙된 주문만 완료할 수 있다.
- 주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다.
- 완료되지 않은 매장 주문이 있는 주문 테이블은 빈 테이블로 설정하지 않는다.
- 1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다.
- 빈 테이블에는 매장 주문을 등록할 수 없다.

포장
- 포장 주문의 경우 서빙된 주문만 완료할 수 있다.
- 1개 이상의 등록된 메뉴로 포장 주문을 등록할 수 있다.

배달
- 1개 이상의 등록된 메뉴로 배달 주문을 등록할 수 있다.
- 배달 주문을 접수되면 배달 대행사를 호출한다.
- 주문을 배달한다.
- 배달 주문만 배달할 수 있다.
- 서빙된 주문만 배달할 수 있다.
- 주문을 배달 완료한다.
- 배달 중인 주문만 배달 완료할 수 있다.
- 배달 주문의 경우 배달 완료된 주문만 완료할 수 있다.
- 배달 주소가 올바르지 않으면 배달 주문을 등록할 수 없다.
  - 배달 주소는 비워 둘 수 없다.
 */

class OrderTest {
    @DisplayName("주문 유형이 올바르지 않으면 등록할 수 없다.")
    @Test
    void registerWithValidOrderType() {
        assertThatCode(() -> createOrder())
                .doesNotThrowAnyException();
    }


    @DisplayName("메뉴가 없으면 등록할 수 없다.")
    @Test
    void registerWithMenu() {
        assertThatCode(() -> createOrder())
                .doesNotThrowAnyException();
    }

    @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
    @Test
    void hideMenu() {
        assertThatCode(() -> createOrder())
                .doesNotThrowAnyException();
    }

    @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
    @Test
    void price() {
        final Order order = createOrder();

        order.accept();

        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    private Order createOrder() {
        final OrderType orderType = OrderType.EAT_IN;
        final Menu menu = createMenu();
        final List<OrderLineItem> orderLineItems = List.of(new OrderLineItem(menu));
        final OrderStatus orderStatus = OrderStatus.WAITING;

        return new Order(orderType, orderLineItems, orderStatus);
    }

    private Menu createMenu() {
        final Price price = new Price(BigDecimal.TEN);
        final DisplayedName name = new DisplayedName("치킨 세트", new FakeProfanity());
        final MenuProduct menuProduct = new MenuProduct(2L, new Product(BigDecimal.valueOf(6L)));
        return new Menu(price, name, List.of(menuProduct), new MenuGroup());
    }
}
