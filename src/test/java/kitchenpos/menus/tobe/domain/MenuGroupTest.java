package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

class MenuGroupTest {

    @Test
    @DisplayName("메뉴 그룹 객체 비교")
    void compareToMenuGroup() {
        // give
        MenuGroup expectedObject = new MenuGroup("치킨");
        MenuGroup actualObject = new MenuGroup("치킨");
        // when
        boolean same = actualObject.equals(expectedObject);
        // then
        assertThat(same).isTrue();
    }
}
