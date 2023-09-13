package kitchenpos.menus.tobe.domain;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import kitchenpos.menus.tobe.domain.Menu.DisplayStatus;
import kitchenpos.menus.tobe.domain.Menu.MenuProductRequest;
import kitchenpos.products.tobe.domain.FakePurgomalumClient;
import kitchenpos.products.tobe.domain.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
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
        BigDecimal menuPrice = BigDecimal.ZERO;

        //when
        Menu menu = new Menu(menuName, menuPrice, menuGroup, List.of(), purgomalumClient);

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
            () -> new Menu(menuName, menuPrice, menuGroup, List.of(), purgomalumClient)
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("가격은 음수가 될수 없습니다");
    }

    @DisplayName("메뉴 이름은 비속어가 될수 없다")
    @ParameterizedTest
    @ValueSource(strings = {"비속어", "욕설"})
    void test2(String profanity) {
        //given
        BigDecimal menuPrice = BigDecimal.ZERO;

        //when && then
        assertThatThrownBy(
            () -> new Menu(profanity, menuPrice, menuGroup, List.of(), purgomalumClient)
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("메뉴명은 비속어가 될수 없습니다");
    }

    @DisplayName("메뉴를 숨길수 있다")
    @Test
    void test3() {
        //given
        String menuName = "name";
        BigDecimal menuPrice = BigDecimal.ZERO;
        Menu menu = new Menu(menuName, menuPrice, menuGroup, List.of(), purgomalumClient);

        //when
        menu.hide();

        //then
        assertThat(menu.getStatus()).isEqualTo(DisplayStatus.HIDE);
    }

    @DisplayName("메뉴를 표시할수 있다")
    @Test
    void test4() {
        //given
        String menuName = "name";
        BigDecimal menuPrice = BigDecimal.ZERO;
        Menu menu = new Menu(menuName, menuPrice, menuGroup, List.of(), purgomalumClient);
        menu.hide();

        //when
        menu.display();

        //then
        assertThat(menu.getStatus()).isEqualTo(DisplayStatus.DISPLAY);
    }

    @DisplayName("구성품들의 가격을 초과한 메뉴를 생성할수 없다")
    @ParameterizedTest
    @MethodSource("test5MethodSource")
    void test5(List<MenuProductRequest> menuProductRequests) {
        assertThatThrownBy(
            () -> new Menu("치킨", BigDecimal.valueOf(36_000), menuGroup, menuProductRequests, purgomalumClient)
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("메뉴의 가격이 상품들의 가격보다 높습니다");
    }

    static Stream<List<MenuProductRequest>> test5MethodSource() {
        return Stream.of(
            of(new MenuProductRequest(UUID.randomUUID(), BigDecimal.valueOf(18_000), 1)),
            of(
                new MenuProductRequest(UUID.randomUUID(), BigDecimal.valueOf(16_000), 1),
                new MenuProductRequest(UUID.randomUUID(), BigDecimal.valueOf(17_000), 1)
            ),
            of(new MenuProductRequest(UUID.randomUUID(), BigDecimal.valueOf(17_000), 2))
        );
    }

    @DisplayName("메뉴 가격이 구성품들의 가격보다 크다면 메뉴를 숨길수 있다")
    @Test
    void test6() {
        //given
        List<MenuProductRequest> menuProductRequests = of(
            new MenuProductRequest(UUID.randomUUID(), BigDecimal.valueOf(17_000), 1),
            new MenuProductRequest(UUID.randomUUID(), BigDecimal.valueOf(18_000), 1)
        );
        Menu menu = new Menu("menu", BigDecimal.valueOf(17_000 + 18_000), menuGroup, menuProductRequests,
            purgomalumClient);

        //when
        MenuProduct menuProduct1 = menu.getMenuProducts().get(0);
        menuProduct1.updatePrice(BigDecimal.valueOf(16_000));
        menu.hideIfPriceIsInvalid();

        //then
        assertThat(menu.getStatus()).isEqualTo(DisplayStatus.HIDE);
    }
}