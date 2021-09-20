package kitchenpos.menus.tobe.domain.menugroup;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

public class NameTest {
	@Test
	void 이름을_생성할_수_있다() {
		// given
		String value = "추천메뉴";

		// when
		Name name = new Name(value);

		// then
		assertThat(name.getValue()).isEqualTo(value);
	}

	@ParameterizedTest
	@NullAndEmptySource
	void 빈_값이일_경우_이름을_생성할_수_없다(String value) {
		// given & when & then
		assertThatThrownBy(
			() -> new Name(value)
		).isInstanceOf(IllegalArgumentException.class);

	}

	@Test
	void 동등성() {
		// given & when
		Name name1 = new Name("추천메뉴");
		Name name2 = new Name("추천메뉴");

		// then
		assertThat(name1).isEqualTo(name2);
	}
}
