package kitchenpos.eatinorders.tobe.application.restaurant;

import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.restaurant.RestaurantTable;

public record RestaurantTableResponse(UUID id, String name, int numberOfGuests, boolean occupied) {

    public static RestaurantTableResponse from(RestaurantTable restaurantTable) {
        return new RestaurantTableResponse(restaurantTable.getId(), restaurantTable.getName(),
            restaurantTable.getNumberOfGuests(), restaurantTable.isOccupied());
    }
}
