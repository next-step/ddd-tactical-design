package kitchenpos.products.tobe.domain;

import org.apache.logging.log4j.util.Strings;

import kitchenpos.core.constant.ExceptionMessages;
import kitchenpos.core.specification.NameSpecification;
import kitchenpos.products.infra.ProfanityClient;

public class ProductNameSpecification implements NameSpecification {

	private final ProfanityClient profanityClient;

	public ProductNameSpecification(ProfanityClient profanityClient) {
		this.profanityClient = profanityClient;
	}

	@Override
	public boolean isSatisfiedBy(String name) {
		if (Strings.isEmpty(name)) {
			throw new IllegalArgumentException(ExceptionMessages.Product.NAME_IS_EMPTY);
		}

		if (profanityClient.containsProfanity(name)) {
			throw new IllegalArgumentException(ExceptionMessages.Product.NAME_IS_PROFANITY);
		}

		return true;
	}
}
