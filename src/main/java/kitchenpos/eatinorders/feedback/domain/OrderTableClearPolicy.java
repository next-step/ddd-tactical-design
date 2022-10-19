package kitchenpos.eatinorders.feedback.domain;

@FunctionalInterface
public interface OrderTableClearPolicy {
    boolean canClear(Long orderTableId);
}
