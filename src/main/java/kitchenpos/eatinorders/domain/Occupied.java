package kitchenpos.eatinorders.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Occupied {
  @Column(name = "occupied", nullable = false)
  private boolean occupied;

  protected Occupied() {}

  protected Occupied(boolean occupied) {
    this.occupied = occupied;
  }

  public boolean isOccupied() {
    return occupied;
  }
}
