package kitchenpos.order.eatinorder.domain.model;

import static kitchenpos.order.eatinorder.exception.EatInOrderExceptionMessage.EAT_IN_ORDER_FLOW_NOT_FOUND_EXCEPTION;

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
                .orElseThrow(() -> new IllegalArgumentException(EAT_IN_ORDER_FLOW_NOT_FOUND_EXCEPTION.getMessage()));
    }
}
