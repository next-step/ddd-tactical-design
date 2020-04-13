package kitchenpos.eatinorders.tobe.order.domain;

import org.springframework.util.CollectionUtils;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class OrderLines {
    @ElementCollection
    @CollectionTable(name = "order_line_item", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderLine> orderLines;

    protected OrderLines() {
    }

    OrderLines(final List<OrderLine> orderLines) {
        if (CollectionUtils.isEmpty(orderLines)) {
            throw new IllegalArgumentException("주문라인을 1개 이상 지정해야합니다.");
        }
        validateDuplicateOrderLine(orderLines);
        this.orderLines = new ArrayList<>(orderLines);
    }

    private void validateDuplicateOrderLine(final List<OrderLine> orderLines) {
        if (orderLines.size() != orderLines.stream()
                .map(OrderLine::getMenuId)
                .distinct()
                .count()) {
            throw new IllegalArgumentException("메뉴는 중복될 수 없습니다.");
        }
    }

    List<OrderLine> get() {
        return new ArrayList<>(orderLines);
    }
}
