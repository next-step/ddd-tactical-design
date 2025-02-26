package kitchenpos.product.application;

import static kitchenpos.TestFixtureFactory.createProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import kitchenpos.TestFixtureFactory;
import kitchenpos.common.application.PurgomalumClient;
import kitchenpos.menu.domain.model.Menu;
import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.menu.domain.service.MarginValidator;
import kitchenpos.product.application.dto.ChangeProductPriceServiceRq;
import kitchenpos.product.application.dto.CreateProductServiceRq;
import kitchenpos.product.application.dto.ProductServiceRs;
import kitchenpos.product.domain.model.Product;
import kitchenpos.product.domain.model.ProductNameCreationService;
import kitchenpos.product.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;
    private MenuRepository menuRepository;
    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        menuRepository = mock(MenuRepository.class);
        purgomalumClient = mock(PurgomalumClient.class);
        ProductNameCreationService productNameCreationService = new ProductNameCreationService(purgomalumClient);
        MarginValidator marginValidator = new MarginValidator(menuRepository);
        productService = new ProductService(productRepository, productNameCreationService, marginValidator);
    }

    @Test
    @DisplayName("상품을 등록할 수 있다")
    void create() {
        // given
        CreateProductServiceRq request = new CreateProductServiceRq("김치", BigDecimal.valueOf(5000));
        when(purgomalumClient.containsProfanity(any())).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        ProductServiceRs result = productService.create(request);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getName()).isEqualTo("김치");
        assertThat(result.getPrice()).isEqualTo(BigDecimal.valueOf(5000));
    }

    @Test
    @DisplayName("상품 가격은 0원 미만이면 예외가 발생한다.")
    void product_price_exception() {
        // given
        CreateProductServiceRq request = new CreateProductServiceRq("김치", BigDecimal.valueOf(-1000));

        // when // then
        assertThatThrownBy(() -> {
            productService.create(request);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품 가격을 채워주세요!");
    }

    @Test
    @DisplayName("상품 이름에 비속어를 넣으면 예외가 발생한다.")
    void product_name_exception() {
        // given
        CreateProductServiceRq request = new CreateProductServiceRq("fuck", BigDecimal.valueOf(5000));
        when(purgomalumClient.containsProfanity("fuck")).thenReturn(true);

        // when // then
        assertThatThrownBy(() -> productService.create(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상품의 가격을 변경할 수 있다")
    void change_price() {
        // given
        Product product = createProduct("김치", 5000);
        ChangeProductPriceServiceRq request = new ChangeProductPriceServiceRq(BigDecimal.valueOf(6000));
        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        when(menuRepository.findAllByProductId(any())).thenReturn(List.of());

        // when
        ProductServiceRs result = productService.changePrice(product.getId(), request);

        // then
        assertThat(result.getPrice()).isEqualTo(BigDecimal.valueOf(6000));
    }

    @Test
    @DisplayName("상품 가격이 변하면 메뉴의 판매 가격이 재료 가격의 총합보다 낮은 메뉴는 게시가 중단된다")
    void change_price_exception() {
        // given
        Product product = createProduct("김치", 5000);
        Menu menu = TestFixtureFactory.createMenuWithProductAndGroup("김치찌개", 7000, product);
        ChangeProductPriceServiceRq request = new ChangeProductPriceServiceRq(new BigDecimal(8000));

        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        when(menuRepository.findAllByProductId(any())).thenReturn(List.of(menu));

        // when
        productService.changePrice(product.getId(), request);

        // then
        assertThat(menu.isDisplayed()).isFalse();
    }

    @Test
    @DisplayName("전체 상품을 조회할 수 있다")
    void find_all() {
        // given
        List<Product> products = List.of(
                createProduct("김치", 5000),
                createProduct("된장", 3000)
        );
        when(productRepository.findAll()).thenReturn(products);

        // when
        List<ProductServiceRs> result = productService.findAll();

        // then
        assertThat(result).hasSize(2);
    }
}
