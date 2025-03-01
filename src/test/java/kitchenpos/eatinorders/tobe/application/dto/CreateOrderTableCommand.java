package kitchenpos.eatinorders.tobe.application.dto;

public record CreateOrderTableCommand(
        String name,
        int numberOfGuests,
        boolean occupied
) {
}
