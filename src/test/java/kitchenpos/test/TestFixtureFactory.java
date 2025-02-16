package kitchenpos.test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import kitchenpos.common.domain.Name;
import kitchenpos.common.domain.NameCreationService;
import kitchenpos.common.domain.Price;
import kitchenpos.menu.domain.model.Menu;
import kitchenpos.menu.domain.model.MenuGroup;
import kitchenpos.menu.domain.model.MenuProduct;
import kitchenpos.order.common.model.Order;
import kitchenpos.order.common.model.OrderLineItem;
import kitchenpos.order.common.model.OrderStatus;
import kitchenpos.order.common.model.OrderType;
import kitchenpos.order.eatinorder.domain.model.OrderTable;
import kitchenpos.product.domain.model.Product;

public class TestFixtureFactory {

    public static MenuGroup createMenuGroup() {
        return new MenuGroup("한식");
    }

    public static Product createProduct(BigDecimal value) {
        Name name = createName("김치");
        Price price = createPrice(value);
        return new Product(name, price);
    }

    private static Name createName(String value) {
        NameCreationService nameCreationService = new NameCreationService(new FakePurgomalumClient());
        return nameCreationService.createName(value);
    }

    private static Price createPrice(BigDecimal price) {
        return new Price(price);
    }

    public static Menu createMenuWithProductAndGroup() {
        MenuGroup menuGroup = createMenuGroup();
        Product product = createProduct(BigDecimal.valueOf(5000));
        MenuProduct menuProduct = new MenuProduct(1, product, product.getId());
        return new Menu("김치찌개", BigDecimal.valueOf(8000), true, List.of(menuProduct), menuGroup,
                menuGroup.getId());
    }

    public static Menu createMenuWithProductAndGroup(boolean displayed) {
        MenuGroup menuGroup = createMenuGroup();
        Product product = createProduct(BigDecimal.valueOf(5000));
        MenuProduct menuProduct = new MenuProduct(1, product, product.getId());
        return new Menu("김치찌개", BigDecimal.valueOf(8000), displayed, List.of(menuProduct), menuGroup,
                menuGroup.getId());
    }

    public static Menu createMenu(MenuGroup menuGroup, Product product) {
        MenuProduct menuProduct = new MenuProduct(1, product, product.getId());
        return new Menu("김치찌개", BigDecimal.valueOf(8000), true, List.of(menuProduct), menuGroup,
                menuGroup.getId());
    }

    public static Menu createMenuWithProductAndGroup(String name, long price, Product product) {
        MenuProduct menuProduct = new MenuProduct(1, product, product.getId());
        MenuGroup menuGroup = new MenuGroup("찌개");
        return new Menu(name, BigDecimal.valueOf(price), true, List.of(menuProduct), menuGroup, menuGroup.getId());
    }

    public static OrderTable createEmptyOrderTable() {
        return new OrderTable("비어 있는 테이블", 0, false);
    }

    public static OrderTable createUsingOrderTable() {
        return new OrderTable("사용 중인 테이블", 4, true);
    }

    public static Product createProduct(String name, long price) {
        return new Product(createName(name), createPrice(BigDecimal.valueOf(price)));
    }

    public static Order createOrderWithDeliveryType(OrderLineItem orderLineItem, OrderTable orderTable,
                                                    OrderStatus status) {
        return new Order(OrderType.DELIVERY, status, LocalDateTime.now(), List.of(orderLineItem),
                "주소", orderTable, orderTable.getId());
    }

    public static Order createOrderWithTakeOutType(OrderLineItem orderLineItem, OrderTable orderTable,
                                                   OrderStatus status) {
        return new Order(OrderType.TAKEOUT, status, LocalDateTime.now(), List.of(orderLineItem),
                "주소", orderTable, orderTable.getId());
    }

    public static Order createOrderWithEatInType(OrderLineItem orderLineItem, OrderTable orderTable,
                                                 OrderStatus status) {
        return new Order(OrderType.EAT_IN, status, LocalDateTime.now(), List.of(orderLineItem),
                "주소", orderTable, orderTable.getId());
    }

    public static Order createOrder(OrderLineItem orderLineItem, OrderTable orderTable, OrderType orderType,
                                    OrderStatus orderStatus, String address) {
        return new Order(orderType, orderStatus, LocalDateTime.now(), List.of(orderLineItem),
                address, orderTable, orderTable.getId());
    }

    public static OrderLineItem createOrderLineItem(Menu menu) {
        return new OrderLineItem(menu, 2, menu.getId(), BigDecimal.valueOf(8000));
    }
}
