package kitchenpos.eatinorders.domain.tobe.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "order_table")
@Entity
public class ToBeOrderTable {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private OrderTableName name;

    @Embedded
    private EatInNumberOfGuest eatInNumberOfGuest;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    protected ToBeOrderTable() {
    }

    public ToBeOrderTable(String name) {
        this.id = UUID.randomUUID();
        this.name = OrderTableName.of(name);
        initOrderTable();
    }

    public void sit() {
        occupied = true;
    }

    public void emptyTable(boolean isExistsOrderTable) {
        if (isExistsOrderTable) {
            throw new IllegalStateException("완료되지 않은 주문이 있는 주문 테이블은 빈 테이블로 설정할 수 없다.");
        }
        initOrderTable();
    }

    public void changeNumberOfGuests(int number) {
        if (!occupied) {
            throw new IllegalStateException("빈 테이블이면 손님 수를 변경 할 수 없다.");
        }

        eatInNumberOfGuest = eatInNumberOfGuest.changeNumberOfGuest(number);
    }

    public boolean isOccupied() {
        return occupied;
    }

    public EatInNumberOfGuest getNumberOfGuest() {
        return eatInNumberOfGuest;
    }

    public void validationForEatInOrder() {
        if (!occupied) {
            throw new IllegalStateException("빈 테이블에는 매장 주문을 등록할 수 없다.");
        }
    }

    public void initOrderTable() {
        eatInNumberOfGuest = EatInNumberOfGuest.defaultNumber();
        occupied = false;
    }
}
