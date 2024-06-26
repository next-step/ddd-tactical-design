package kitchenpos.menus.tobe.application;

import kitchenpos.infra.PurgomalumClient;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.menus.tobe.fixtures.FakeMenuGroupRepository;
import kitchenpos.menus.tobe.fixtures.FakeMenuRepository;
import kitchenpos.menus.tobe.fixtures.MenuStep;
import kitchenpos.products.tobe.Money;
import kitchenpos.products.tobe.Name;
import kitchenpos.products.tobe.application.DefaultProductPriceService;
import kitchenpos.products.tobe.fixtures.FakeProductRepository;
import kitchenpos.products.tobe.fixtures.ProductStep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    private MenuService menuService;
    private MenuRepository menuRepository;
    private MenuGroupRepository menuGroupRepository;
    private ProductPriceService productPriceService;
    private ProductStep productStep;
    private MenuStep menuStep;

    @BeforeEach
    void setUp() {

        menuRepository = new FakeMenuRepository();
        menuGroupRepository = new FakeMenuGroupRepository();
        menuStep = new MenuStep(menuRepository, menuGroupRepository);

        var productRepository = new FakeProductRepository();
        productStep = new ProductStep(productRepository);
        productPriceService = new DefaultProductPriceService(productRepository);

        var purgomalumClient = new PurgomalumClient() {
            @Override
            public boolean containsProfanity(String text) {
                return "비속어".equals(text);
            }
        };
        menuService = new MenuService(menuRepository, menuGroupRepository, productPriceService, purgomalumClient);
    }

    @DisplayName("메뉴를 등록할 때")
    @Nested
    class MenuCreateTest {

        @DisplayName("이름, 가격, 표시 여부를 갖는다")
        @Test
        void createMenuTest() {

            // given
            final var product = productStep.상품_생성(UUID.randomUUID(), new Name("닭강정"), Money.from(10_000L));

            final var menuGroup = menuStep.메뉴_그룹_생성();
            final var menuProduct = new MenuProduct(product.id(), 1);

            MenuCreateCommand creatCommand = new MenuCreateCommand("치킨", BigDecimal.valueOf(10_000), menuGroup.getId(), true, List.of(menuProduct));
            Menu menu = menuService.create(creatCommand);

            assertNotNull(menu.getName());
            assertThat(menu.getPrice()).isEqualTo(BigDecimal.valueOf(10_000));
            assertTrue(menu.isDisplayed());
        }


        @DisplayName("메뉴의 이름이 없으면 등록이 불가능 하다")
        @Test
        void menuNameWithBlankTest() {

            final var product = productStep.상품_생성();
            final var menuGroup = menuStep.메뉴_그룹_생성();
            final var menuProduct = new MenuProduct(product.id(), 1);

            assertThatThrownBy(() -> {
                final var menuCreateCommand = new MenuCreateCommand(null, product.price().value(), menuGroup.getId(), true, List.of(menuProduct));
                menuService.create(menuCreateCommand);
            }).isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("name은 필수 입력값입니다.");
        }

        @DisplayName("메뉴의 가격이 0보다 작으면 등록이 불가능하다")
        @Test
        void menuPriceWithZeroTest() {

            final var product = productStep.상품_생성();
            final var menuGroup = menuStep.메뉴_그룹_생성();
            final var menuProduct = new MenuProduct(product.id(), 1);


            assertThatThrownBy(() -> {
                final var menuCreateCommand = new MenuCreateCommand(null, BigDecimal.valueOf(-10_000), menuGroup.getId(), true, List.of(menuProduct));
                menuService.create(menuCreateCommand);
            }).isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("금액은 0보다 작을 수 없습니다.");
        }

        @DisplayName("메뉴그룹에 소속되지 않으면 등록이 불가능하다")
        @Test
        void menuGroupNullTest() {

            final var product = productStep.상품_생성();
            final var menuProduct = new MenuProduct(product.id(), 1);

            assertThatThrownBy(() -> {
                final var menuCreateCommand = new MenuCreateCommand(null, BigDecimal.valueOf(10_000), UUID.randomUUID(), true, List.of(menuProduct));
                menuService.create(menuCreateCommand);
            }).isInstanceOf(NoSuchElementException.class);
        }


        @DisplayName("메뉴 이름에 비속어가 포함되어 있으면 등록이 불가능하다")
        @Test
        void menuNameWithProfanityTest() {

            final var product = productStep.상품_생성();
            final var menuGroup = menuStep.메뉴_그룹_생성();
            final var menuProduct = new MenuProduct(product.id(), 1);

            assertThatThrownBy(() -> {
                final var menuCreateCommand = new MenuCreateCommand("비속어", product.price().value(), menuGroup.getId(), true, List.of(menuProduct));
                menuService.create(menuCreateCommand);
            }).isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("비속어가 포함되어 있습니다.");
        }

        @DisplayName("메뉴의 속한 상품의 가격이 상품 가격보다 높으면 등록이 불가능하다")
        @Test
        void menuPriceGreaterThanProductPriceTest() {

            final var product = productStep.상품_생성();
            final var menuGroup = menuStep.메뉴_그룹_생성();
            final var menuProduct = new MenuProduct(product.id(), 1);

            assertThatThrownBy(() -> {
                final var menuCreateCommand = new MenuCreateCommand("치킨", Money.from(Long.MAX_VALUE).value(), menuGroup.getId(), true, List.of(menuProduct));
                menuService.create(menuCreateCommand);
            }).isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("메뉴에 포함된 상품의 가격이 상품의 가격보다 높습니다.");
        }
    }

    @DisplayName("메뉴는 숨김 처리가 가능하다")
    @Test
    void menuHideTest() {

        // given
//        Menu menu = menuProductSteps.노출된_메뉴를_생성한다();

        // when
//        Menu hiddenMenu = menuService.hide(menu.getId());

        // then
//        assertFalse(hiddenMenu.isDisplayed());
    }

    @DisplayName("메뉴는 보이기 처리가 가능하다")
    @Test
    void menuDisplayTest() {

        // given
//        Menu menu = menuProductSteps.감추기된_메뉴를_생성한다();

        // when
//        Menu displayed = menuService.display(menu.getId());

        // then
//        assertTrue(displayed.isDisplayed());
    }


    @DisplayName("메뉴의 가격이 메뉴상품의 가격보다 높으면 보이기 처리가 불가능하다")
    @Test
    void menuDisplayFailTest() {

//        MenuGroup menuGroup = menuProductSteps.메뉴그룹_생성한다();

        // given
//        Product product = menuProductSteps.상품을_생성한다(new ProductBuilder().with("치킨", BigDecimal.valueOf(100)).build());
//        MenuProduct menuProduct1 = menuProductSteps.메뉴상품을_생성한다(product);
//        Menu menu = menuProductSteps.메뉴를_생성한다("치킨", 10_000, menuGroup, true, List.of(menuProduct1));

        // when
        // then
//        assertThatThrownBy(() -> menuService.display(menu.getId())).isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("메뉴의 가격을 변경할 때 메뉴상품의 가격보다 크면 변경이 불가능하다")
    @Test
    void changePriceTest() {


        // given
//        MenuGroup menuGroup = menuProductSteps.메뉴그룹_생성한다();
//        Product product = menuProductSteps.상품을_생성한다(new ProductBuilder().with("치킨", BigDecimal.valueOf(100)).build());
//        MenuProduct menuProduct1 = menuProductSteps.메뉴상품을_생성한다(product);

//        Menu menu = menuProductSteps.메뉴를_생성한다("치킨", 10_000, menuGroup, true, List.of(menuProduct1));

        // when
        // then
//        assertThatThrownBy(() -> menuService.changePrice(menu.getId(), menu)).isInstanceOf(IllegalArgumentException.class);
    }

}
