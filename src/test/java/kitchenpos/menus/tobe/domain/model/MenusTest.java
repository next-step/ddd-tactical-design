package kitchenpos.menus.tobe.domain.model;

import kitchenpos.commons.tobe.domain.model.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;

import static kitchenpos.menus.tobe.domain.fixture.MenuFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenusTest {

    @DisplayName("메뉴 목록을 생성한다.")
    @Test
    void 생성_성공() {
        final Menu menu = MENU_WITH_ID(UUID.randomUUID());

        final Menus menus = new Menus(Collections.singletonList(menu));

        assertAll(
                () -> assertThat(menus.getMenu(menu.getId()).isPresent()).isTrue(),
                () -> assertThat(menus.getMenu(menu.getId()).get()).isEqualTo(menu)
        );
    }

    @DisplayName("메뉴 목록은 크기를 비교한다.")
    @Test
    void 크기_비교_성공() {
        final List<Menu> menus = Arrays.asList(MENU_WITH_ID(UUID.randomUUID()), MENU_WITH_ID(UUID.randomUUID()));

        assertThat(new Menus(menus).isSizeEqual(menus.size())).isTrue();
    }

    @DisplayName("메뉴 목록은 숨겨진 메뉴가 있는지 확인한다.")
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void 숨겨진_메뉴_존재_여부_성공(final boolean expected) {
        final List<Menu> menus = Collections.singletonList(MENU_WITH_DISPLAYED(!expected));

        assertThat(new Menus(menus).existsMenuNotDisplay()).isEqualTo(expected);
    }


    @DisplayName("메뉴 목록은 메뉴 가격을 반환한다.")
    @ParameterizedTest
    @ValueSource(longs = {0L, 16_000L})
    void 메뉴_가격_반환_성공(final long expected) {
        final Menu menu = MENU_WITH_PRICE(expected);
        final Menus menus = new Menus(Collections.singletonList(menu));

        final Map<UUID, Price> menuPriceMap = menus.getPrices();

        assertThat(menuPriceMap.get(menu.getId())).isEqualTo(new Price(expected));
    }
}
