package kitchenpos.menus.tobe.domain;

import java.util.Collections;

public class FakeProfanities implements Profanities {

	public static final String PROFANITY = "비속어";
	
	@Override
	public boolean contains(String word) {
		return Collections.singletonList(PROFANITY).contains(word);
	}
}
