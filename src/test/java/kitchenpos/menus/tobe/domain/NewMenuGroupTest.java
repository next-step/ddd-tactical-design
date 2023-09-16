package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.UUID;

import static kitchenpos.menus.exception.MenuGroupExceptionMessage.NULL_EMPTY_NAME;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("메뉴 그룹 테스트")
class NewMenuGroupTest {

    @DisplayName("메뉴 그룹 생성 성공")
    @Test
    void create() {
        UUID id = UUID.randomUUID();
        NewMenuGroup newMenuGroup = NewMenuGroup.create(id, "후라이드메뉴그룹");
        assertThat(newMenuGroup).isEqualTo(NewMenuGroup.create(id, "후라이드메뉴그룹"));
    }

    @DisplayName("이름이 null 이거나 비어있으면 예외를 반환한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void name(String name) {
        UUID id = UUID.randomUUID();
        assertThatThrownBy( () -> NewMenuGroup.create(id, name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(NULL_EMPTY_NAME);
    }

}
