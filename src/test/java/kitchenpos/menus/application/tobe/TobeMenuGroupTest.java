package kitchenpos.menus.application.tobe;

import kitchenpos.menus.tobe.domain.MenuGroupName;
import kitchenpos.menus.tobe.domain.TobeMenuGroup;
import kitchenpos.tobeinfra.TobeFakePurgomalumClient;
import kitchenpos.tobeinfra.TobePurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class TobeMenuGroupTest {
    private TobePurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        this.purgomalumClient = new TobeFakePurgomalumClient();
    }

    @DisplayName("메뉴 그룹 생성")
    @Test
    void create() {
        TobeMenuGroup tobeMenuGroup = new TobeMenuGroup(new MenuGroupName("양식", purgomalumClient));

        assertThat(tobeMenuGroup).isNotNull();
    }
}




