package kitchenpos.order.eatinorder.domain.model;

import java.util.Arrays;

public enum EatInOrderFlow {
    WAITING(EatInOrderStatus.NONE),
    ACCEPTED(EatInOrderStatus.WAITING),
    SERVED(EatInOrderStatus.ACCEPTED),
    COMPLETED(EatInOrderStatus.SERVED);

    private final EatInOrderStatus previousOrderStatus;

    EatInOrderFlow(EatInOrderStatus previousOrderStatus) {
        this.previousOrderStatus = previousOrderStatus;
    }

    public boolean validateOrderStatus(EatInOrderStatus nextOrderStatus) {
        EatInOrderFlow nextOrderFlow = findByOrderStatus(nextOrderStatus);
        return this.name().equals(nextOrderFlow.previousOrderStatus.name());
    }

    public static EatInOrderFlow findByOrderStatus(EatInOrderStatus nextOrderStatus) {
        return Arrays.stream(values())
                .filter(flow -> flow.name().equals(nextOrderStatus.name()))
                .findFirst()
                .orElseThrow();
    }
}
