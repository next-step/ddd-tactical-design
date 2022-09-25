package kitchenpos.core.specification;

@FunctionalInterface
public interface NameSpecification extends Specification<String> {

	boolean isSatisfiedBy(String name);
}
