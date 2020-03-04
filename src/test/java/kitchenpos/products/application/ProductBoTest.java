package kitchenpos.products.application;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductBoTest {
    @Mock
    private ProductRepository productRepository;

    private ProductBo productBo;

    @BeforeEach
    void setUp() {
        productBo = new ProductBo(productRepository);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        // given
        Product product = Product.registerProduct("후라이드치킨", 17000);
        given(productRepository.save(product)).willReturn(product);

        // when
        final Product actual = productBo.create(product);

        // then
        assertThat(actual).isEqualTo(product);
    }

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ParameterizedTest
//    @NullSource
    @ValueSource(strings = "-1000")
    void create(final int price) {
        // given
        // when
        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                productBo.create(Product.registerProduct("정신나간치킨", price)));
    }
//
//    @DisplayName("상품의 목록을 조회할 수 있다.")
//    @Test
//    void list() {
//        // given
//        final Product friedChicken = productDao.save(friedChicken());
//        final Product seasonedChicken = productDao.save(seasonedChicken());
//
//        // when
//        final List<Product> actual = productBo.list();
//
//        // then
//        assertThat(actual).containsExactlyInAnyOrderElementsOf(Arrays.asList(friedChicken, seasonedChicken));
//    }
//
//    private void assertProduct(final Product expected, final Product actual) {
//        assertThat(actual).isNotNull();
//        assertAll(
//                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
//                () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice())
//        );
//    }
}
