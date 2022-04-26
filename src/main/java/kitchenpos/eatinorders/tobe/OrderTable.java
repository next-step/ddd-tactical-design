package kitchenpos.eatinorders.tobe;

import java.util.UUID;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class OrderTable {

    @Id
    private UUID id;

    @Embedded
    private OrderTableName name;

    @Embedded
    private NumberOfGuests numberOfGuests;

    private boolean empty;

    protected OrderTable() {
    }

    public OrderTable(final OrderTableName name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.numberOfGuests = NumberOfGuests.init();
        this.empty = Boolean.TRUE;
    }

    public void sit() {
        if (!empty) {
            throw new IllegalArgumentException();
        }
        empty = Boolean.FALSE;
    }

    public NumberOfGuests getNumberOfGuests() {
        return numberOfGuests;
    }

    public boolean isEmpty() {
        return empty;
    }
}
