package kitchenpos.menus.application.tobe;

import kitchenpos.menus.tobe.domain.MenuName;
import kitchenpos.tobeinfra.TobeFakePurgomalumClient;
import kitchenpos.tobeinfra.TobePurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TobeMenuNameTest {
    private TobePurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        purgomalumClient = new TobeFakePurgomalumClient();
    }

    @DisplayName("비속어 확인")
    @Test
    void purgomalum() {
        assertThatThrownBy(
            () -> new MenuName("욕설", purgomalumClient)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈값 확인")
    @NullSource
    @ParameterizedTest
    void nullOfEmpty(final String name) {
        assertThatThrownBy(
                () -> new MenuName(name, purgomalumClient)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("생성")
    @Test
    void create() {
        MenuName menuName = new MenuName("한식", purgomalumClient);

        assertThat(menuName).isNotNull();
    }

    @DisplayName("동등성 확인")
    @Test
    void equalName() {
        MenuName menuName1 = new MenuName("한식", purgomalumClient);
        MenuName menuName2 = new MenuName("한식", purgomalumClient);

        assertThat(menuName1).isEqualTo(menuName2);
    }
}
