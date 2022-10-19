package kitchenpos.eatinorders.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Embeddable
public class EatInOrderLineItems {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "order_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_eat_in_order_line_item_to_eat_in_order")
    )
    private List<EatInOrderLineItem> values = new ArrayList<>();

    protected EatInOrderLineItems() {
    }

    public EatInOrderLineItems(List<EatInOrderLineItem> values) {
        this.values.addAll(values);
        validateEmpty(this.values);
    }

    private void validateEmpty(List<EatInOrderLineItem> values) {
        if (values.isEmpty()) {
            throw new IllegalArgumentException("주문의 메뉴는 1개 이상이어야 합니다.");
        }
    }

    public List<EatInOrderLineItem> getValues() {
        return values;
    }
}
