package kitchenpos.common.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import kitchenpos.common.infra.FakePurgomalumClient;

public class DisplayedNameTest {
	private PurgomalumClient purgomalumClient = new FakePurgomalumClient();

	@ParameterizedTest
	@ValueSource(strings = {"강정치킨", "닭가슴살 샐러드"})
	void 표시된_이름은_비속어를_포함하지_않는다(String value) {
		// given & when
		DisplayedName displayedName = new DisplayedName(value, purgomalumClient);

		// then
		Assertions.assertThat(displayedName.getValue()).isEqualTo(value);
	}

	@ParameterizedTest
	@ValueSource(strings = {"비속어", "욕설"})
	void 표시된_이름은_비속어가_포함될_수_없다(String value) {
		// given & when & then
		Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
			.isThrownBy(() -> new DisplayedName(value, purgomalumClient));
	}

	@Test
	void 표시된_이름_동등성() {
		// given
		String value = "강정치킨";

		// when
		DisplayedName displayedName = new DisplayedName(value, purgomalumClient);

		// then
		Assertions.assertThat(displayedName).isEqualTo(new DisplayedName("강정치킨", purgomalumClient));
	}
}
