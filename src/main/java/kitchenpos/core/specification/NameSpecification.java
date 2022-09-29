package kitchenpos.core.specification;

import org.apache.logging.log4j.util.Strings;

import kitchenpos.core.constant.ExceptionMessages;

@FunctionalInterface
public interface NameSpecification extends Specification<String> {

	default boolean isSatisfiedBy(String name) {
		if (Strings.isEmpty(name)) {
			throw new IllegalArgumentException(String.format(ExceptionMessages.EMPTY_NAME_TEMPLATE, domain()));
		}

		return true;
	}

	String domain();
}
