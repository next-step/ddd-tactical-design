package kitchenpos.menus.tobe;

import kitchenpos.products.tobe.domain.exception.InvalidMenuGroupNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MenuGroupTest {

    @DisplayName("메뉴 그룹 이름이 null 이거나 빈 문자열일 경우 메뉴 그룹을 생성할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest(name = "{index}. 메뉴 그룹 이름: `{0}`")
    void createWithInvalidName(final String name) {
        final UUID uuid = UUID.randomUUID();

        assertThatThrownBy(() -> new MenuGroup(uuid, name)
        ).isInstanceOf(InvalidMenuGroupNameException.class);
    }
}
