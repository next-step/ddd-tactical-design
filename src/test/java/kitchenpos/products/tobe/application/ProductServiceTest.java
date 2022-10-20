package kitchenpos.products.tobe.application;

import static kitchenpos.products.helper.ProductFixtureFactory.createProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.global.domain.client.ProfanityCheckClient;
import kitchenpos.products.application.FakeProfanityCheckClient;
import kitchenpos.products.helper.ProductFixtureFactory;
import kitchenpos.products.tobe.application.dto.ProductCommand;
import kitchenpos.products.tobe.application.dto.ProductInfo;
import kitchenpos.products.tobe.domain.model.entity.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import kitchenpos.products.tobe.infra.InMemoryProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductServiceTest {

    private final ProductRepository productRepository = new InMemoryProductRepository();

    private final ProfanityCheckClient profanityCheckClient = new FakeProfanityCheckClient();

    private ProductService testTarget;

    @BeforeEach
    void setUp() {
        testTarget = new ProductService(productRepository, profanityCheckClient);
    }

    @Test
    void 상품을_등록_할_수_있다() {
        // given
        var request = new ProductCommand.Create("후라이드 치킨", 6000);

        // when
        ProductInfo.Create response = testTarget.create(request);

        // then
        assertAll(
            () -> assertThat(response.getId()).isNotNull(),
            () -> assertThat(response.getName()).isEqualTo("후라이드 치킨"),
            () -> assertThat(response.getPrice()).isEqualTo(BigDecimal.valueOf(6000))
        );
    }

    @Test
    void 상품가격을_변경_할_수_있다() {
        // given
        Product product = productRepository.save(createProduct("후라이드 치킨", 6000));
        var request = new ProductCommand.ChangePrice(product.getId(), 5000);

        // when
        ProductInfo.ChangePrice response = testTarget.changePrice(request);

        // then
        assertAll(
            () -> assertThat(response.getId()).isEqualTo(product.getId()),
            () -> assertThat(response.getName()).isEqualTo("후라이드 치킨"),
            () -> assertThat(response.getPrice()).isEqualTo(BigDecimal.valueOf(5000))
        );
    }

    @Test
    void 상품의_목록을_조회할_수_있다() {
        // given
        productRepository.save(ProductFixtureFactory.createProduct("후라이드", 6000));
        productRepository.save(ProductFixtureFactory.createProduct("양념치킨", 6000));

        // when
        final List<Product> actual = testTarget.findAll();

        // then
        assertThat(actual).hasSize(2);
    }
}