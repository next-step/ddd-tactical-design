package kitchenpos.eatinorders.tobe.domain.eatinorder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

public class EatInOrderLineItems {

    public static final String ERROR_MESSAGE_EMPTY = "최소 1개 이상의 주문 항목이 포함되어야 합니다.";


    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "order_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
    private List<EatInOrderLineItem> value;

    public EatInOrderLineItems(List<EatInOrderLineItem> value) {
        validate(value);
        this.value = value;
    }

    protected EatInOrderLineItems() {

    }

    private static void validate(List<EatInOrderLineItem> value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(ERROR_MESSAGE_EMPTY);
        }
    }

    public List<EatInOrderLineItem> getValue() {
        return value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        EatInOrderLineItems that = (EatInOrderLineItems) object;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
