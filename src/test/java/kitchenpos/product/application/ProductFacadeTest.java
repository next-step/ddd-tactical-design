package kitchenpos.product.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.global.exception.ProfanityException;
import kitchenpos.global.infrastructure.external.FakeProfanityClient;
import kitchenpos.menu.domain.entity.Menu;
import kitchenpos.menu.domain.fixture.MenuFixture;
import kitchenpos.menu.domain.fixture.MenuProductFixture;
import kitchenpos.menu.domain.model.MenuProductQty;
import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.menu.domain.service.FakeMenuPolicy;
import kitchenpos.product.application.dto.ProductRequest;
import kitchenpos.product.application.dto.ProductRequest.UpdatePrice;
import kitchenpos.product.application.dto.ProductResponse;
import kitchenpos.product.application.facade.ProductFacade;
import kitchenpos.product.domain.event.ProductEventPublisher;
import kitchenpos.product.domain.exception.ProductPriceException;
import kitchenpos.product.domain.fixture.ProductFixture;
import kitchenpos.product.domain.repository.InMemoryMenuRepository;
import kitchenpos.product.domain.repository.InMemoryProductRepository;
import kitchenpos.product.domain.repository.ProductRepository;
import kitchenpos.menu.domain.service.ProductContextService;
import kitchenpos.product.domain.service.ProductPurgomalumClient;
import kitchenpos.product.domain.service.ProductService;
import kitchenpos.product.domain.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductFacadeTest {

    @InjectMocks
    private ProductFacade productFacade;

    @Mock
    private ProductService productService;

    @Mock
    private ProductEventPublisher productEventPublisher;

    @Mock
    private ProductContextService productContextService;

    private FakeMenuPolicy menuPolicy;

    private ProductRepository productRepository;

    private MenuRepository menuRepository;

    private final ProductPurgomalumClient purgomalumClient = new FakeProfanityClient(List.of("나쁜", "XXX"));

    private ProductRequest.Create chicken;
    private ProductRequest.UpdatePrice updateChicken;
    private Menu chickenMenu;

    @BeforeEach
    void setUp() {
        menuRepository = new InMemoryMenuRepository();
        productRepository = new InMemoryProductRepository();
        menuPolicy = new FakeMenuPolicy(menuRepository);

        productService = new ProductServiceImpl(productRepository, purgomalumClient, productEventPublisher);
        productFacade = new ProductFacade(productService);

        chicken = ProductFixture.init().create();
        updateChicken = ProductFixture.init().update();
        chickenMenu = MenuFixture.init().toEntity();
    }

    @Nested
    @DisplayName("상품 등록")
    class 상품_등록 {

        @Test
        @DisplayName("성공")
        void 상품등록_성공() {

            var result = productService.create(chicken.toVo());

            assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(result.name().name(), chicken.name()),
                () -> assertEquals(result.price().price(), chicken.price()),
                () -> assertThatCode(() -> {
                    productService.create(chicken.toVo());
                }).doesNotThrowAnyException()
            );

        }

        @DisplayName("상품명을 반드시 가지며 비속어를 포함하면 안된다.")
        @ParameterizedTest
        @ValueSource(strings = {"나쁜", "XXX"})
        void 상품명_비속어_검사(final String name) {
            chicken = ProductFixture.test(name, null).create();

            assertThatExceptionOfType(ProfanityException.class)
                .isThrownBy(() -> productService.create(chicken.toVo()))
                .withMessage(ErrorCode.PRODUCT_NAME_PROFANITY_NOT_ALLOWED.toString());
        }

        @DisplayName("상품가격은 0원 이상이어야 한다.")
        @ParameterizedTest
        @ValueSource(ints = {-10000, 0, 10000})
        void 상품가격_허용범위_검사(final int price) {
            chicken = ProductFixture.test(null, BigDecimal.valueOf(price)).create();

            if (price < 0) {
                assertThatExceptionOfType(ProductPriceException.class)
                    .isThrownBy(() -> productFacade.create(chicken))
                    .withMessage(ErrorCode.PRODUCT_PRICE_NOT_ALLOWED.toString());
            }
        }

    }

    @Nested
    @DisplayName("상품 수정")
    class 상품_수정 {

        @ParameterizedTest
        @DisplayName("성공")
        @ValueSource(ints = {0, 1000, 10000})
        void 상품수정_성공(final int price) {

            final UUID productId = productService.create(chicken.toVo()).productId();

            updateChicken = new ProductRequest.UpdatePrice(productId, BigDecimal.valueOf(price));

            var result = productService.changePrice(updateChicken.toVo());

            assertAll(
                () -> assertEquals(BigDecimal.valueOf(price), result.price().price())
            );

        }

        @DisplayName("상품가격이 0원 이상이어야 한다.")
        @ParameterizedTest
        @ValueSource(ints = {-10000, 0, 10000})
        void 상품가격_허용범위_검사(final int price) {
            updateChicken = ProductFixture.test(null, BigDecimal.valueOf(price)).update();

            if (price < 0) {
                assertThatExceptionOfType(ProductPriceException.class)
                    .isThrownBy(() -> productFacade.changePrice(updateChicken))
                    .withMessage(ErrorCode.PRODUCT_PRICE_NOT_ALLOWED.toString());
            }
        }

        @DisplayName("메뉴의 가격이 메뉴 구성 상품들의 총 금액보다 크면 해당 메뉴는 숨겨진다.")
        @ParameterizedTest
        @CsvSource({"10000"})
        void 가격비교_숨김처리(final int price) {
            final UUID productId = productFacade.create(chicken).id();

            var product = productRepository.findById(productId);


            updateChicken = new UpdatePrice(productId, BigDecimal.valueOf(price));

            chickenMenu = MenuFixture.test(
                null,
                null,
                null,
                true,
                List.of(new MenuProductFixture(
                    productId,
                    MenuProductQty.of(100)
                ).toEntity())
            ).toEntity();
            var menu = menuRepository.save(chickenMenu);

            productService.changePrice(updateChicken.toVo());

            menuPolicy.hideMenu(productId);

            var result = menuRepository.findById(menu.getId()).orElseThrow();

            assertThat(result.isDisplayed()).isFalse();
        }

    }

    @Nested
    @DisplayName("상품 조회")
    class 상품_조회 {

        @Test
        @DisplayName("성공 : 특정 조건 없이 상품의 모든 목록을 조회할 수 있다.")
        void 상품목록_조회() {
            List<ProductResponse.GetProduct> result = productFacade.findAll();

            assertAll(
                () -> assertThat(result).isEmpty(),
                () -> assertEquals(result.size(), 0)
            );
        }
    }
}
