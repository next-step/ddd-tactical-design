package kitchenpos.eatinorders.tobe.table.domain;

import javax.persistence.*;

@Entity(name = "order_table")
public class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "table_group_id")
    private Long tableGroupId;

    @Column(name = "number_of_guests")
    private int numberOfGuests;

    @Column(name = "empty")
    private boolean empty;

    protected Table() {}

    public Table(final int numberOfGuests, final boolean empty) {
        if (numberOfGuests < 0) {
            throw new IllegalArgumentException("테이블 고객 수는 0명 이상이여야합니다.");
        }
        this.tableGroupId = null;
        this.numberOfGuests = numberOfGuests;
        this.empty = empty;
    }

    public Long getId() {
        return id;
    }

    public Long getTableGroupId() {
        return tableGroupId;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void changeEmpty(final boolean empty) {
        if (tableGroupId != null) {
            throw new IllegalArgumentException("단체테이블에 포함된 경우, 공석 상태를 변경할 수 없습니다.");
        }
        this.empty = empty;
    }

    public void changeNumberOfGuests(final int numberOfGuests) {
        if (numberOfGuests < 0) {
            throw new IllegalArgumentException("테이블 고객 수는 0명 이상이여야합니다.");
        }
        validateTableGuestChangePolicy();
        this.numberOfGuests = numberOfGuests;
    }

    private void validateTableGuestChangePolicy() {
        if (isEmpty()) {
            throw new IllegalArgumentException("테이블이 공석 상태일 때는 변경할 수 없습니다.");
        }
    }

    public void group(final Long tableGroupId) {
        this.tableGroupId = tableGroupId;
        this.empty = false;
    }

    public void ungroup() {
        this.tableGroupId = null;
        this.empty = true;
    }
}
