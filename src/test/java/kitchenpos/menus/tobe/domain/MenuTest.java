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

    @BeforeEach
    void setUp() {
        menuGroupRepository = new InMemoryMenuGroupRepository();
        productRepository = new FakeInMemoryProductRepository();
    }

    @DisplayName("Menu를 생성한다.")
    @Test
    void create() {
        //given
        MenuRequest menuRequest = MenuFixture.menu();
        menuGroupRepository.save(menuRequest.getMenuGroup());
        MenuCreateValidator validator = new MenuCreateValidator(menuGroupRepository, productRepository);

        //when
        Menu menu = new Menu(
                menuRequest.getId(),
                menuRequest.getName(),
                menuRequest.getPrice(),
                menuRequest.isDisplayed(),
                menuRequest.getMenuGroup(),
                menuRequest.getMenuProducts(),
                validator
        );

        //then
        assertAll(
                () -> assertThat(menu).isNotNull(),
                () -> assertThat(menu.getId()).isEqualTo(menuRequest.getId()),
                () -> assertThat(menu.getName()).isEqualTo(menuRequest.getName()),
                () -> assertThat(menu.getPrice()).isEqualTo(menuRequest.getPrice()),
                () -> assertThat(menu.getMenuGroup()).isEqualTo(menuRequest.getMenuGroup()),
                () -> assertThat(menu.getMenuProducts()).isEqualTo(menuRequest.getMenuProducts())
        );
    }

    @DisplayName("Menu 생성 실패 - 존재하지 않는 상품은 메뉴에 등록할 수 없다.")
    @Test
    void create_fail_non_existent_Product() {
        //given
        MenuRequest menuRequest = MenuFixture.menu();
        menuGroupRepository.save(menuRequest.getMenuGroup());
        MenuProduct menuProduct = MenuProductFixture.menuProduct_with_non_existent_product();
        MenuProducts menuProducts = new MenuProducts(Arrays.asList(menuProduct));
        menuRequest.setMenuProducts(menuProducts);
        MenuCreateValidator validator = new MenuCreateValidator(menuGroupRepository, productRepository);

        //when then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() ->
                        new Menu(
                                menuRequest.getId(),
                                menuRequest.getName(),
                                menuRequest.getPrice(),
                                menuRequest.isDisplayed(),
                                menuRequest.getMenuGroup(),
                                menuRequest.getMenuProducts(),
                                validator
                        ));
    }

    @DisplayName("Menu 생성 실패 - 존재하지 않는 메뉴 그룹에 메뉴를 추가할 수 없다.")
    @Test
    void create_fail_non_existent_MenuGroup() {
        //given
        MenuRequest menuRequest = MenuFixture.menu();
        MenuCreateValidator validator = new MenuCreateValidator(menuGroupRepository, productRepository);

        //when then
        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() ->
                        new Menu(
                                menuRequest.getId(),
                                menuRequest.getName(),
                                menuRequest.getPrice(),
                                menuRequest.isDisplayed(),
                                menuRequest.getMenuGroup(),
                                menuRequest.getMenuProducts(),
                                validator
                        ));
    }

    @DisplayName("Menu 생성 실패 - 메뉴의 가격은 메뉴에 속한 상품 금액의 합보다 작거나 같아야 한다.")
    @Test
    void create_fail_invalid_price() {
        //given
        MenuRequest menuRequest = MenuFixture.menu();
        menuGroupRepository.save(menuRequest.getMenuGroup());
        MenuCreateValidator validator = new MenuCreateValidator(menuGroupRepository, productRepository);
        Price totalMenuProductsPrice = menuRequest.getMenuProducts().getTotalMenuProductsPrice();
        menuRequest.setPrice(totalMenuProductsPrice.add(new Price(1000)));

        //when then
        assertThatExceptionOfType(WrongPriceException.class)
                .isThrownBy(() ->
                        new Menu(
                                menuRequest.getId(),
                                menuRequest.getName(),
                                menuRequest.getPrice(),
                                menuRequest.isDisplayed(),
                                menuRequest.getMenuGroup(),
                                menuRequest.getMenuProducts(),
                                validator
                        ))
                .withMessage(PRICE_SHOULD_NOT_BE_MORE_THAN_TOTAL_PRODUCTS_PRICE);
    }
}
