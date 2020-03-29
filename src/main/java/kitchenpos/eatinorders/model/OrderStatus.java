package kitchenpos.eatinorders.model;

public enum OrderStatus {
    COOKING, MEAL, COMPLETION;

    public boolean isCompleted() {
        return this == COMPLETION;
    }
}
