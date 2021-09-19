package kitchenpos.eatinorders.tobe.domain.model.orderstatus;

import kitchenpos.eatinorders.tobe.domain.model.OrderStatus;

public class Completed implements OrderStatus {

    private final String status = "Completed";

    @Override
    public OrderStatus proceed() throws IllegalStateException {
        throw new IllegalStateException("이미 계산 완료된 상태입니다.");
    }

    @Override
    public String getStatus() {
        return status;
    }
}
