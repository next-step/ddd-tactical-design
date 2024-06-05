package kitchenpos.eatinorders.tobe.domain;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Table(name = "order_table")
@Entity
public class OrderTable {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private OrderTableName name;

    @Column(name = "number_of_guests", nullable = false)
    private int numberOfGuests;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    public OrderTable() {
    }

    public OrderTable(String name) {
        this(UUID.randomUUID(), name, 0, false);
    }

    public OrderTable(UUID id, String name, int numberOfGuests, boolean occupied) {
        checkNumberOfGuests(numberOfGuests);
        this.id = id;
        this.name = new OrderTableName(name);
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    private void checkNumberOfGuests(int numberOfGuests){
        if(numberOfGuests < 0){
            throw new IllegalArgumentException();
        }
    }

    public void changeNumberOfGuests(int numberOfGuests){
        checkNumberOfGuests(numberOfGuests);
        if(!this.occupied){
            throw new IllegalArgumentException();
        }
        this.numberOfGuests = numberOfGuests;
    }


    public void sit(){
        this.occupied = true;
    }

    public void clear(){
        this.occupied = false;
    }


    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }
    public int getNumberOfGuests() {
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
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
