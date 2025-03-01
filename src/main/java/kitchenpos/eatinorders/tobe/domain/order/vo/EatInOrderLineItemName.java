package kitchenpos.eatinorders.tobe.domain.order.vo;

import java.util.Objects;

public class EatInOrderLineItemName {
    private String value;

    public EatInOrderLineItemName(final String value) {
        if(Objects.isNull(value)) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final EatInOrderLineItemName that = (EatInOrderLineItemName) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }
}
