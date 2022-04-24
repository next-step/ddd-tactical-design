package kitchenpos.eatinorders.domain.tobe.domain.vo;

public enum TableEmptyStatus {
    OCCUPIED, EMPTY;

    public boolean isOccupied() {
        return this == TableEmptyStatus.OCCUPIED;
    }

    public boolean isEmpty() {
        return this == TableEmptyStatus.EMPTY;
    }
}
