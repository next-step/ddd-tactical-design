package kitchenpos.eatinorders.domain;

public enum OrderType {
    DELIVERY, TAKEOUT, EAT_IN;

    public boolean isDelivery() {
        return this == OrderType.DELIVERY;
    }

    public boolean isTakeOut() {
        return this == OrderType.TAKEOUT;
    }

    public boolean isEatIn() {
        return this == OrderType.EAT_IN;
    }
}
