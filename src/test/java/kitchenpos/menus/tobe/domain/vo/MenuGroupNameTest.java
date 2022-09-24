package kitchenpos.menus.tobe.domain.vo;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class MenuGroupNameTest {

    @DisplayName("상품 이름을 생성할 수 있다.")
    @Test
    void create() {
        MenuGroupName actual = new MenuGroupName("한마리메뉴");

        assertThat(actual).isEqualTo(new MenuGroupName("한마리메뉴"));
        assertThat(actual.hashCode() == new MenuGroupName("한마리메뉴").hashCode())
                .isTrue();
    }

    @DisplayName("상품 그룹 이름은 비어 있을 수 없다.")
    @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
    @NullAndEmptySource
    void error1(String name) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new MenuGroupName(name));
    }
}
