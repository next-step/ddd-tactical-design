package kitchenpos.menus.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuNameTest {

    @DisplayName("정상적으로 메뉴를 생성해보자")
    @ParameterizedTest
    @ValueSource(strings = {"1인 메뉴", "세트메뉴"})
    void createMenuName(String name) {
        PurgomalumClient purgomalumClient = new FakePurgomalumClient();
        MenuName menuName = new MenuName(purgomalumClient, name);

        assertAll(
                () -> assertThat(menuName).isNotNull(),
                () -> assertThat(menuName.getName()).isEqualTo(name)
        );
    }

    @DisplayName("메뉴이름에 null은 불가능하다")
    @Test
    void invalidMenuName() {
        PurgomalumClient purgomalumClient = new FakePurgomalumClient();

        assertThatThrownBy(
                () -> new MenuName(purgomalumClient, null)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴이름이 비속어가 될수 없다.")
    @ParameterizedTest
    @ValueSource(strings = {"비속어", "욕설"})
    void slangMenuName(String name) {
        PurgomalumClient purgomalumClient = new FakePurgomalumClient();

        assertThatThrownBy(
                () -> new MenuName(purgomalumClient, name)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
