package kitchenpos.order.domain.fixture;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.menu.domain.fixture.MenuFixture;
import kitchenpos.menu.domain.model.MenuId;
import kitchenpos.order.common.application.dto.OrderRequest;
import kitchenpos.order.common.application.dto.OrderRequest.OrderLineItemCreate;
import kitchenpos.order.common.domain.entity.OrderLineItem;
import kitchenpos.order.common.domain.entity.OrderType;
import kitchenpos.order.common.domain.model.OrderId;
import kitchenpos.order.common.domain.model.OrderLineItemQty;

public record OrderLineItemFixture(UUID 메뉴아이디, UUID 주문아이디, OrderType 주문유형, long 주문수량, BigDecimal 주문가격) {

    public static final long DEFAULT_ORDER_LINE_ITEM_QTY = 1;
    public static final BigDecimal DEFAULT_ORDER_LINE_ITEM_PRICE = BigDecimal.valueOf(20_000);

    public static OrderLineItemFixture init(UUID orderId, OrderType 주문유형) {
        return new OrderLineItemFixture(
            MenuFixture.init().toEntity().getMenuId().get(),
            orderId,
            주문유형,
            DEFAULT_ORDER_LINE_ITEM_QTY,
            DEFAULT_ORDER_LINE_ITEM_PRICE
        );
    }

    public static OrderLineItemFixture test(UUID 메뉴아이디, UUID 주문아이디, OrderType 주문유형, long 주문수량, BigDecimal 주문가격) {
        return new OrderLineItemFixture(
            메뉴아이디,
            주문아이디,
            주문유형,
            Objects.requireNonNullElse(주문수량, DEFAULT_ORDER_LINE_ITEM_QTY),
            Objects.requireNonNullElse(주문가격, DEFAULT_ORDER_LINE_ITEM_PRICE)
        );
    }

    public OrderLineItem toEntity() {
        return new OrderLineItem(MenuId.of(메뉴아이디), OrderId.of(주문아이디), OrderLineItemQty.of(주문수량, 주문유형), 주문가격);
    }

    public OrderRequest.OrderLineItemCreate create() {
        return new OrderLineItemCreate(메뉴아이디, 주문가격, 주문수량);
    }
}

