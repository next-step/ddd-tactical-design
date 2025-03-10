package kitchenpos.mock.fixture;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.eatinorder.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.eatinorder.EatInOrderLineItem;
import kitchenpos.eatinorders.tobe.domain.eatinorder.EatInOrderStatus;
import kitchenpos.eatinorders.tobe.domain.restaurant.RestaurantTable;
import kitchenpos.eatinorders.tobe.dto.restaurant.RestaurantTableChangeRequest;
import kitchenpos.eatinorders.tobe.dto.restaurant.RestaurantTableCreateRequest;
import kitchenpos.shared.domain.OrderType;

public class RestaurantTableFixture {

    public static EatInOrder eatInorder(EatInOrderStatus eatInOrderStatus,
        RestaurantTable restaurantTable) {

        final List<EatInOrderLineItem> eatInOrderLineItems = List.of(
            new EatInOrderLineItem(1L, UUID.randomUUID(), 1));

        return new EatInOrder(
            UUID.randomUUID(),
            OrderType.EAT_IN,
            eatInOrderStatus,
            LocalDateTime.now(),
            eatInOrderLineItems,
            restaurantTable.getId());
    }

    public static RestaurantTable orderTable(boolean occupied, int numberOfGuests) {
        return new RestaurantTable(UUID.randomUUID(), "1번 테이블", numberOfGuests, occupied);
    }

    public static RestaurantTableCreateRequest createOrderTableRequest(final String name) {
        return new RestaurantTableCreateRequest(name);
    }

    public static RestaurantTableChangeRequest changeNumberOfGuestsRequest(
        final int numberOfGuests) {
        return new RestaurantTableChangeRequest(numberOfGuests);
    }
}
