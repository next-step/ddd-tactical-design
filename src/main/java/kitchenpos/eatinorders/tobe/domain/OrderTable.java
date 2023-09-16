package kitchenpos.eatinorders.tobe.domain;

import static javax.persistence.EnumType.STRING;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class OrderTable {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private OrderTableName name = OrderTableName.INIT;

    @Embedded
    private NumberOfGuest numberOfGuest = NumberOfGuest.INIT;

    @Enumerated(STRING)
    private OrderTableStatus status;

    public OrderTable(OrderTableName name) {
        if (isEmpty(name)) {
            throw new IllegalArgumentException("주문테이블은 이름을 가지고 있어야 합니다");
        }

        this.id = UUID.randomUUID();
        this.name = name;
        this.numberOfGuest = NumberOfGuest.INIT;
        this.status = OrderTableStatus.VACANT;
    }

    protected OrderTable() {
    }

    public void occupy() {
        this.status = OrderTableStatus.OCCUPIED;
    }

    public boolean isOccupied() {
        return this.status == OrderTableStatus.OCCUPIED;
    }

    public boolean isVacant() {
        return !isOccupied();
    }

    public UUID getId() {
        return id;
    }

    public OrderTableName getName() {
        return name;
    }

    public NumberOfGuest getNumberOfGuest() {
        return numberOfGuest;
    }

    public OrderTableStatus getStatus() {
        return status;
    }

    public enum OrderTableStatus {
        OCCUPIED, VACANT
    }

}
