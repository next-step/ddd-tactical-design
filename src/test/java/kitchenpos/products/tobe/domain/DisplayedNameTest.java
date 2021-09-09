package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class DisplayedNameTest {
	@Test
	void 노출_이름을_생성할_수_있다() {
		// given
		String value = "치킨";

		// when
		DisplayedName displayedName = new DisplayedName(value, name -> false);

		// then
		assertThat(displayedName.getValue()).isEqualTo(value);
	}

	@ParameterizedTest
	@ValueSource(strings = {"비속어", "욕설"})
	@NullSource
	void 빈_값이거나_비속어일_경우_노출_이름을_생성할_수_없다(String value) {
		// given & when & then
		assertThatThrownBy(
			() -> new DisplayedName(value, name -> true)
		).isInstanceOf(IllegalArgumentException.class);

	}

	@Test
	void 동등성() {
		// given & when
		DisplayedName name1 = new DisplayedName("치킨", name -> false);
		DisplayedName name2 = new DisplayedName("치킨", name -> false);

		// then
		assertThat(name1).isEqualTo(name2);
	}
}
