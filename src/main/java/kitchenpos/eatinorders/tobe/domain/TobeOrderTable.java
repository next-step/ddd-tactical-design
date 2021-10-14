package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Table(name = "order_table")
@Entity
public class TobeOrderTable {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "number_of_guests", nullable = false)
    private int numberOfGuests;

    @Column(name = "empty", nullable = false)
    private boolean empty;

    protected TobeOrderTable() {
    }

    public TobeOrderTable(String name) {
        validationTableName(name);
        this.name = name;
        this.id = UUID.randomUUID();
        this.numberOfGuests = 0;
        this.empty = true;
    }

    private void validationTableName(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void changeNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    };

    public void setTheTable() {
        this.empty = false;
    }

    public void clear() {
        this.empty = true;
        this.numberOfGuests = 0;
    }
}
