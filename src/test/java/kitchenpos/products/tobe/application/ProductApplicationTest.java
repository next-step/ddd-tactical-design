package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductApplicationTest {
    private ProductRepository productRepository = new ImMemoryProductRepository();

    private ProductApplication productApplication;

    private final Product friedChicken = new Product("후라이드", BigDecimal.valueOf(16_000L));
    private final Product seasonedChicken = new Product("양념치킨", BigDecimal.valueOf(16_000L));

    @BeforeEach
    void setUp() {
        productApplication = new ProductApplication(productRepository);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void registerNewProduct() {
        // given
        final ProductData expected = new ProductData();
        expected.setName("후라이드");
        expected.setPrice(BigDecimal.valueOf(16_000L));;

        // when
        final ProductData actual =  productApplication.registerNewProduct(expected);

        // then
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice())
        );


    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void listProduct() {
        // given
        productRepository.save(friedChicken);
        productRepository.save(seasonedChicken);

        // when
        final List<ProductData> actual = productApplication.listProduct();

        // then
        assertThat(actual).hasSize(2);
    }


}