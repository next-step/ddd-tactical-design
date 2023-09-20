package kitchenpos.deliveryorders.tobe;

import kitchenpos.deliveryorders.tobe.domain.DeliveryOrder;
import kitchenpos.deliveryorders.tobe.domain.OrderLineItem;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuProducts;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static kitchenpos.Fixtures.menuProduct;

public class DeliveryOrderFixtures {

    private static final UUID menuId = UUID.randomUUID();

    public static Menu menu() {
        return menu(menuId, true, 18_000L);
    }

    public static Menu menu(final boolean displayed) {
        return menu(menuId, displayed, 18_000L);
    }

    public static Menu menu(final long price) {
        return menu(menuId, true, price);
    }

    public static Menu menu(final UUID id, final boolean displayed, long price) {
        return Menu.create(
            id,
            "후라이드+후라이드",
            BigDecimal.valueOf(price),
            UUID.randomUUID(),
            displayed,
            new MenuProducts(List.of(menuProduct())),
            (name) -> false
        );
    }

    public static OrderLineItem orderLineItem() {
        return orderLineItem(18_000L, menuId);
    }

    public static OrderLineItem orderLineItem(final UUID menuId) {
        return OrderLineItem.create(1L, menuId, 18_000L);
    }

    public static OrderLineItem orderLineItem(final UUID menuId, final BigDecimal price) {
        return OrderLineItem.create(1L, menuId, price.toBigInteger().longValue());
    }


    public static OrderLineItem orderLineItem(final long quantity) {
        return OrderLineItem.create(quantity, menuId, 18_000L);
    }

    public static OrderLineItem orderLineItem(final Long price, final UUID menuId) {
        return OrderLineItem.create(1L, menuId, price);
    }

    public static DeliveryOrder deliveryOrder() {
        return DeliveryOrder.create(
            List.of(menu()),
            List.of(orderLineItem()),
            "서울시 송파구 위례성대로 2"
        );
    }

    public static DeliveryOrder deliveryOrder(Menu menu) {
        return DeliveryOrder.create(
            List.of(menu),
            List.of(orderLineItem(menu.getId(), menu.getBigDecimalPrice())),
            "서울시 송파구 위례성대로 2"
        );
    }

    public static DeliveryOrder deliveryOrder(String deliveryAddress) {
        return DeliveryOrder.create(
            List.of(menu()),
            List.of(orderLineItem()),
            deliveryAddress
        );
    }

    public static DeliveryOrder acceptedOrder() {
        DeliveryOrder deliveryOrder = deliveryOrder();
        deliveryOrder.accept();
        return deliveryOrder;
    }

    public static DeliveryOrder acceptedOrder(Menu menu) {
        DeliveryOrder deliveryOrder = deliveryOrder(menu);
        deliveryOrder.accept();
        return deliveryOrder;
    }

    public static DeliveryOrder servedOrder() {
        DeliveryOrder deliveryOrder = acceptedOrder();
        deliveryOrder.serve();
        return deliveryOrder;
    }

    public static DeliveryOrder servedOrder(Menu menu) {
        DeliveryOrder deliveryOrder = acceptedOrder(menu);
        deliveryOrder.serve();
        return deliveryOrder;
    }

    public static DeliveryOrder deliveringOrder() {
        DeliveryOrder deliveryOrder = servedOrder();
        deliveryOrder.startDelivery();
        return deliveryOrder;
    }

    public static DeliveryOrder deliveringOrder(Menu menu) {
        DeliveryOrder deliveryOrder = servedOrder(menu);
        deliveryOrder.startDelivery();
        return deliveryOrder;
    }

    public static DeliveryOrder deliveredOrder() {
        DeliveryOrder deliveryOrder = deliveringOrder();
        deliveryOrder.completeDelivery();
        return deliveryOrder;
    }

    public static DeliveryOrder deliveredOrder(Menu menu) {
        DeliveryOrder deliveryOrder = deliveringOrder(menu);
        deliveryOrder.completeDelivery();
        return deliveryOrder;
    }

    public static DeliveryOrder completedOrder() {
        DeliveryOrder deliveryOrder = deliveredOrder();
        deliveryOrder.complete();
        return deliveryOrder;
    }

    public static DeliveryOrder completedOrder(Menu menu) {
        DeliveryOrder deliveryOrder = deliveredOrder(menu);
        deliveryOrder.complete();
        return deliveryOrder;
    }
}
