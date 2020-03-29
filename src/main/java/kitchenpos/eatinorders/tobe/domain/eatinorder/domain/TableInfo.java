package kitchenpos.eatinorders.tobe.domain.eatinorder.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class TableInfo {

    private int numberOfGuests;

    private boolean empty;

    @Column(name = "table_group_id")
    private Long tableGroupId;

    protected TableInfo() {
    }

    TableInfo(boolean empty) {
        this(0, empty, null);
    }

    public TableInfo(TableInfo input) {
        this(input.numberOfGuests, input.empty, input.tableGroupId);
    }

    public TableInfo(int numberOfGuests, boolean empty, Long tableGroupId) {
        if (numberOfGuests < 0) {
            throw new IllegalArgumentException();
        }

        if (numberOfGuests != 0 && empty) {
            throw new IllegalArgumentException();
        }

        this.numberOfGuests = numberOfGuests;
        this.empty = empty;
        this.tableGroupId = tableGroupId;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public boolean isEmpty() {
        return empty;
    }

    public Long getTableGroupId() {
        return tableGroupId;
    }

    TableInfo group(Long tableGroupId) {
        return new TableInfo(numberOfGuests, false, tableGroupId);
    }

    TableInfo ungroup() {
        return new TableInfo(numberOfGuests, false, null);
    }

    public TableInfo changeEmpty() {
        if (Objects.nonNull(tableGroupId)) {
            throw new IllegalArgumentException();
        }

        return new TableInfo(numberOfGuests, empty, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableInfo tableInfo = (TableInfo) o;
        return numberOfGuests == tableInfo.numberOfGuests &&
                empty == tableInfo.empty;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfGuests, empty);
    }
}
