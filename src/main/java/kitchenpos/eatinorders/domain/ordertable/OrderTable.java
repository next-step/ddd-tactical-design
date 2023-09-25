package kitchenpos.eatinorders.domain.ordertable;

import kitchenpos.common.domain.vo.Name;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "order_table")
@Entity
public class OrderTable {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private Name name;

    @Embedded
    private GuestNumber numberOfGuests;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    protected OrderTable() {
    }

    public OrderTable(final Name name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.numberOfGuests = GuestNumber.of(0);
        this.occupied = false;
    }

    public OrderTable(final Name name, final GuestNumber numberOfGuests, final boolean occupied) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getName() {
        return name.getName();
    }

    public int getNumberOfGuests() {
        return numberOfGuests.getNumberOfGuests();
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(final boolean occupied) {
        this.occupied = occupied;
    }

    public void clear() {
        this.numberOfGuests = GuestNumber.of(0);
        this.occupied = false;
    }

    public void changeNumberOfGuests(GuestNumber numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }
}
