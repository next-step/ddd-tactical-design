package kitchenpos.eatinorders.tobe.domain.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import kitchenpos.eatinorders.tobe.domain.service.OrderTableDomainService;

@Table(name = "order_table")
@Entity
public class OrderTable {

    private static final int INIT_NUMBER_OF_GUEST = 0;
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    @Column(name = "name", nullable = false)
    private OrderTableName name;

    @Embedded
    @Column(name = "number_of_guests", nullable = false)
    private NumberOfGuests numberOfGuests;

    @Column(name = "empty", nullable = false)
    private boolean empty;

    public OrderTable() {
    }

    public OrderTable(String name) {
        this.id = UUID.randomUUID();
        this.name = new OrderTableName(name);
        this.numberOfGuests = new NumberOfGuests(INIT_NUMBER_OF_GUEST);
        this.empty = true;
    }

    public void clear(final OrderTableDomainService orderTableDomainService) {
        orderTableDomainService.validateOrderTableClear(id);
        this.numberOfGuests = new NumberOfGuests(INIT_NUMBER_OF_GUEST);
        this.empty = true;
    }

    public void sit() {
        this.empty = false;
    }

    public void changeNumberOfGuests(int numberOfGuests) {
        if (isEmpty()) {
            throw new IllegalStateException();
        }

        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public int getNumberOfGuests() {
        return numberOfGuests.getNumber();
    }

    public boolean isEmpty() {
        return empty;
    }

}
