package kitchenpos.eatinorders.domain.tobe;

public interface EatInOrderCreatePolicy {

    boolean isOccupiedOrderTable(OrderTableId orderTableId);

}
