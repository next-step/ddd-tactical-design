package kitchenpos.eatinorders.tobe.domain;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "order_table")
@Entity
public class OrderTable {

    private static final int EMPTY_TABLE_GUESTS = 0;
    private static final boolean DEFAULT_TABLE_STATUS = true;

    @Id
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @GeneratedValue
    private UUID id;

    @Embedded
    private OrderTableName name;

    @Column(name = "number_of_guests", nullable = false)
    private int numberOfGuests;

    @Column(name = "empty", nullable = false)
    private boolean empty;

    protected OrderTable() { }

    private OrderTable(OrderTableName name, int numberOfGuests, boolean empty) {
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.empty = empty;
    }

    public static OrderTable create(OrderTableName name) {
        return new OrderTable(name, EMPTY_TABLE_GUESTS, DEFAULT_TABLE_STATUS);
    }

    public static OrderTable create(String name) {
        return new OrderTable(OrderTableName.create(name), EMPTY_TABLE_GUESTS, DEFAULT_TABLE_STATUS);
    }

    public String getName() {
        return name.value();
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public boolean isEmpty() {
        return empty;
    }
}
