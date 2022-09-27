package kitchenpos.products.application;

import java.util.List;

import kitchenpos.products.infra.ProfanityClient;

public class FakePurgomalumClient implements ProfanityClient {

	private static final List<String> PROFANITIES = List.of("비속어", "욕설");

	@Override
	public boolean containsProfanity(final String text) {
		return PROFANITIES.stream()
			.anyMatch(text::contains);
	}
}
