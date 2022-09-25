package kitchenpos.core.specification;

@FunctionalInterface
public interface Specification<T> {

	boolean isSatisfiedBy(T target);
}
