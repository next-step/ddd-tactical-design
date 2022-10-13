package kitchenpos.eatinorders.tobe.domain.ordertable;

import kitchenpos.eatinorders.tobe.domain.vo.NumberOfGuests;
import kitchenpos.global.vo.DisplayedName;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "order_table")
@Entity
public class OrderTable {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private DisplayedName name;

    @Embedded
    private NumberOfGuests numberOfGuests;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    protected OrderTable() {}

    public OrderTable(DisplayedName name, NumberOfGuests numberOfGuests, boolean occupied) {
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public OrderTable(UUID id, DisplayedName name, NumberOfGuests numberOfGuests, boolean occupied) {
        this(name, numberOfGuests, occupied);
        this.id = id;
    }

    public void tableClear() {
        this.numberOfGuests = new NumberOfGuests(0);
        emptyTable();
    }

    public void chageNumberOfGuests(NumberOfGuests numberOfGuests) {
        if (!isOccupied()) {
            throw new IllegalStateException("빈 테이블은 손님수를 변경할 수 없습니다.");
        }
        this.numberOfGuests = numberOfGuests;
    }

    public void sit() {
        this.occupied = true;
    }

    public void emptyTable() {
        this.occupied = false;
    }

    public UUID getId() {
        return id;
    }

    public DisplayedName getName() {
        return name;
    }

    public NumberOfGuests getNumberOfGuests() {
        return numberOfGuests;
    }

    public boolean isOccupied() {
        return occupied;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderTable that = (OrderTable) o;

        if (occupied != that.occupied) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return numberOfGuests != null ? numberOfGuests.equals(that.numberOfGuests) : that.numberOfGuests == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (numberOfGuests != null ? numberOfGuests.hashCode() : 0);
        result = 31 * result + (occupied ? 1 : 0);
        return result;
    }
}
