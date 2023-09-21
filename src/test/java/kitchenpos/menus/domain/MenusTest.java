package kitchenpos.menus.domain;

import static kitchenpos.menus.domain.MenuFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kitchenpos.menus.domain.tobe.domain.ToBeMenu;
import kitchenpos.menus.domain.tobe.domain.ToBeMenus;

class MenusTest {
    private ToBeMenu 돈가스;
    private ToBeMenu 김밥;

    @BeforeEach
    void setUp() {
        돈가스 = createMenu("김밥세트"
            , 30_000L
            , UUID.randomUUID()
            , true
            , false
            , createMenuProduct(30_000L, 1));

        김밥 = createMenu("순대세트"
            , 10_000L
            , UUID.randomUUID()
            , true
            , false
            , createMenuProduct(10_000L, 1));
    }

    @DisplayName("메뉴 중 1개라도 숨김 메뉴가 있다.")
    @Test
    void hiddenMenu() {
        ToBeMenu 떡볶이 = createMenu("떡볶이세트"
            , 10_000L
            , UUID.randomUUID()
            , false
            , false
            , createMenuProduct(10_000L, 1));

        ToBeMenus menus = new ToBeMenus(List.of(돈가스, 김밥, 떡볶이));
        assertThat(menus.hasHiddenMenu()).isTrue();
    }

    @DisplayName("동일메뉴 and 동일 가격의 메뉴를 찾을 수 없다.")
    @Test
    void name2() {
        ToBeMenus menus = new ToBeMenus(List.of(돈가스, 김밥));
        assertThat(menus.isNotMatchByMenuAndPrice(UUID.randomUUID(), BigDecimal.valueOf(10_000)))
            .isTrue();
    }
}
