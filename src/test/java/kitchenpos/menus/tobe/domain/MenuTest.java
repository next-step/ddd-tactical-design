package kitchenpos.menus.tobe.domain;

import kitchenpos.common.vo.Price;
import kitchenpos.menus.application.tobe.exception.InvalidMenuPriceException;
import kitchenpos.products.tobe.domain.ProductId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuTest {

    @DisplayName("메뉴를 생성한다")
    @Test
    void create() {
        MenuProducts menuProducts = new MenuProducts(
                new MenuProduct(ProductId.generate(), 1, 25_000),
                new MenuProduct(ProductId.generate(), 1, 2_000)
        );
        MenuId id = MenuId.generate();
        Menu menu = new Menu(
                id,
                new MenuName("후라이드치킨", (menuName) -> false),
                new Price(25_000),
                createMenuGroup(MenuGroupId.generate(), "치킨"),
                menuProducts,
                true
        );

        assertThat(menu.getId()).isEqualTo(id);
    }

    @DisplayName("메뉴 상품의 가격의 총 합보다 메뉴의 가격이 더 크면 예외가 발생한다")
    @ValueSource(longs = {28_000})
    @ParameterizedTest
    void isPriceInvalid(long menuPrice) {
        MenuProducts menuProducts = new MenuProducts(
                new MenuProduct(ProductId.generate(), 1, 25_000),
                new MenuProduct(ProductId.generate(), 1, 2_000)
        );

        assertThatThrownBy(() ->
                new Menu(
                        MenuId.generate(),
                        new MenuName("후라이드치킨", (menuName) -> false),
                        new Price(menuPrice),
                        createMenuGroup(MenuGroupId.generate(), "치킨"),
                        menuProducts,
                        true
                )
        ).isInstanceOf(InvalidMenuPriceException.class);
    }
    
    @DisplayName("메뉴의 가격을 변경한다")
    @Test
    void changePrice(){
        MenuProducts menuProducts = new MenuProducts(
                new MenuProduct(ProductId.generate(), 1, 25_000),
                new MenuProduct(ProductId.generate(), 1, 2_000)
        );
        Menu menu = new Menu(
                MenuId.generate(),
                new MenuName("후라이드치킨", (menuName) -> false),
                new Price(25_000),
                createMenuGroup(MenuGroupId.generate(), "치킨"),
                menuProducts,
                true
        );

        menu.changePrice(new Price(24_000));

        assertThat(menu.getPrice()).isEqualTo(new Price(24_000));
    }
    
    @DisplayName("메뉴를 전시한다")
    @Test
    void show(){
        MenuProducts menuProducts = new MenuProducts(
                new MenuProduct(ProductId.generate(), 1, 25_000),
                new MenuProduct(ProductId.generate(), 1, 2_000)
        );
        Menu menu = new Menu(
                MenuId.generate(),
                new MenuName("후라이드치킨", (menuName) -> false),
                new Price(25_000),
                createMenuGroup(MenuGroupId.generate(), "치킨"),
                menuProducts,
                true
        );

        menu.show();

        assertThat(menu.isDisplayed()).isTrue();
    }
    
    @DisplayName("메뉴를 숨긴다")
    @Test
    void hide(){
        MenuProducts menuProducts = new MenuProducts(
                new MenuProduct(ProductId.generate(), 1, 25_000),
                new MenuProduct(ProductId.generate(), 1, 2_000)
        );
        Menu menu = new Menu(
                MenuId.generate(),
                new MenuName("후라이드치킨", (menuName) -> false),
                new Price(25_000),
                createMenuGroup(MenuGroupId.generate(), "치킨"),
                menuProducts,
                true
        );

        menu.hide();

        assertThat(menu.isDisplayed()).isFalse();
    }

    private MenuGroup createMenuGroup(MenuGroupId id, String name) {
        return new MenuGroup(id, new MenuGroupName(name));
    }
}
