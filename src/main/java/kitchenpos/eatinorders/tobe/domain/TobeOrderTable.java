package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.*;
import java.util.Objects;
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

    public void clear() {
        this.empty = true;
        numberOfGuests.clear();
    }
}
