package kitchenpos.common.domain.vo;

import kitchenpos.common.domain.vo.exception.InvalidNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NameTest {

    @DisplayName("메뉴그룹명을 생성한다.")
    @Test
    void valueOf() {
        final Name name = Name.valueOf("인기상품");

        assertThat(name.value()).isEqualTo("인기상품");
    }

    @ParameterizedTest(name = "상품명에는 비어있을 수 없습니다. name={0}")
    @NullAndEmptySource
    void invalid_name(final String name) {
        assertThatThrownBy(() -> Name.valueOf(name))
                .isInstanceOf(InvalidNameException.class);
    }
}
