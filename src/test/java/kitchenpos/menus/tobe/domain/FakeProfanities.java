package kitchenpos.menus.tobe.domain;

import java.util.Collections;

public class FakeProperties implements Properties {

	public static final String PROPERTY = "비속어";
	
	@Override
	public boolean contains(String word) {
		return Collections.singletonList(PROPERTY).contains(word);
	}
}
