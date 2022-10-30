package kitchenpos.eatinorders.tobe.domain.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity(name = "TobeOrderTable")
@Table(name = "order_table")
public class OrderTable {

    @Id
    @Column(name = "id", columnDefinition = "binary(16)")
    private UUID id;
    @Column(name = "name", nullable = false)
    private String name;
    @Embedded
    private OrderTableStatus orderTableStatus;

    protected OrderTable() {
        this("");
    }

    public OrderTable(String name) {
        this.name = name;
        this.orderTableStatus = OrderTableStatus.ofEmpty();
    }

    public void changeNumberOfGuests(int numberOfGuests) {
        orderTableStatus = orderTableStatus.changeNumberOfGuests(numberOfGuests);
    }

    public void occupy() {
        orderTableStatus = orderTableStatus.occupy();
    }

    public void clear(TablePolicy policy) {
        policy.validateClearable(this);
        orderTableStatus = orderTableStatus.clear();
    }

    public UUID getId() {
        return id;
    }
}
