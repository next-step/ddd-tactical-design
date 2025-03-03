package kitchenpos.products.tobe.application;

import static java.math.BigDecimal.valueOf;
import kitchenpos.products.tobe.infra.FakeProfanitiesClient;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.tobe.domain.exception.InvalidProductException;
import kitchenpos.products.tobe.domain.vo.Profanities;
import kitchenpos.products.tobe.ui.dto.ChangeProductRequest;
import kitchenpos.products.tobe.ui.dto.ChangeProductResponse;
import kitchenpos.products.tobe.ui.dto.CreateProductRequest;
import kitchenpos.products.tobe.ui.dto.CreateProductResponse;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.vo.ProductName;
import kitchenpos.products.tobe.domain.vo.ProductPrice;
import kitchenpos.products.tobe.domain.ProductRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;
    private MenuRepository menuRepository;
    private Profanities profanities;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        menuRepository = new InMemoryMenuRepository();
        profanities = new FakeProfanitiesClient();
        productService = new ProductService(productRepository, menuRepository, profanities);
    }

    @Test
    void 상품을_등록할_수_있다() {
        // given
        ProductName name = new ProductName("후라이드치킨");
        ProductPrice price = new ProductPrice(valueOf(16000));
        CreateProductRequest request = new CreateProductRequest(name.getName(), price.getPrice());

        // when
        CreateProductResponse savedProduct = productService.create(request);

        // then
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.id()).isNotNull();
        assertThat(savedProduct.name()).isEqualTo("후라이드치킨");
        assertThat(savedProduct.price()).isEqualTo(valueOf(16000));
    }
    @Test
    void 상품의_가격을_변경할_수_있다() {
        // given
        Product product = new Product("후라이드치킨", valueOf(16000));
        productRepository.save(product);

        UUID productId = product.getId();
        BigDecimal 변경할_가격 = valueOf(17000);
        ChangeProductRequest request = new ChangeProductRequest(변경할_가격);

        // when
        ChangeProductResponse response = productService.changePrice(productId, request);

        // then
        Product updatedProduct = productRepository.findById(productId).orElseThrow();

        assertThat(updatedProduct.getPrice().compareTo(변경할_가격)).isEqualTo(0);
        assertThat(response.productId()).isEqualTo(productId);
        assertThat(response.price()).isEqualTo(변경할_가격);
    }

    @Test
    void 존재하지_않은_상품_ID로_가격을_변경할_수_없다() {
        // given
        UUID nonExistentProductUd = UUID.randomUUID();
        BigDecimal 변경할_가격 = valueOf(20000);
        ChangeProductRequest request = new ChangeProductRequest(변경할_가격);

        // when & then
        assertThatThrownBy(() -> productService.changePrice(nonExistentProductUd, request))
                .isInstanceOf(InvalidProductException.class);
    }

    @Test
    void 등록된_상품을_전체_조회할_수_있다() {
        // given
        ProductName name = new ProductName("후라이드치킨");
        ProductPrice price = new ProductPrice(valueOf(16000));
        CreateProductRequest request = new CreateProductRequest(name.getName(), price.getPrice());

        // when
        productService.create(request);

        // then
        List<Product> products = productRepository.findAll();
        assertNotNull(products);
        assertThat(products).hasSize(1);
    }
}
