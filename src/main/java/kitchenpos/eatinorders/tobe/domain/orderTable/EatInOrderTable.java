package kitchenpos.eatinorders.tobe.domain.orderTable;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kitchenpos.eatinorders.tobe.domain.exception.InvalidOccupiedException;
import kitchenpos.eatinorders.tobe.domain.exception.UncompletedOrdersExistException;
import kitchenpos.eatinorders.tobe.domain.orderTable.vo.NumberOfGuests;
import kitchenpos.eatinorders.tobe.domain.orderTable.vo.Occupied;
import kitchenpos.eatinorders.tobe.domain.orderTable.vo.TableName;

import java.util.UUID;

@Table(name = "order_table")
@Entity
public class EatInOrderTable {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private TableName name;

    @Embedded
    private NumberOfGuests numberOfGuests;

    @Embedded
    private Occupied occupied;

    protected EatInOrderTable() {
    }

    public EatInOrderTable(final String name) {
        this.id = UUID.randomUUID();
        this.name = new TableName(name);
        this.numberOfGuests = new NumberOfGuests(0);
        this.occupied = new Occupied(false);
    }

    public EatInOrderTable(final String name,
                           final int numberOfGuests,
                           final boolean occupied) {
        this.id = UUID.randomUUID();
        this.name = new TableName(name);
        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
        this.occupied = new Occupied(occupied);
    }

    public void changeNumberOfGuests(final int numberOfGuests) {
        if (!occupied.isOccupied()) {
            throw new InvalidOccupiedException("손님 수를 변경하려면 테이블이 사용 중이어야 합니다.");
        }
        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
    }

    public int numberOfGuests() {
        return numberOfGuests.getNumberOfGuests();
    }

    public boolean occupied() {
        return occupied.isOccupied();
    }
    public void sit() {
        this.occupied = new Occupied(true);
    }

    public void clear(final OrderTableOrders orderTableOrders) {
        if (orderTableOrders.existByOrderTableId(id)) {
            throw new UncompletedOrdersExistException("완료되지 않은 주문이 존재하는 테이블은 비울 수 없습니다.");
        }
        this.numberOfGuests = new NumberOfGuests(0);
        this.occupied = new Occupied(false);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public int getNumberOfGuests() {
        return numberOfGuests.getNumberOfGuests();
    }

    public boolean getOccupied() {
        return occupied.isOccupied();
    }
}
