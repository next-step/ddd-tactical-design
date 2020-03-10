package kitchenpos.products.bo;

import kitchenpos.products.Fixtures;
import kitchenpos.products.tobe.domain.model.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductBoTest {
    private ProductBo productBo;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productBo = new ProductBo(productRepository);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        // given
        final Product expected = new Product(Fixtures.FRIED_CHICKEN_ID, "후라이드", BigDecimal.valueOf(16_000L));
        given(productRepository.save(expected)).willReturn(expected);

        // when
        final Product actual = productBo.create(expected);

        // then
        assertProduct(expected, actual);
    }

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "-1000")
    void create(final BigDecimal price) {
        // given
        final Product expected = new Product(Fixtures.FRIED_CHICKEN_ID, "후라이드", BigDecimal.valueOf(16_000L));
        given(productRepository.save(expected)).willThrow(NumberFormatException.class);

        // when
        // then
        assertThatExceptionOfType(NumberFormatException.class).isThrownBy(() -> productBo.create(expected));
    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void list() {
        // given
        final Product friedChicken = new Product(Fixtures.FRIED_CHICKEN_ID, "후라이드", BigDecimal.valueOf(16_000L));
        final Product seasonedChicken = new Product(Fixtures.SEASONED_CHICKEN_ID, "양념치킨", BigDecimal.valueOf(16_000L));

        given(productRepository.findAll()).willReturn(Arrays.asList(friedChicken, seasonedChicken));

        // when
        final List<Product> actual = productBo.list();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(Arrays.asList(friedChicken, seasonedChicken));
    }

    private void assertProduct(final Product expected, final Product actual) {
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice())
        );
    }
}
