package kitchenpos.eatinorders.tobe.domain.eatinorder.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "table_group")
public class TableGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private OrderTables orderTables;

    @CreationTimestamp
    private LocalDateTime createdDate;

    public Long getId() {
        return id;
    }

    public List<OrderTable> getOrderTables() {
        return orderTables.getOrderTables();
    }

    protected TableGroup() {
    }

    public TableGroup(List<OrderTable> savedOrderTables) {
        if (CollectionUtils.isEmpty(savedOrderTables) || savedOrderTables.size() < 2) {
            throw new IllegalArgumentException();
        }

        for (final OrderTable savedOrderTable : savedOrderTables) {
            if (!savedOrderTable.isEmpty() || Objects.nonNull(savedOrderTable.getTableGroupId())) {
                throw new IllegalArgumentException();
            }
        }

        this.orderTables = new OrderTables(savedOrderTables);
    }

    public List<Long> getOrderTableIds() {
        return orderTables.getOrderTableIds();
    }

    public void updateOrderTables(List<OrderTable> savedOrderTables) {
        this.orderTables = new OrderTables(savedOrderTables);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableGroup that = (TableGroup) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(orderTables, that.orderTables);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderTables);
    }
}
