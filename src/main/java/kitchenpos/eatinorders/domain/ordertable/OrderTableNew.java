package kitchenpos.eatinorders.domain.ordertable;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import kitchenpos.support.vo.Name;

@Table(name = "order_table_new")
@Entity
public class OrderTableNew {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private Name name;

    @Column(name = "number_of_guests", nullable = false)
    private int numberOfGuests;

    @Column(name = "occustatuspied", nullable = false)
    private Status status;

    protected OrderTableNew() {
    }

    public static OrderTableNew create(final Name name) {

        return new OrderTableNew(UUID.randomUUID(), name, 0, Status.EMPTY);
    }

    private OrderTableNew(final UUID id, final Name name, final int numberOfGuests,
        final Status status) {

        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.status = status;
    }

    public void sit(final int numberOfGuests) {
        status = Status.NOT_EMPTY;
        changeNumberOfGuests(numberOfGuests);
    }

    public void changeNumberOfGuests(final int numberOfGuests) {
        if (status != Status.NOT_EMPTY) {
            throw new IllegalStateException("");
        }

        if (numberOfGuests < 0) {
            throw new IllegalArgumentException("");
        }

        this.numberOfGuests = numberOfGuests;
    }

    public void clear() {
        status = Status.EMPTY;
        numberOfGuests = 0;
    }

    public UUID getId() {
        return id;
    }
}
