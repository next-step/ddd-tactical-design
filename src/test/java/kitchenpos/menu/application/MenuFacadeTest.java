package kitchenpos.menu.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.global.exception.NotFoundException;
import kitchenpos.global.exception.ProfanityException;
import kitchenpos.global.infrastructure.external.FakeProfanityClient;
import kitchenpos.menu.application.dto.MenuRequest;
import kitchenpos.menu.application.dto.MenuRequest.UpdatePrice;
import kitchenpos.menu.application.dto.MenuResponse;
import kitchenpos.menu.application.facade.MenuFacade;
import kitchenpos.menu.domain.entity.Menu;
import kitchenpos.menu.domain.entity.MenuProduct;
import kitchenpos.menu.domain.exception.MenuPriceException;
import kitchenpos.menu.domain.exception.MenuPriceInvalidException;
import kitchenpos.menu.domain.exception.MenuProductQtyException;
import kitchenpos.menu.domain.exception.MenuStateInvalidException;
import kitchenpos.menu.domain.fixture.MenuFixture;
import kitchenpos.menu.domain.fixture.MenuGroupFixture;
import kitchenpos.menu.domain.fixture.MenuProductFixture;
import kitchenpos.menu.domain.model.MenuProductQty;
import kitchenpos.menu.domain.repository.InMemoryMenuRepository;
import kitchenpos.menu.domain.repository.MenuGroupRepository;
import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.menu.domain.service.DefaultMenuService;
import kitchenpos.menu.domain.service.FakeMenuPolicy;
import kitchenpos.menu.domain.service.MenuCommandService;
import kitchenpos.menu.domain.service.MenuPurgomalumClient;
import kitchenpos.menu.domain.service.MenuQueryService;
import kitchenpos.product.domain.entity.Product;
import kitchenpos.product.domain.fixture.ProductFixture;
import kitchenpos.product.domain.model.ProductId;
import kitchenpos.product.domain.model.ProductName;
import kitchenpos.product.domain.model.ProductPrice;
import kitchenpos.product.domain.repository.InMemoryProductRepository;
import kitchenpos.product.domain.repository.ProductRepository;
import kitchenpos.product.domain.service.ProductPurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MenuFacadeTest {

    private MenuFacade menuFacade;
    private MenuQueryService menuQueryService;
    private MenuCommandService menuCommandService;
    private ProductRepository productRepository;
    private MenuRepository menuRepository;

    @Mock
    private MenuGroupRepository menuGroupRepository;

    private final MenuPurgomalumClient purgomalumClient = new FakeProfanityClient(List.of("나쁜", "XXX"));

    private final ProductPurgomalumClient menuPurgomalumClient = new FakeProfanityClient(List.of("나쁜", "XXX"));

    private FakeMenuPolicy menuPolicy;

    @Mock
    private ProductContextProvider productContextProvider;

    private MenuRequest.Create createMenu;
    private MenuRequest.UpdatePrice updatePriceMenu;
    private Menu defaultMenu;

    private Product chicken;

    @BeforeEach
    void setUp() {
        menuRepository = new InMemoryMenuRepository();
        productRepository = new InMemoryProductRepository();
        menuPolicy = new FakeMenuPolicy(menuRepository);
        menuQueryService = new DefaultMenuService(menuRepository, menuGroupRepository, purgomalumClient, menuPolicy, productContextProvider);
        menuCommandService = new DefaultMenuService(menuRepository, menuGroupRepository, purgomalumClient, menuPolicy, productContextProvider);
        menuFacade = new MenuFacade(menuQueryService, menuCommandService);

        createMenu = MenuFixture.init().create();

        chicken = ProductFixture.init().toEntity();
        defaultMenu = MenuFixture.test(
            null,
            null,
            null,
            true,
            List.of(new MenuProductFixture(chicken.getProductId().get(), MenuProductQty.of(10)).toEntity())
        ).toEntity();

        productRepository.save(chicken);
        menuRepository.save(defaultMenu);
    }

    @Nested
    @DisplayName("메뉴 조회")
    class 메뉴_조회 {

        @Test
        @DisplayName("성공 : 특정 조건 없이 상품의 모든 목록을 조회할 수 있다.")
        void 메뉴목록_조회() {
            List<MenuResponse.GetMenu> result = menuFacade.findAll();

            assertAll(
                () -> assertThat(result).isNotEmpty(),
                () -> assertEquals(result.size(), 1)
            );
        }
    }

    @Nested
    @DisplayName("메뉴 등록")
    class 메뉴_등록 {

        @Test
        @DisplayName("성공")
        void 메뉴등록_성공() {

            mockCreateMenu();
            var result = menuFacade.create(createMenu);

            assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(result.name(), createMenu.name()),
                () -> assertEquals(result.price(), createMenu.price()),
                () -> assertEquals(result.displayed(), createMenu.displayed())
            );

        }

        @DisplayName("메뉴가격은 0원 이상이어야 한다.")
        @ParameterizedTest
        @ValueSource(ints = {-10000, 0, 10000})
        void 메뉴가격_허용범위_검사(final int price) {
            createMenu = MenuFixture.test(
                null,
                BigDecimal.valueOf(price),
                null,
                true,
                null
            ).create();

            if (price < 0) {
                assertThatExceptionOfType(MenuPriceException.class)
                    .isThrownBy(() -> menuFacade.create(createMenu))
                    .withMessage(ErrorCode.MENU_PRICE_NOT_ALLOWED.toString());
            }
        }

        @Test
        @DisplayName("메뉴 그룹에 속해 있어야 한다.")
        void 메뉴그룹_검사() {
            createMenu = MenuFixture.test(
                null,
                null,
                null,
                true,
                null
            ).create();

            assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> menuFacade.create(createMenu))
                .withMessage(ErrorCode.NOT_FOUND_MENU_GROUP.toString());
        }

        @DisplayName("메뉴명도 상품명처럼 비속어를 포함하면 안된다.")
        @ParameterizedTest
        @ValueSource(strings = {"나쁜", "XXX"})
        void 메뉴명_비속어_검사(final String name) {

            createMenu = MenuFixture.test(
                name,
                null,
                null,
                true,
                null
            ).create();

            assertThatExceptionOfType(ProfanityException.class)
                .isThrownBy(() -> menuFacade.create(createMenu))
                .withMessage(ErrorCode.MENU_NAME_PROFANITY_NOT_ALLOWED.toString());
        }

        @DisplayName("등록 메뉴가격이 구성 상품의 총 금액보다 크지 않아야 한다.")
        @ParameterizedTest
        @CsvSource({"100000, 100"})
        void 메뉴_구성상품_금액_비교검사(final int price1, final int price2) {
            createMenu = MenuFixture.test(
                null,
                BigDecimal.valueOf(price1),
                null,
                true,
                List.of(new MenuProductFixture(chicken.getProductId().get(), MenuProductQty.of(100)).toEntity())
            ).create();

            chicken = new Product(chicken.getProductId(), chicken.getName(), ProductPrice.of(BigDecimal.valueOf(price2)));

            productRepository.save(chicken);

            mockCreateMenu();
            menuPolicy.setExceptionStatus(isOver(BigDecimal.valueOf(price1)));

            assertThatExceptionOfType(MenuPriceInvalidException.class)
                .isThrownBy(() -> menuFacade.create(createMenu))
                .withMessage(ErrorCode.MENU_PRICE_OVER_TOTAL_PRODUCTS_NOT_ALLOWED.toString());
        }

        @Test
        @DisplayName("메뉴 상품 정보를 반드시 가진다.")
        void 메뉴구성상품_검사() {
            createMenu = MenuFixture.test(
                null,
                null,
                null,
                true,
                List.of(new MenuProduct(null, MenuProductQty.of(1)))
            ).create();
            mockFindByMenuGroup();

            assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> menuFacade.create(createMenu))
                .withMessage(ErrorCode.NOT_FOUND_ANY_PRODUCT.toString());
        }

        @DisplayName("메뉴 상품 정보에 속한 상품의 수량은 0개 이상이어야 한다.")
        @ParameterizedTest
        @ValueSource(ints = {-100, -10})
        void 메뉴구성상품_수량_검사(final int qty) {
            assertThatExceptionOfType(MenuProductQtyException.class)
                .isThrownBy(() -> {

                    createMenu = MenuFixture.test(
                        null,
                        null,
                        null,
                        true,
                        List.of(new MenuProductFixture(chicken.getProductId().get(), MenuProductQty.of(qty)).toEntity())
                    ).create();

                    mockCreateMenu();

                    menuFacade.create(createMenu);
                })
                .withMessage(ErrorCode.MENU_PRODUCT_QTY_NOT_ALLOWED.toString());
        }


    }

    @Nested
    @DisplayName("메뉴 노출")
    class 메뉴_노출 {

        @Test
        @DisplayName("성공")
        void 메뉴_노출_성공() {

            assertThatCode(() -> {
                menuFacade.display(defaultMenu.getMenuId().get());
            }).doesNotThrowAnyException();

        }

        @DisplayName("메뉴가격이 구성 상품 총 금액보다 크지 않아야 한다.")
        @ParameterizedTest
        @CsvSource({"100000, 100"})
        void 변경가격_비교_검사(final int price1, final int price2) {
            chicken = new ProductFixture(
                        UUID.randomUUID(),
                        null,
                        BigDecimal.valueOf(price2)
                    ).toEntity();

            productRepository.save(chicken);

            defaultMenu = MenuFixture.test(
                null,
                BigDecimal.valueOf(price1),
                null,
                true,
                List.of(new MenuProductFixture(chicken.getProductId().get(), MenuProductQty.of(100)).toEntity())
            ).toEntity();

            menuRepository.save(defaultMenu);

            menuPolicy.setExceptionStatus(isOver(BigDecimal.valueOf(price1)));

            assertThatExceptionOfType(MenuStateInvalidException.class)
                .isThrownBy(() -> menuFacade.display(defaultMenu.getMenuId().get()))
                .withMessage(ErrorCode.MENU_PRICE_OVER_TOTAL_PRODUCTS_NOT_ALLOWED.toString());
        }
    }

    @Nested
    @DisplayName("메뉴 숨김")
    class 메뉴_숨김 {

        @Test
        @DisplayName("성공 : 등록 메뉴를 숨긴다.")
        void 메뉴_숨김_성공() {

            menuFacade.hide(defaultMenu.getMenuId().get());

            assertThat(defaultMenu.isDisplayed()).isFalse();
        }
    }

    @Nested
    @DisplayName("메뉴 가격변경")
    class 메뉴_가격변경 {

        @ParameterizedTest
        @DisplayName("성공")
        @ValueSource(ints = {0, 1000, 10000})
        void 메뉴_가격변경_성공(final int price) {

            updatePriceMenu = new UpdatePrice(defaultMenu.getMenuId().get(), BigDecimal.valueOf(price));

            assertThatCode(() -> {
                menuFacade.changePrice(updatePriceMenu);
            }).doesNotThrowAnyException();
        }

        @DisplayName("변경가격이 0원 보다 작으면 안된다.")
        @ParameterizedTest
        @ValueSource(ints = {-10000, 0, 10000})
        void 변경가격_허용범위_검사(final int price) {

            updatePriceMenu = MenuFixture.test(
                null,
                BigDecimal.valueOf(price),
                null,
                true,
                null
            ).update();

            if (price < 0) {
                assertThatExceptionOfType(MenuPriceException.class)
                    .isThrownBy(() -> menuFacade.changePrice(updatePriceMenu))
                    .withMessage(ErrorCode.MENU_PRICE_NOT_ALLOWED.toString());
            }
        }

        @DisplayName("변경가격이 메뉴 구성 상품 총 금액보다 크지 않아야 한다.")
        @ParameterizedTest
        @ValueSource(ints = {250000, 300000})
        void 변경가격_비교_검사(final int price) {

            updatePriceMenu = new UpdatePrice(defaultMenu.getMenuId().get(), BigDecimal.valueOf(price));

            menuPolicy.setExceptionStatus(isOver(BigDecimal.valueOf(price)));

            assertThatExceptionOfType(MenuPriceInvalidException.class)
                .isThrownBy(() -> menuFacade.changePrice(updatePriceMenu))
                .withMessage(ErrorCode.MENU_PRICE_OVER_TOTAL_PRODUCTS_NOT_ALLOWED.toString());
        }
    }

    private void mockCreateMenu() {
        mockFindByMenuGroup();
        mockFindAllByProductContext();
    }

    private void mockFindByMenuGroup() {
        when(menuGroupRepository.findByMenuGroupId(Mockito.any()))
            .thenReturn(Optional.of(MenuGroupFixture.init().toEntity()));
    }

    private void mockFindAllByProductContext() {
        when(productContextProvider.findAllByProductIds(anyList()))
            .thenAnswer(invocation -> {
                List<ProductId> requestedProductIds = invocation.getArgument(0);
                return requestedProductIds.stream()
                    .map(id -> new Product(
                        id, ProductName.of("치킨", menuPurgomalumClient), ProductPrice.of(BigDecimal.TEN))) // UUID 일치하는 Product 생성
                    .toList();
            });
    }

    private boolean isOver(BigDecimal price) {
        return defaultMenu.getMenuProducts().menuProducts().stream()
            .map(mp -> {
                return chicken.getPrice().price().multiply(BigDecimal.valueOf(mp.getQuantity().quantity()));
            })
            .reduce(BigDecimal.ZERO, BigDecimal::add).compareTo(price) < 0;
    }

}
