package kitchenpos.menus.tobe.domain;

import kitchenpos.products.infra.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class MenuGroupTest {

    private final PurgomalumClient purgomalumClient = new FakePurgomalumClient();

    @ParameterizedTest
    @ValueSource(strings = { "욕설", "비속어" })
    @NullAndEmptySource
    @DisplayName("이름이 비어있거나 비속어를 사용하면 메뉴 그룹 생성 불가능")
    void createMenuGroupFail(String name) {
        assertThatIllegalArgumentException().isThrownBy(() -> MenuGroup.create(name, purgomalumClient));
    }

    @Test
    @DisplayName("메뉴 그룹 정상 생성")
    void createMenuGroupSuccess() {
        // given
        String expected = "이름";

        // when
        MenuGroup menuGroup = MenuGroup.create(expected, purgomalumClient);

        // then
        assertThat(menuGroup.getName()).isEqualTo(expected);
    }
}
