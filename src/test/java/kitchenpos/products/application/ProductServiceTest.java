package kitchenpos.products.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.fake.FakeProfanityValidator;
import kitchenpos.fake.InMemoryMenuGroupRepository;
import kitchenpos.fake.InMemoryMenuRepository;
import kitchenpos.fake.InMemoryProductRepository;
import kitchenpos.fixture.MenuFixture;
import kitchenpos.fixture.MenuGroupFixture;
import kitchenpos.fixture.ProductFixture;
import kitchenpos.menugroups.domain.MenuGroupRepository;
import kitchenpos.menugroups.domain.tobe.MenuGroup;
import kitchenpos.menus.application.MenuProductsService;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.menus.domain.tobe.Menu;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.domain.ProfanityValidator;
import kitchenpos.products.domain.tobe.Product;
import kitchenpos.products.domain.tobe.ProductPrice;
import kitchenpos.products.ui.dto.ProductCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("ProductService")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ProductServiceTest {

    private MenuGroupRepository menuGroupRepository = new InMemoryMenuGroupRepository();

    private ProductRepository productRepository = new InMemoryProductRepository();

    private MenuRepository menuRepository = new InMemoryMenuRepository();

    private ProfanityValidator profanityValidator = new FakeProfanityValidator();

    private ProductService productService;

    @BeforeEach
    void setUp() {
        MenuProductsService menuProductService = new MenuProductsService(productRepository);
        productService = new ProductService(productRepository, menuRepository, menuProductService,
                profanityValidator);
    }

    @Test
    void 상품을_등록할_수_있다() {
        ProductCreateRequest request = ProductFixture.createRequest("후라이드", 20_000L);
        Product actual = productService.create(request);

        assertThat(actual.getId()).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"비속어", "욕설이 포함된 단어"})
    void 상품의_이름에_욕설이_포함되어있으면_예외를_던진다(final String name) {
        ProductCreateRequest request = ProductFixture.createRequest(name);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> productService.create(request));
    }

    @Test
    void 상품가격이_0보다_작으면_예외를_던진다() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> productService.create(ProductFixture.createRequest(-10_000L)));
    }

    @Test
    void 상품의_가격을_변경한다() {
        Product saved = productService.create(ProductFixture.createRequest("후라이드", 20_000L));
        Menu menu = createFriedMenu(saved);

        Product actualProduct = productService.changePrice(saved.getId(),
                new ProductPrice(BigDecimal.valueOf(15_000L)));
        Menu actualMenu = menuRepository.findById(menu.getId()).get();

        assertAll(
                () -> assertThat(actualProduct.getPrice()).isEqualTo(BigDecimal.valueOf(15_000L)),
                () -> assertThat(actualMenu.isDisplayed()).isTrue()
        );
    }

    @Test
    void 상품가격변경시_수정한_상품이_속해있는_메뉴가격이_상품가격x상품갯수의_총합을_넘는다면_해당_메뉴는_손님들에게_숨긴다() {
        Product saved = productService.create(ProductFixture.createRequest("후라이드", 20_000L));
        Menu menu = createFriedMenu(saved);

        Product actualProduct = productService.changePrice(saved.getId(),
                new ProductPrice(BigDecimal.valueOf(12_000L)));
        Menu actualMenu = menuRepository.findById(menu.getId()).get();

        assertAll(
                () -> assertThat(
                        actualProduct.isSamePrice(
                                new ProductPrice(BigDecimal.valueOf(12_000L)))).isTrue(),
                () -> assertThat(actualMenu.isDisplayed()).isFalse()
        );
    }

    private Menu createFriedMenu(Product product) {
        MenuGroup chickenMenuGroup = menuGroupRepository.save(MenuGroupFixture.createChicken());
        return menuRepository.save(
                MenuFixture.createFriedOnePlusOne(chickenMenuGroup, product));
    }

    @Test
    void 모든_상품목록을_볼_수_있다() {
        productService.create(ProductFixture.createRequest("후라이드", 20_000L));
        productService.create(ProductFixture.createRequest("양념", 20_000L));

        List<Product> actual = productService.findAll();

        assertThat(actual).hasSize(2);
    }
}