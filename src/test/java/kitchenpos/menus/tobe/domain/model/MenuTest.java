package kitchenpos.menus.tobe.domain.model;

import kitchenpos.global.domain.vo.DisplayedName;
import kitchenpos.global.domain.vo.Price;
import kitchenpos.menus.helper.MenuFixtureFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuTest {

    private static final DisplayedName 미트파이_세트 = new DisplayedName("미트파이와 레몬에이드 세트", text -> false);

    @DisplayName("메뉴 (Menu)의 가격은 속한 메뉴 상품 (Menu Product)의 가격의 합보다 작거나 같은 가격을 가져야 한다.")
    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void create(boolean displayed) {

        Price 메뉴_가격 = new Price(BigDecimal.valueOf(4000L));
        assertThatThrownBy(() -> new Menu(
                MenuFixtureFactory.런치_세트_메뉴,
                미트파이_세트,
                메뉴_가격,
                displayed,
                Arrays.asList(
                        MenuFixtureFactory.레몬에이드_메뉴_상품_1000원_1개,
                        MenuFixtureFactory.미트파이_메뉴_상품_1500원_1개
                )
        )).isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("메뉴의 가격이 상품의 가격의 합보다 낮은 경우 메뉴(Menu)를 숨기(invisible)거나 노출(visible)할 수 있다. ")
    @ValueSource(booleans =  {true, false})
    @ParameterizedTest
    void isDisplayed(boolean displayed) {
        Menu menu = new Menu(
                MenuFixtureFactory.런치_세트_메뉴,
                미트파이_세트,
                new Price(BigDecimal.valueOf(2000L)),
                displayed,
                Arrays.asList(
                        MenuFixtureFactory.레몬에이드_메뉴_상품_1000원_1개,
                        MenuFixtureFactory.미트파이_메뉴_상품_1500원_1개
                )
        );
        assertThat(menu.isDisplayed()).isEqualTo(displayed);
    }

    @DisplayName("메뉴(Menu)를 숨길(invisible)수 있다.")
    @Test
    void hide() {
        //given
        Menu menu = new Menu(
                MenuFixtureFactory.런치_세트_메뉴,
                미트파이_세트,
                new Price(BigDecimal.valueOf(2000L)),
                true,
                Arrays.asList(
                        MenuFixtureFactory.레몬에이드_메뉴_상품_1000원_1개,
                        MenuFixtureFactory.미트파이_메뉴_상품_1500원_1개
                )
        );
        //when
        menu.hide();

        //then
        assertThat(menu.isDisplayed()).isFalse();
    }

    @DisplayName("메뉴(Menu)를 노출(visible)할수 있다.")
    @Test
    void show() {
        //given
        Menu menu = new Menu(
                MenuFixtureFactory.런치_세트_메뉴,
                미트파이_세트,
                new Price(BigDecimal.valueOf(2000L)),
                false,
                Arrays.asList(
                        MenuFixtureFactory.레몬에이드_메뉴_상품_1000원_1개,
                        MenuFixtureFactory.미트파이_메뉴_상품_1500원_1개
                )
        );
        //when
        menu.show();

        //then
        assertThat(menu.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴(Menu) 의 가격은 변경될 수 있다.")
    @Test
    void changePrice01() {
        //given
        Menu menu = new Menu(
                MenuFixtureFactory.런치_세트_메뉴,
                미트파이_세트,
                new Price(BigDecimal.valueOf(2000L)),
                true,
                Arrays.asList(
                        MenuFixtureFactory.레몬에이드_메뉴_상품_1000원_1개,
                        MenuFixtureFactory.미트파이_메뉴_상품_1500원_1개
                )
        );
        //when
        menu.changePrice(new Price(BigDecimal.valueOf(1500L)));

        //then
        assertThat(menu.getPrice()).isEqualTo(new Price(BigDecimal.valueOf(1500L)));
    }

    @DisplayName("메뉴(Menu)의 변경할 가격은 메뉴 상품 (Menu Product)의 가격의 합보다 작거나 같은 가격을 가져야 한다.")
    @Test
    void changePrice02() {
        //given

        Menu menu = new Menu(
                MenuFixtureFactory.런치_세트_메뉴,
                미트파이_세트,
                new Price(BigDecimal.valueOf(2000L)),
                true,
                Arrays.asList(
                        MenuFixtureFactory.레몬에이드_메뉴_상품_1000원_1개,
                        MenuFixtureFactory.미트파이_메뉴_상품_1500원_1개
                )
        );
        //when
        Price 변경할_가격 = new Price(BigDecimal.valueOf(4500L));
        assertThatThrownBy(() -> menu.changePrice(변경할_가격))
                .isInstanceOf(IllegalStateException.class);

    }
}
