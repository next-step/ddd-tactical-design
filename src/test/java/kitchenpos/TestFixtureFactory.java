package kitchenpos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import kitchenpos.common.infra.external.FakePurgomalumClient;
import kitchenpos.menu.domain.model.Menu;
import kitchenpos.menu.domain.model.MenuGroup;
import kitchenpos.menu.domain.model.MenuGroupNameCreationService;
import kitchenpos.menu.domain.model.MenuProduct;
import kitchenpos.order.common.model.Order;
import kitchenpos.order.common.model.OrderLineItem;
import kitchenpos.order.common.model.OrderStatus;
import kitchenpos.order.common.model.OrderType;
import kitchenpos.order.eatinorder.application.dto.CreateEatInOrderServiceRq;
import kitchenpos.order.eatinorder.application.dto.CreateEatInOrderServiceRq.OrderLineItemServiceDto;
import kitchenpos.order.eatinorder.domain.model.EatInOrder;
import kitchenpos.order.eatinorder.domain.model.EatInOrderFlow;
import kitchenpos.order.eatinorder.domain.model.OrderTable;
import kitchenpos.order.eatinorder.ui.dto.CreateEatInOrderRq;
import kitchenpos.order.eatinorder.ui.dto.CreateEatInOrderRq.OrderLineItemDto;
import kitchenpos.product.domain.model.Product;
import kitchenpos.product.domain.model.ProductName;
import kitchenpos.product.domain.model.ProductNameCreationService;
import kitchenpos.product.domain.model.ProductPrice;

public class TestFixtureFactory {

    public static MenuGroup createMenuGroup() {
        return new MenuGroup(
                new MenuGroupNameCreationService(new FakePurgomalumClient())
                        .createName("한식")
        );
    }

    public static Product createProduct(BigDecimal value) {
        ProductName productName = createName("김치");
        ProductPrice productPrice = createPrice(value);
        return new Product(productName, productPrice);
    }

    private static ProductName createName(String value) {
        ProductNameCreationService productNameCreationService = new ProductNameCreationService(
                new FakePurgomalumClient());
        return productNameCreationService.createName(value);
    }

    private static ProductPrice createPrice(BigDecimal price) {
        return new ProductPrice(price);
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

    public static Menu createMenu(MenuGroup menuGroup, Product product, long quantity) {
        MenuProduct menuProduct = new MenuProduct(quantity, product, product.getId());
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

    public static Order createOrder(OrderLineItem orderLineItem, OrderTable orderTable, OrderType orderType,
                                    OrderStatus orderStatus, String address) {
        return new Order(orderType, orderStatus, LocalDateTime.now(), List.of(orderLineItem),
                address, orderTable, orderTable.getId());
    }

    public static OrderLineItem createOrderLineItem(Menu menu) {
        return new OrderLineItem(menu, 2, menu.getId(), BigDecimal.valueOf(8000));
    }

    public static EatInOrder createEatInOrderRequestWithEmptyTable(Menu menu,
                                                                   EatInOrderFlow eatInOrderFlow) {
        return new EatInOrder(LocalDateTime.now(),
                List.of(new OrderLineItem(menu, 1, menu.getId(), BigDecimal.valueOf(8000))), eatInOrderFlow);
    }

    public static CreateEatInOrderServiceRq createEatInOrderServiceRq(Menu menu, UUID orderTableId) {
        return new CreateEatInOrderServiceRq(
                List.of(new OrderLineItemServiceDto(menu.getId(), 1, BigDecimal.valueOf(8000))), orderTableId);
    }

    public static CreateEatInOrderRq createEatInOrderRq(Menu menu, UUID orderTableId) {
        return new CreateEatInOrderRq(
                List.of(new OrderLineItemDto(menu.getId(), 1, BigDecimal.valueOf(8000))), orderTableId);
    }

    public static CreateEatInOrderRq createEatInOrderRq(List<UUID> menuIds, UUID orderTableId) {
        return new CreateEatInOrderRq(
                menuIds.stream()
                        .map(id -> new OrderLineItemDto(id, 2, BigDecimal.valueOf(8000)))
                        .toList(),
                orderTableId);
    }
}
