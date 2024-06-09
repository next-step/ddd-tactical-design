package kitchenpos.fixture;

import kitchenpos.support.domain.OrderLineItem;
import kitchenpos.support.dto.OrderLineItemCreateRequest;

import java.util.Random;
import java.util.UUID;

import static kitchenpos.fixture.MenuFixture.menu;

public class Fixtures {
    public static final UUID INVALID_ID = new UUID(0L, 0L);

    public static OrderLineItem orderLineItem() {
        return new OrderLineItem(new Random().nextLong(), menu().getId(), menu().getPrice(), 0);
    }

    public static OrderLineItemCreateRequest orderLineItemRequest() {
        return new OrderLineItemCreateRequest(menu().getId(), menu().getPrice(), 0);
    }
}
