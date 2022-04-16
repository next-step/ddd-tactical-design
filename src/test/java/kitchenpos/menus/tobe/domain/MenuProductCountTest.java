package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuProductCountTest {

	@DisplayName("메뉴상품 개수를 생성할 수 있다")
	@ValueSource(ints = {0, 10})
	@ParameterizedTest
	void create(int value) {
		// when
		MenuProductCount result = new MenuProductCount(value);

		// then
		assertThat(result).isNotNull();
	}

	@DisplayName("메뉴상품 개수가 음수인 경우 예외가 발생한다")
	@ValueSource(ints = {-1, -10})
	@ParameterizedTest
	void minusCount(int value) {
		// when then
		assertThatThrownBy(() -> new MenuProductCount(value))
				.isInstanceOf(IllegalArgumentException.class)
						.hasMessageContaining("메뉴에 속한 상품의 수량은 0 이상이어야 합니다");
	}
	
	
}
