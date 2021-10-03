package kitchenpos.eatinorders.tobe.domain.fixture;

import kitchenpos.common.domain.model.Price;
import kitchenpos.eatinorders.tobe.domain.model.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.model.OrderLineItemQuantity;
import kitchenpos.eatinorders.tobe.domain.validator.OrderLineItemValidator;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderLineItemFixture {

    public static OrderLineItem ORDER_LINE_ITEM_FIXTURE(Long priceValue, Long quantityValue, UUID menuId, OrderLineItemValidator orderLineItemValidator) {
        Price price = new Price(BigDecimal.valueOf(priceValue));
        OrderLineItemQuantity orderLineItemQuantity = new OrderLineItemQuantity(BigDecimal.valueOf(quantityValue));

        return new OrderLineItem(price, orderLineItemQuantity, menuId, orderLineItemValidator);
    }

}
