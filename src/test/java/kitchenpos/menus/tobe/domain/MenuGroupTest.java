package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MenuGroupTest {

    @DisplayName("메뉴 그룹명 테스트")
    @Nested
    class TestMenuGroupName {
        @DisplayName("메뉴 그룹명은 비어있을 수 없다")
        @ParameterizedTest(name = "[{index}] productName={0}")
        @NullSource
        @ValueSource(strings = {""})
        void givenMenuGroupName_whenMenuGroupNameIsBlank_thenThrowException(final String menuGroupName) {
            assertThrows(IllegalArgumentException.class, () -> MenuGroupName.create(menuGroupName));
        }
    }

}
