package kitchenpos.common.domain.model;

import java.util.Arrays;

public enum

OrderStatus {
    WAITING(1),
    ACCEPTED(2),
    SERVED(3),
    DELIVERING(4),
    DELIVERED(5),
    COMPLETED(6);

    private int level;

    OrderStatus(int level) {
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }

    public OrderStatus advanceLevel(OrderType orderType) {
        if (this.level == 6) {
            throw new IllegalArgumentException("이미 완료 상태입니다.");
        }

        if (orderType != OrderType.DELIVERY && this.level == 3) {
            return COMPLETED;
        }

        final int nextLevel = this.level + 1;
        return Arrays.stream(values())
                .filter(value -> value.level == nextLevel)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("level 에 맞는 OrderStatus 가 없습니다."));
    }

}
