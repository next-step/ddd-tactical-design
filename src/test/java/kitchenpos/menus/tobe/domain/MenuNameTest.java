package kitchenpos.menus.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MenuNameTest {
    private PurgomalumClient purgomalumClient = new FakePurgomalumClient();

    @Test
    @DisplayName("성공")
    void success() {
        //given
        String name = "양념치킨두마리세트";

        //when
        MenuName menuName = new MenuName(name, purgomalumClient);

        //then
        assertThat(menuName.getValue()).isEqualTo(name);
    }

    @Test
    @DisplayName("비속어는 올 수 없다.")
    void canNotCreateHaveProfanity(){
        assertThatThrownBy(() -> new MenuName("비속어치킨세트", purgomalumClient))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

}
