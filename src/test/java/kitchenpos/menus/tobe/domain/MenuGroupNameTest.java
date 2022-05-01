package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuGroupNameTest {

    @DisplayName("정상적으로 메뉴그룹을 생성해보자")
    @ParameterizedTest
    @ValueSource(strings = {"1인 메뉴", "세트 메뉴"})
    void createProduct(String name) {
        MenuGroupName menuGroupName = new MenuGroupName(name);

        assertAll(
                () -> assertThat(menuGroupName).isNotNull(),
                () -> assertThat(menuGroupName.getName()).isEqualTo(name)
        );
    }

    @DisplayName("메뉴그룹 이름에 Null 또는 공백은 불가능하다")
    @ParameterizedTest
    @NullAndEmptySource
    void invalidProductName(String name) {
        assertThatThrownBy(
                () -> new MenuGroupName(name)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
