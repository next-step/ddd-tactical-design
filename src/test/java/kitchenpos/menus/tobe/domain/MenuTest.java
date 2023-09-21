package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.domain.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MenuTest {
    private final MenuPricePolicy menuPricePolicy = new MenuPricePolicy();

    @Test
    void 메뉴를_등록할_때_메뉴가격정책을_따른다() {
        assertDoesNotThrow(() ->
                new Menu(UUID.randomUUID(),
                        new DisplayedName("내일의 치킨", new FakePurgomalumClient()),
                        new Price(10000),
                        new MenuProducts(List.of(new MenuProduct(UUID.randomUUID(), new Quantity(1), new Price(10000)))),
                        menuPricePolicy
                )
        );
    }

    @Test
    void 메뉴를_등록할_때_메뉴가격정책을_따르지_못하면_예외가_발생한다() {
        assertThatThrownBy(() -> new Menu(UUID.randomUUID(),
                        new DisplayedName("내일의 치킨", new FakePurgomalumClient()),
                        new Price(10001),
                        new MenuProducts(List.of(new MenuProduct(UUID.randomUUID(), new Quantity(1), new Price(10000)))),
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
                new Price(15000),
                new MenuProducts(List.of(new MenuProduct(UUID.randomUUID(), new Quantity(1), new Price(20000)))),
                menuPricePolicy
        );

        menu.changePrice(new Price(20000), menuPricePolicy);

        assertThat(menu.getPrice()).isEqualTo(new Price(20000));
    }

    @Test
    void 가격을_변경할_때_메뉴가격정책을_따르지_못하면_예외가_발생한다() {
        Menu menu = new Menu(UUID.randomUUID(),
                new DisplayedName("내일의 치킨", new FakePurgomalumClient()),
                new Price(15000),
                new MenuProducts(List.of(new MenuProduct(UUID.randomUUID(), new Quantity(1), new Price(20000)))),
                menuPricePolicy
        );

        assertThatThrownBy(() -> menu.changePrice(new Price(20001), menuPricePolicy))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴의 가격은 메뉴에 속한 상품들의 가격 총합보다 작거나 같아야 합니다. 현재 값: 메뉴가격 20001, 메뉴에 속한 상품들 가격 총합 20000");
    }

    @Test
    void 메뉴를_노출할_수_있다() {
        Menu menu = new Menu(UUID.randomUUID(),
                new DisplayedName("내일의 치킨", new FakePurgomalumClient()),
                new Price(10000),
                new MenuProducts(List.of(new MenuProduct(UUID.randomUUID(), new Quantity(1), new Price(10000)))),
                menuPricePolicy
        );

        menu.display();

        assertThat(menu.isDisplayed()).isTrue();
    }

    @Test
    void 메뉴를_숨길_수_있다() {
        Menu menu = new Menu(UUID.randomUUID(),
                new DisplayedName("내일의 치킨", new FakePurgomalumClient()),
                new Price(10000),
                new MenuProducts(List.of(new MenuProduct(UUID.randomUUID(), new Quantity(1), new Price(10000)))),
                menuPricePolicy
        );

        menu.hide();

        assertThat(menu.isDisplayed()).isFalse();
    }
}