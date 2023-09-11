package kitchenpos.eatinorders.tobe.domain;

import java.util.Objects;

public class OrderTableName {
    private String name;

    public OrderTableName(String name) {
        checkOrderTableName(name);
        this.name = name;
    }

    public void checkOrderTableName(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    public String getOrderTableName() {
        return this.name;
    }


}
