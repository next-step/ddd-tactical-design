package kitchenpos.eatinorders.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kitchenpos.eatinorders.tobe.domain.constant.OccupiedStatus;

import java.util.NoSuchElementException;
import java.util.UUID;

@Table(name = "order_table")
@Entity
public class TobeOrderTable {
    @Id
    @Column(name = "id", columnDefinition = "binary(16)")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "number_of_guests", nullable = false)
    private int numberOfGuests;

    @Enumerated(EnumType.STRING)
    @Column(name = "occupied", nullable = false)
    private OccupiedStatus occupied;

    protected TobeOrderTable() {
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public OccupiedStatus isOccupied() {
        return occupied;
    }

    public void validAvailableTable() {
        if (occupied == OccupiedStatus.OCCUPIED_TABLE) {
            throw new NoSuchElementException("이미 사용중인 테이블입니다.");
        }
    }
}
