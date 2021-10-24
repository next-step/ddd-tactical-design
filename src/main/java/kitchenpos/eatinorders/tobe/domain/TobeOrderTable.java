package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.infra.OrderAdaptor;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "order_table")
@Entity
public class TobeOrderTable {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private TableName name;

    @Embedded
    private TableNumberOfGuests numberOfGuests;

    @Column(name = "empty", nullable = false)
    private boolean empty;

    protected TobeOrderTable() {
    }

    public TobeOrderTable(TableName tableName) {
        this(tableName, new TableNumberOfGuests(0));
    }

    public TobeOrderTable(TableName tableName, TableNumberOfGuests tableNumberOfGuests) {
        this.name = tableName;
        this.id = UUID.randomUUID();
        this.numberOfGuests = tableNumberOfGuests;
        this.empty = true;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public int getNumberOfGuests() {
        return numberOfGuests.getNumberOfGuests();
    }

    public boolean isEmpty() {
        return empty;
    }

    public void changeNumberOfGuests(int numberOfGuests) {
        if (empty) {
            throw new IllegalStateException();
        }
        this.numberOfGuests.changeNumberOfGuests(numberOfGuests);
    };

    public void setTheTable() {
        this.empty = false;
    }

    public void clear(OrderAdaptor orderAdaptor) {
        validationOrderCompleteStatus(orderAdaptor);
        this.empty = true;
        numberOfGuests.clear();
    }

    private void validationOrderCompleteStatus(OrderAdaptor orderAdaptor) {
        if (!orderAdaptor.isOrderComplete(this)) {
            throw new IllegalStateException("주문 완료가 아닙니다.");
        }
    }
}
