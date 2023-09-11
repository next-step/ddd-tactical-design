package kitchenpos.menus.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import kitchenpos.menus.tobe.domain.Menu.DisplayStatus;
import kitchenpos.products.tobe.domain.FakePurgomalumClient;
import kitchenpos.products.tobe.domain.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MenuTest {

    private PurgomalumClient purgomalumClient;
    private MenuGroup menuGroup;

    @BeforeEach
    public void init() {
        this.purgomalumClient = new FakePurgomalumClient();
        this.menuGroup = new MenuGroup("menuGroup");
    }

    @DisplayName("메뉴를 생성할수 있다")
    @Test
    void test1() {
        //given
        String menuName = "name";
        BigDecimal menuPrice = BigDecimal.TEN;

        //when
        Menu menu = new Menu(menuName, menuPrice, menuGroup, purgomalumClient);

        //then
        assertAll(
            () -> assertThat(menu.getId()).isNotNull(),
            () -> assertThat(menu.getStatus()).isEqualTo(DisplayStatus.DISPLAY),
            () -> assertThat(menu.getName()).isEqualTo(menuName),
            () -> assertThat(menu.getPrice()).isEqualTo(menuPrice),
            () -> assertThat(menu.getMenuProducts()).asList().isEmpty()
        );
    }

    @DisplayName("메뉴의 가격은 음수가 될수 없다")
    @ParameterizedTest
    @ValueSource(longs = {-1, -100, -1_000, -10_000, -100_000, -1_000_000})
    void test2(long price) {
        //given
        String menuName = "name";
        BigDecimal menuPrice = BigDecimal.valueOf(price);

        //when && then
        assertThatThrownBy(
            () -> new Menu(menuName, menuPrice, menuGroup, purgomalumClient)
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("가격은 음수가 될수 없습니다");
    }

    @DisplayName("메뉴 이름은 비속어가 될수 없다")
    @ParameterizedTest
    @ValueSource(strings = {"비속어", "욕설"})
    void test2(String profanity) {
        //given
        BigDecimal menuPrice = BigDecimal.TEN;

        //when && then
        assertThatThrownBy(
            () -> new Menu(profanity, menuPrice, menuGroup, purgomalumClient)
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("메뉴명은 비속어가 될수 없습니다");
    }
}