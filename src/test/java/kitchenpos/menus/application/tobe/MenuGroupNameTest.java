package kitchenpos.menus.application.tobe;

import kitchenpos.menus.tobe.domain.MenuGroupName;
import kitchenpos.tobeinfra.TobeFakePurgomalumClient;
import kitchenpos.tobeinfra.TobePurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuGroupNameTest {

    private TobePurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        this.purgomalumClient = new TobeFakePurgomalumClient();
    }

    @DisplayName("메뉴 그룹명 생성")
    @Test
    void create() {
        MenuGroupName menuGroupName = new MenuGroupName("메뉴그룹", purgomalumClient);

        assertThat(menuGroupName).isNotNull();
    }

    @DisplayName("비속어 확인")
    @Test
    void profanities() {
        assertThatThrownBy(() ->
                new MenuGroupName("비속어", purgomalumClient)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}




