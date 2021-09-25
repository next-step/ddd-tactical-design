package kitchenpos.eatinordertables.dto;

public class OrderCompletedResponse {
    private final boolean isCompleted;

    public OrderCompletedResponse(final boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public boolean isCompleted() {
        return isCompleted;
    }
}
