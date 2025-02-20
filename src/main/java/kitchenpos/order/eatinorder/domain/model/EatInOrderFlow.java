package kitchenpos.order.eatinorder.domain.model;

import java.util.Arrays;

import static kitchenpos.order.eatinorder.exception.EatInOrderExceptionMessage.EAT_IN_ORDER_FLOW_NOT_FOUND_EXCEPTION;

public enum EatInOrderFlow {
    WAITING(EatInOrderStatus.NONE),
    ACCEPTED(EatInOrderStatus.WAITING),
    SERVED(EatInOrderStatus.ACCEPTED),
    COMPLETED(EatInOrderStatus.SERVED);

    private final EatInOrderStatus previousOrderStatus;

    EatInOrderFlow(EatInOrderStatus previousOrderStatus) {
        this.previousOrderStatus = previousOrderStatus;
    }

    public static EatInOrderFlow findByOrderStatus(EatInOrderStatus nextOrderStatus) {
        return Arrays.stream(values())
                .filter(flow -> flow.name().equals(nextOrderStatus.name()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(EAT_IN_ORDER_FLOW_NOT_FOUND_EXCEPTION.getMessage()));
    }

    public boolean validateOrderStatus(EatInOrderStatus nextOrderStatus) {
        EatInOrderFlow nextOrderFlow = findByOrderStatus(nextOrderStatus);
        return this.name().equals(nextOrderFlow.previousOrderStatus.name());
    }
}
