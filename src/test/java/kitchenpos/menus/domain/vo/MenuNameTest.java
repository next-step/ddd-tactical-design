package kitchenpos.menus.domain.vo;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MenuNameTest {
    PurgomalumClient purgomalumClient = new FakePurgomalumClient();

    @DisplayName("[정상] 메뉴 이름이 같으면 같은 객체이다.")
    @Test
    void valueObject_equals() {
        MenuName menuName1 = new MenuName("메뉴", purgomalumClient);
        MenuName menuName2 = new MenuName("메뉴", purgomalumClient);
        assertThat(menuName1).isEqualTo(menuName2);
    }
}