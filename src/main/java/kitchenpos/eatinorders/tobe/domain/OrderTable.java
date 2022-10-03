package kitchenpos.eatinorders.tobe.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "order_table_id",
            nullable = false,
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_orders_to_order_table")
    )
    private List<EatInOrder> orders = new ArrayList<>();

    @Column(name = "occupied", nullable = false)
    @ColumnDefault("0")
    private boolean occupied;

    protected OrderTable() {
        id = UUID.randomUUID();
        clear();
    }

    public OrderTable(NumberOfGuests numberOfGuests, boolean occupied) {
        this();
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public OrderTable(Name name, NumberOfGuests numberOfGuests, List<EatInOrder> orders,
            boolean occupied) {
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.orders = orders;
        this.occupied = occupied;
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

    public boolean isEmpty() {
        return numberOfGuests.equals(NumberOfGuests.zero())
                && occupied == false;
    }

    public void changeNumberOfGuests(NumberOfGuests numberOfGuests) {
        if (!isOccupied()) {
            throw new IllegalStateException();
        }
        this.numberOfGuests = numberOfGuests;
    }

    public boolean isCompletedAllOrders() {
        return orders.stream()
                .allMatch(EatInOrder::isCompleted);
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
