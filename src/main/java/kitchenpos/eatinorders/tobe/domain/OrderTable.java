package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Table(name = "order_table")
@Entity
public class OrderTable {
    private static final String EMPTY_TABLE_CANNOT_CHANGE_NUMBER_OF_GUESTS = "빈 테이블은 방문한 손님 수를 변경할 수 없습니다.";

    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private OrderTableName name;

    @Embedded
    private NumberOfGuests numberOfGuests;

    @Column(name = "empty", nullable = false)
    private boolean empty;

    @Embedded
    private EatInOrders eatInOrders;

    protected OrderTable() {
    }

    public OrderTable(String name) {
        this.id = UUID.randomUUID();
        this.name = new OrderTableName(name);
        this.numberOfGuests = new NumberOfGuests(0);
        this.empty = true;
        this.eatInOrders = new EatInOrders();
    }

    public UUID getId() {
        return id;
    }

    public OrderTableName getName() {
        return name;
    }

    public NumberOfGuests getNumberOfGuests() {
        return numberOfGuests;
    }

    public boolean isEmpty() {
        return empty;
    }

    public List<EatInOrder> getEatInOrders() {
        return eatInOrders.getEatInOrders();
    }

    public void sit() {
        this.empty = false;
    }

    public void changeNumberOfGuests(int numberOfGuests) {
        if (isEmpty()) {
            throw new IllegalStateException(EMPTY_TABLE_CANNOT_CHANGE_NUMBER_OF_GUESTS);
        }
        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
    }

    public void addEatInOrder(EatInOrder eatInOrder) {
        eatInOrders.add(eatInOrder);
    }

    public boolean checkAllOrdersCompleted() {
        return eatInOrders.checkAllCompleted();
    }

    public void clear() {
        this.empty = true;
        this.numberOfGuests = new NumberOfGuests(0);
    }
}
