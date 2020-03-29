package kitchenpos.eatinorders.tobe.domain.eatinorder.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "order_table")
public class OrderTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private TableInfo tableInfo;

    protected OrderTable() {
    }

    public static OrderTable emptyTable() {
        return new OrderTable(0, true);
    }

    public static OrderTable nonEmptyTable(int numberOfGuests) {
        return new OrderTable(numberOfGuests, false);
    }

    public OrderTable(Long id, boolean empty) {
        this.id = id;
        this.tableInfo = new TableInfo(empty);
    }

    private OrderTable(int numberOfGuests, boolean empty) {
        tableInfo = new TableInfo(numberOfGuests, empty, null);
    }

    public Long getId() {
        return id;
    }

    public Long getTableGroupId() {
        return tableInfo.getTableGroupId();
    }

    public int getNumberOfGuests() {
        return tableInfo.getNumberOfGuests();
    }

    public boolean isEmpty() {
        return tableInfo.isEmpty();
    }

    public void changeEmpty(OrderTable orderTable) {
        this.tableInfo = orderTable.tableInfo.changeEmpty();
    }

    public void changeNumberOfGuests(OrderTable orderTable) {
        if (tableInfo.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.tableInfo = new TableInfo(orderTable.tableInfo);
    }

    public void group(Long tableGroupId) {
        if (Objects.isNull(tableGroupId) || tableGroupId <= 0) {
            throw new IllegalArgumentException();
        }
        this.tableInfo = tableInfo.group(tableGroupId);
    }

    public void ungroup() {
        this.tableInfo = tableInfo.ungroup();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderTable that = (OrderTable) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(tableInfo, that.tableInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tableInfo);
    }
}
