package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.application.FakeInMemoryProductRepository;
import kitchenpos.menus.tobe.application.InMemoryMenuGroupRepository;
import kitchenpos.menus.tobe.domain.exception.WrongPriceException;
import kitchenpos.menus.tobe.domain.repository.MenuGroupRepository;
import kitchenpos.menus.tobe.fixture.MenuFixture;
import kitchenpos.menus.tobe.fixture.MenuProductFixture;
import kitchenpos.menus.tobe.fixture.MenuRequest;
import kitchenpos.products.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static kitchenpos.menus.tobe.domain.exception.WrongPriceException.PRICE_SHOULD_NOT_BE_MORE_THAN_TOTAL_PRODUCTS_PRICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuTest {
    private MenuGroupRepository menuGroupRepository;
    private ProductRepository productRepository;
    private MenuCreateValidator validator;

    @BeforeEach
    void setUp() {
        menuGroupRepository = new InMemoryMenuGroupRepository();
        productRepository = new FakeInMemoryProductRepository();
        validator = new MenuCreateValidator(menuGroupRepository, productRepository);
    }

    @DisplayName("Menu를 생성한다.")
    @Test
    void create() {
        //given
        MenuRequest menuRequest = MenuFixture.menuRequest();
        menuGroupRepository.save(menuRequest.getMenuGroup());

        //when
        Menu menu = MenuFixture.menu(menuRequest, validator);

        //then
        assertAll(
                () -> assertThat(menu).isNotNull(),
                () -> assertThat(menu.getId()).isEqualTo(menu.getId()),
                () -> assertThat(menu.getName()).isEqualTo(menu.getName()),
                () -> assertThat(menu.getPrice()).isEqualTo(menu.getPrice()),
                () -> assertThat(menu.getMenuGroup()).isEqualTo(menu.getMenuGroup()),
                () -> assertThat(menu.getMenuProducts()).isEqualTo(menu.getMenuProducts())
        );
    }

    @DisplayName("Menu 생성 실패 - 존재하지 않는 상품은 메뉴에 등록할 수 없다.")
    @Test
    void create_fail_non_existent_Product() {
        //given
        MenuRequest menuRequest = MenuFixture.menuRequest();
        menuGroupRepository.save(menuRequest.getMenuGroup());
        MenuProduct menuProduct = MenuProductFixture.menuProduct_with_non_existent_product();
        MenuProducts menuProducts = new MenuProducts(Arrays.asList(menuProduct));
        menuRequest.setMenuProducts(menuProducts);

        //when then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() ->
                        MenuFixture.menu(menuRequest, validator));
    }

    @DisplayName("Menu 생성 실패 - 존재하지 않는 메뉴 그룹에 메뉴를 추가할 수 없다.")
    @Test
    void create_fail_non_existent_MenuGroup() {
        //given
        MenuRequest menuRequest = MenuFixture.menuRequest();

        //when then
        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() ->
                        MenuFixture.menu(menuRequest, validator));
    }

    @DisplayName("Menu 생성 실패 - 메뉴의 가격은 메뉴에 속한 상품 금액의 합보다 작거나 같아야 한다.")
    @Test
    void create_fail_invalid_price() {
        //given
        MenuRequest menuRequest = MenuFixture.menuRequest();
        menuGroupRepository.save(menuRequest.getMenuGroup());
        Price totalMenuProductsPrice = menuRequest.getMenuProducts().getTotalMenuProductsPrice();
        menuRequest.setPrice(totalMenuProductsPrice.add(new Price(1000)));

        //when then
        assertThatExceptionOfType(WrongPriceException.class)
                .isThrownBy(() ->
                        MenuFixture.menu(menuRequest, validator))
                .withMessage(PRICE_SHOULD_NOT_BE_MORE_THAN_TOTAL_PRODUCTS_PRICE);
    }

    @DisplayName("메뉴 가격 변경")
    @Test
    void change_price() {
        //given
        MenuRequest menuRequest = MenuFixture.menuRequest();
        menuGroupRepository.save(menuRequest.getMenuGroup());
        Menu menu = MenuFixture.menu(menuRequest, validator);
        Price newPrice = menu.getPrice().subtract(new Price(1000L));

        //when
        menu.changePrice(newPrice);

        //then
        assertThat(menu.getPrice()).isEqualTo(newPrice);
    }

    @DisplayName("메뉴 가격 변경 실패 - 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우")
    @Test
    void change_price_fail() {
        //given
        MenuRequest menuRequest = MenuFixture.menuRequest();
        menuGroupRepository.save(menuRequest.getMenuGroup());
        Menu menu = MenuFixture.menu(menuRequest, validator);
        Price totalMenuProductsPrice = menu.getMenuProducts().getTotalMenuProductsPrice();
        Price newPrice = totalMenuProductsPrice.add(new Price(1000L));

        //when then
        assertThatExceptionOfType(WrongPriceException.class)
                .isThrownBy(() -> menu.changePrice(newPrice))
                .withMessage(PRICE_SHOULD_NOT_BE_MORE_THAN_TOTAL_PRODUCTS_PRICE);
    }

    @DisplayName("메뉴를 숨긴다.")
    @Test
    void hide() {
        //given
        MenuRequest menuRequest = MenuFixture.menuRequest();
        menuGroupRepository.save(menuRequest.getMenuGroup());
        Menu menu = MenuFixture.menu(menuRequest, validator);

        //when
        menu.hide();

        //then
        assertThat(menu.isDisplayed()).isFalse();
    }

    @DisplayName("메뉴를 노출한다.")
    @Test
    void dislay() {
        //given
        MenuRequest menuRequest = MenuFixture.menuRequest();
        menuGroupRepository.save(menuRequest.getMenuGroup());
        menuRequest.setDisplayed(false);
        Menu menu = MenuFixture.menu(menuRequest, validator);

        //when
        menu.display();

        //then
        assertThat(menu.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴를 노출 실패 - 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다.")
    @Test
    void dislay_fail_cause_invalid_price() {
        //given
        MenuRequest menuRequest = MenuFixture.menuRequest();
        menuGroupRepository.save(menuRequest.getMenuGroup());
        menuRequest.setDisplayed(false);
        Menu menu = MenuFixture.menu(menuRequest, validator);

        // 메뉴 가격 인하
        menu.getMenuProducts().getMenuProducts()
                .forEach(menuProduct -> menuProduct.changePrice(new Price(0)));

        //when then
        assertAll(
                () -> assertThatExceptionOfType(WrongPriceException.class)
                        .isThrownBy(() -> menu.display())
                        .withMessage(PRICE_SHOULD_NOT_BE_MORE_THAN_TOTAL_PRODUCTS_PRICE),
                () -> assertThat(menu.isDisplayed()).isFalse()
        );
    }
}
