package kitchenpos.eatinorders.tobe.domain;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import kitchenpos.eatinorders.tobe.domain.vo.NumberOfGuests;
import kitchenpos.global.vo.Name;
import org.hibernate.annotations.ColumnDefault;

@Table(name = "tb_order_table")
@Entity(name = "tb_order_table")
public class OrderTable {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private Name name;

    @Embedded
    private NumberOfGuests numberOfGuests;

    @Column(name = "occupied", nullable = false)
    @ColumnDefault("0")
    private boolean occupied;

    protected OrderTable() {
        id = UUID.randomUUID();
        clear();
    }

    public static OrderTable create(Name name) {
        OrderTable orderTable = new OrderTable();
        orderTable.name = name;
        return orderTable;
    }

    public void sit() {
        occupied = true;
    }

    public void clear() {
        numberOfGuests = NumberOfGuests.zero();
        occupied = false;
    }

    public void changeNumberOfGuests(NumberOfGuests numberOfGuests) {
        if (!isOccupied()) {
            throw new IllegalStateException();
        }
        this.numberOfGuests = numberOfGuests;
    }

    public UUID getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public NumberOfGuests getNumberOfGuests() {
        return numberOfGuests;
    }

    public boolean isOccupied() {
        return occupied;
    }
}
