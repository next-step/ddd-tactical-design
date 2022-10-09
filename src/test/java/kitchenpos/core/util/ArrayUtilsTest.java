package kitchenpos.core.util;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArrayUtilsTest {

	@DisplayName("varargs -> ArrayList 형 변환 수행된다")
	@Test
	void asList() {
		// given
		String value = "1";
		String[] values = { "2", "3" };

		// when
		List<String> result = ArrayUtils.asList(value, values);

		// then
		assertThat(result).containsExactly("1", "2", "3");
	}
}