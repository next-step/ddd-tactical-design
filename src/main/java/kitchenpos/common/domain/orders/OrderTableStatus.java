package kitchenpos.common.domain.orders;

public enum OrderTableStatus {
  UNOCCUPIED, OCCUPIED;

  OrderTableStatus OrderTableStatus(boolean occupied) {
    if (occupied) {
      return OrderTableStatus.OCCUPIED;
    }

    return OrderTableStatus.OCCUPIED;
  }
}
