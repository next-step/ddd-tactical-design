package kitchenpos.menus.tobe.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import kitchenpos.menus.domain.DisplayedName;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.MenuPricePolicy;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuProducts;
import kitchenpos.menus.domain.Price;
import kitchenpos.menus.domain.Quantity;

class MenuTest {
    private final MenuGroup menuGroup = new MenuGroup(UUID.randomUUID(), "베스트 메뉴");
    private final MenuPricePolicy menuPricePolicy = new MenuPricePolicy();

    @Test
    void 메뉴를_등록할_때_메뉴가격정책을_따른다() {
        assertDoesNotThrow(() ->
                new Menu(UUID.randomUUID(),
                        new DisplayedName("내일의 치킨", new FakePurgomalumClient()),
                        new Price(10000L),
                        menuGroup,
                        new MenuProducts(List.of(new MenuProduct(UUID.randomUUID(), 1, 10000L))),
                        menuPricePolicy
                )
        );
    }

    @Test
    void 메뉴를_등록할_때_메뉴가격정책을_따르지_못하면_예외가_발생한다() {
        assertThatThrownBy(() -> new Menu(UUID.randomUUID(),
                        new DisplayedName("내일의 치킨", new FakePurgomalumClient()),
                        new Price(10001L),
                        menuGroup,
                        new MenuProducts(List.of(new MenuProduct(UUID.randomUUID(), 1, 10000L))),
                        menuPricePolicy
                )
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴의 가격은 메뉴에 속한 상품들의 가격 총합보다 작거나 같아야 합니다. 현재 값: 메뉴가격 10001, 메뉴에 속한 상품들 가격 총합 10000");
    }

    @Test
    void 가격을_변경할_때_메뉴가격정책을_따른다() {
        Menu menu = new Menu(UUID.randomUUID(),
                new DisplayedName("내일의 치킨", new FakePurgomalumClient()),
                new Price(15000L),
                menuGroup,
                new MenuProducts(List.of(new MenuProduct(UUID.randomUUID(), 1, 20000L))),
                menuPricePolicy
        );

        menu.changePrice(new Price(20000L), menuPricePolicy);

        assertThat(menu.getPrice()).isEqualTo(new Price(20000L));
    }

    @Test
    void 가격을_변경할_때_메뉴가격정책을_따르지_못하면_예외가_발생한다() {
        Menu menu = new Menu(UUID.randomUUID(),
                new DisplayedName("내일의 치킨", new FakePurgomalumClient()),
                new Price(15000L),
                menuGroup,
                new MenuProducts(List.of(new MenuProduct(UUID.randomUUID(), 1, 20000L))),
                menuPricePolicy
        );

        assertThatThrownBy(() -> menu.changePrice(new Price(20001L), menuPricePolicy))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴의 가격은 메뉴에 속한 상품들의 가격 총합보다 작거나 같아야 합니다. 현재 값: 메뉴가격 20001, 메뉴에 속한 상품들 가격 총합 20000");
    }

    @Test
    void 메뉴를_노출할_때_메뉴가격정책을_따른다() {
        Menu menu = new Menu(UUID.randomUUID(),
                new DisplayedName("내일의 치킨", new FakePurgomalumClient()),
                new Price(10000L),
                menuGroup,
                new MenuProducts(List.of(new MenuProduct(UUID.randomUUID(), 1, 10000L))),
                menuPricePolicy
        );

        menu.display(menuPricePolicy);

        assertThat(menu.isDisplayed()).isTrue();
    }

    @Test
    void 메뉴를_숨길_수_있다() {
        Menu menu = new Menu(UUID.randomUUID(),
                new DisplayedName("내일의 치킨", new FakePurgomalumClient()),
                new Price(10000L),
                menuGroup,
                new MenuProducts(List.of(new MenuProduct(UUID.randomUUID(), 1, 10000L))),
                menuPricePolicy
        );

        menu.hide();

        assertThat(menu.isDisplayed()).isFalse();
    }
}
