package kitchenpos.eatinorders.tobe.domain;

@FunctionalInterface
public interface OrderValidator {

  void validate(Order order);
}
