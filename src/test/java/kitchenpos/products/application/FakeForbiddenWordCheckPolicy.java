package kitchenpos.products.application;

import kitchenpos.products.tobe.domain.ForbiddenWordCheckPolicy;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FakeForbiddenWordCheckPolicy implements ForbiddenWordCheckPolicy {

	private String forbiddenWord = "바보";
	
	private Set<String> forbiddenWords = new HashSet<>(Collections.singletonList(forbiddenWord));

	@Override
	public boolean hasForbiddenWord(String name) {
		return forbiddenWords.stream()
				.anyMatch(name::contains);
	}
	
	public String findAnyForbiddenWord() {
		return forbiddenWord;
	}
}
