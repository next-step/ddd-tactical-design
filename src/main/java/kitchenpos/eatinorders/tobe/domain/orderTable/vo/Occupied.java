package kitchenpos.eatinorders.tobe.domain.orderTable.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Occupied {

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    protected Occupied() {
    }

    public Occupied(final boolean occupied) {
        this.occupied = occupied;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Occupied occupied1)) return false;
        return occupied == occupied1.occupied;
    }

    @Override
    public int hashCode() {
        return Objects.hash(occupied);
    }

    public boolean isOccupied() {
        return occupied;
    }
}
