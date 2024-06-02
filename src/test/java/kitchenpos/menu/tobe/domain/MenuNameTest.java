package kitchenpos.menu.tobe.domain;

import kitchenpos.exception.IllegalNameException;
import kitchenpos.infra.FakePurgomalumClient;
import kitchenpos.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MenuNameTest {

    PurgomalumClient purgomalumClient;
    MenuNameFactory menuName;

    @BeforeEach
    void setUP() {
        purgomalumClient = new FakePurgomalumClient();
        menuName = new MenuNameFactory(purgomalumClient);
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("메뉴 이름은 필수로 입력해야한다.")
    void nameFail1(final String input) {

        assertThrows(IllegalNameException.class, () -> menuName.create(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"욕설", "욕설포함"})
    @DisplayName("메뉴 이름에 욕설이 포함되어있는 경우 등록할 수 없다.")
    void nameFail2(final String input) {

        assertThrows(IllegalArgumentException.class, () -> menuName.create(input));
    }
}
