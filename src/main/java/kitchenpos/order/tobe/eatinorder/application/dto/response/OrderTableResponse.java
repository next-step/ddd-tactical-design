package kitchenpos.order.tobe.eatinorder.application.dto.response;

import java.util.UUID;

public record OrderTableResponse(UUID id, String name, int numberOfGuests, boolean isOccupied) {
}
