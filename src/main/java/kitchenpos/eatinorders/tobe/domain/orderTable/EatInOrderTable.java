package kitchenpos.eatinorders.tobe.domain.orderTable;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kitchenpos.eatinorders.tobe.domain.orderTable.vo.NumberOfGuests;
import kitchenpos.eatinorders.tobe.domain.orderTable.vo.Occupied;
import kitchenpos.eatinorders.tobe.domain.orderTable.vo.TableName;

import java.util.UUID;

@Table(name = "order_table")
@Entity
public class EatInOrderTable {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private TableName name;

    @Embedded
    private NumberOfGuests numberOfGuests;

    private Occupied occupied;

    protected EatInOrderTable() {
    }

    public EatInOrderTable(final String name,
                           final int numberOfGuests,
                           final boolean occupied) {
        this.name = new TableName(name);
        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
        this.occupied = new Occupied(occupied);
    }

    public void changeNumberOfGuests(final int numberOfGuests) {
        if (!occupied.isOccupied()) {
            throw new IllegalArgumentException();
        }
        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
    }

    public int numberOfGuests() {
        return numberOfGuests.getNumberOfGuests();
    }
}
