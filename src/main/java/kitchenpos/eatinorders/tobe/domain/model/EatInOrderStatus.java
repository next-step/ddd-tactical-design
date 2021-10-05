package kitchenpos.eatinorders.tobe.domain.model;

public enum EatInOrderStatus {
    WAITING, ACCEPTED, SERVED, COMPLETED;

    public void verify(EatInOrderStatus eatInOrderStatus) {
        if (this != eatInOrderStatus) {
           throw new IllegalStateException();
        }
    }
}
