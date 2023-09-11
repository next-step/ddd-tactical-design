package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.ui.dto.OrderTableRequest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Table(name = "order_table")
@Entity
public class OrderTable {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "number_of_guests", nullable = false)
    private int numberOfGuests;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    protected OrderTable() {
    }

    public static OrderTable of(OrderTableRequest request) {
        return new OrderTable(request.getName());
    }

    public OrderTable(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.numberOfGuests = 0;
        this.occupied = false;
    }

    public String getId() {
        return id.toString();
    }

    public String getName() {
        return name;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void sit() {
        this.occupied = true;
    }

    public void clear() {
        this.numberOfGuests = 0;
        this.occupied = false;
    }

    public void changeNumberOfGuest(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }
}
