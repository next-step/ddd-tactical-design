package kitchenpos.products.application;

import static java.math.BigDecimal.valueOf;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.ui.dto.ChangeProductRequest;
import kitchenpos.products.ui.dto.ChangeProductResponse;
import kitchenpos.products.ui.dto.CreateProductRequest;
import kitchenpos.products.ui.dto.CreateProductResponse;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.common.infra.PurgomalumClient;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;


class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;
    private MenuRepository menuRepository;
    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        menuRepository = new InMemoryMenuRepository();
        purgomalumClient = new FakePurgomalumClient();
        productService = new ProductService(productRepository, menuRepository, purgomalumClient);
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
        Product request = 후라이드치킨();
        productRepository.save(request);

        UUID productId = request.getId();
        BigDecimal 변경할_가격 = valueOf(17000);

        // when
        productService.changePrice(productId, new Product(new ProductName("양념치킨"), new ProductPrice(변경할_가격)));

        // then
        Product updatedProduct = productRepository.findById(productId).orElseThrow();
        // 값만 비교
        assertThat(updatedProduct.getPrice().compareTo(변경할_가격)).isEqualTo(0);

        // scale 제거하고 비교
        assertThat(updatedProduct.getPrice().stripTrailingZeros())
                .isEqualTo(변경할_가격.stripTrailingZeros()); // stripTrailingZeros()를 사용하면 소수점이 필요 없는 경우 자동으로 정리함
    }

    @Test
    void 존재하지_않은_상품_ID로_가격을_변경할_수_없다() {
        // given
        UUID nonExistentProductUd = UUID.randomUUID();
        BigDecimal changedPrice = valueOf(20000);

        Product updateProduct = new Product(new ProductName("후라이드치킨"), new ProductPrice(valueOf(16000)));
        updateProduct.updatePrice(changedPrice);

        // when & then
        assertThatThrownBy(() -> productService.changePrice(nonExistentProductUd, updateProduct))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void 등록된_상품을_전체_조회할_수_있다() {
        // given
        Product burger = 후라이드치킨();
        Product pizza = 양념치킨();

        // when
        productService.create(burger);
        productService.create(pizza);

        // then
        List<Product> products = productRepository.findAll();
        assertNotNull(products);
        assertThat(products).hasSize(2);
    }
}
